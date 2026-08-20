#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define maxLimit 100
#define stringLength 32

typedef struct {
    char transactionName[stringLength];
    char variableName[stringLength];
    int oldValue;
    int newValue;
    int hasOldValue; // 1 for Immediate, 0 for Deferred
    int isCheckpoint;
    int isStart;
    int isCommit;
    int isAbort;
    char activeTransactions[maxLimit][stringLength]; // record.activeTransactions[0] ->  "T2"
    int activeCount;
} LogRecord;

// Global State
LogRecord logRecords[maxLimit];
int logRecordCount = 0;

char databaseVariables[maxLimit][stringLength];
int databaseValues[maxLimit];
int databaseVariableCount = 0;

char redoList[maxLimit][stringLength];
int redoCount = 0;

char undoList[maxLimit][stringLength];
int undoCount = 0;

// Start index for recovery pass (based on checkpoint)
int recoveryStartIndex = 0;

void trimWhitespace(char *text) {
    char *start = text;
    char *end;

    while (*start && isspace((unsigned char)*start)) start++;
    if (*start == '\0') {
        text[0] = '\0';
        return;
    }

    end = start + strlen(start) - 1;
    while (end > start && isspace((unsigned char)*end)) end--;
    end[1] = '\0';

    if (start != text) {
        memmove(text, start, strlen(start) + 1);
    }
}

void updateDatabaseVariable(const char *variableName, int value) {

    for (int i = 0; i < databaseVariableCount; i++) {
        if (strcmp(databaseVariables[i], variableName) == 0) {
            databaseValues[i] = value;
            return;
        }
    }
    strcpy(databaseVariables[databaseVariableCount], variableName);     //new variable
    databaseValues[databaseVariableCount] = value;
    databaseVariableCount++;
}

int isTransactionInList(char transactionList[maxLimit][stringLength], int count, const char *transactionName) {
    for (int i = 0; i < count; i++) {
        if (strcmp(transactionList[i], transactionName) == 0) return 1;
    }
    return 0;
}

void removeFromUndoList(const char *transactionName) {
    for (int u = 0; u < undoCount; u++) {
        if (strcmp(undoList[u], transactionName) == 0) {
            for (int k = u; k < undoCount - 1; k++) {
                strcpy(undoList[k], undoList[k + 1]);       //shift left to remove the transaction
            }
            undoCount--;
            break;
        }
    }
}

// Read the lines and make record object for each line and store in logRecords array
void parseLogRecord(char *line) {
    char cleanedLine[256];
    int position = 0;
    
    //remove < and > and \r and \n from the line
    for (int i = 0; line[i] != '\0'; i++) {
        if (line[i] != '<' && line[i] != '>' && line[i] != '\r' && line[i] != '\n') {
            cleanedLine[position++] = line[i];
        }
    }

    cleanedLine[position] = '\0';
    trimWhitespace(cleanedLine);

    if (strlen(cleanedLine) == 0) return;

    LogRecord record;
    memset(&record, 0, sizeof(LogRecord));

    // Case 1: Checkpoint log
    if (strstr(cleanedLine, "checkpoint") != NULL) {
        record.isCheckpoint = 1;

        // Parse the running transactions from the checkpoint log
        char *startParen = strchr(cleanedLine, '(');    //point to the first '(' character
        char *endParen = strchr(cleanedLine, ')');
        
        if (startParen && endParen && endParen > startParen) {
            *endParen = '\0';
            char *txList = startParen + 1;
            char *token = strtok(txList, ",");
            while (token != NULL) {
                trimWhitespace(token);
                if (strlen(token) > 0) {
                    // Add the transaction to the active transactions list
                    strcpy(record.activeTransactions[record.activeCount++], token);
                }
                token = strtok(NULL, ",");
            }
        }
    } 
    // Case 2
    else if (strstr(cleanedLine, "start") != NULL) {
        // Set the start flag and parse the transaction name
        record.isStart = 1;
        sscanf(cleanedLine, "start %s", record.transactionName);
    } 
    else if (strstr(cleanedLine, "commit") != NULL) {
        // Set the commit flag and parse the transaction name
        record.isCommit = 1;
        sscanf(cleanedLine, "commit %s", record.transactionName);
    } 
    else if (strstr(cleanedLine, "abort") != NULL) {
        record.isAbort = 1;
        sscanf(cleanedLine, "abort %s", record.transactionName);
    } 

    //this is for the regular transactions {T1, X, 10, 20}
    else {
        char *token = strtok(cleanedLine, ",");
        char tokens[4][stringLength];
        int tokenCount = 0;

        while (token != NULL && tokenCount < 4) {
            trimWhitespace(token);
            strcpy(tokens[tokenCount++], token);
            token = strtok(NULL, ",");
        }

        strcpy(record.transactionName, tokens[0]);
        strcpy(record.variableName, tokens[1]);

        if (tokenCount == 4) { // Immediate: Transaction, Variable, Old, New
            record.oldValue = atoi(tokens[2]);
            record.newValue = atoi(tokens[3]);
            record.hasOldValue = 1;
        } else if (tokenCount == 3) { // Deferred: Transaction, Variable, New
            record.newValue = atoi(tokens[2]);
            record.hasOldValue = 0;
        }
    }

    logRecords[logRecordCount++] = record;
}

// Function to read and parse the input file
void readAndParseFile(const char *filename) {
    FILE *inputFile = fopen(filename, "r");
    if (!inputFile) {
        printf("Error opening file: %s\n", filename);
        exit(1);
    }

    char singleLine[256];
    while (fgets(singleLine, sizeof(singleLine), inputFile)) {
        trimWhitespace(singleLine);
        if (strlen(singleLine) == 0) continue;


        if (strncmp(singleLine, "INITIAL:", 8) == 0) {
            char *token = strtok(singleLine + 8, ",=");
            while (token != NULL) {
                char varName[stringLength];
                sscanf(token, "%s", varName);
                token = strtok(NULL, ",=");
                if (token != NULL) {
                    updateDatabaseVariable(varName, atoi(token));
                    token = strtok(NULL, ",=");
                }
            }
        } else {
            parseLogRecord(singleLine);
        }
    }
    fclose(inputFile);
}


//divide the transactions into groups(redo,undo)
void classifyTransactions(void) {
    int checkpointIndex = -1;

    // Find the last checkpoint record in the log
    for (int i = logRecordCount - 1; i >= 0; i--) {
        if (logRecords[i].isCheckpoint) {
            checkpointIndex = i;
            break;
        }
    }

    if (checkpointIndex != -1) {
        // Start recovery from the checkpoint location
        recoveryStartIndex = checkpointIndex;

        // Populate initial UNDO list with transactions active at the checkpoint
        for (int j = 0; j < logRecords[checkpointIndex].activeCount; j++) {
            if (!isTransactionInList(undoList, undoCount, logRecords[checkpointIndex].activeTransactions[j])) {
                strcpy(undoList[undoCount++], logRecords[checkpointIndex].activeTransactions[j]);
            }
        }

        // Find the earliest <start T> record among the active transactions
        for (int j = 0; j < logRecords[checkpointIndex].activeCount; j++) {
            for (int k = 0; k < checkpointIndex; k++) {
                if (logRecords[k].isStart && 
                    strcmp(logRecords[k].transactionName, logRecords[checkpointIndex].activeTransactions[j]) == 0) {
                    if (k < recoveryStartIndex) {
                        recoveryStartIndex = k; // Roll back scan start index for REDO/UNDO log scans
                    }
                    break;
                }
            }
        }
    } else {
        recoveryStartIndex = 0;
    }

    // Process the log sequentially from recoveryStartIndex to the end
    for (int i = recoveryStartIndex; i < logRecordCount; i++) {
        // Skip log records prior to the checkpoint except when identifying new starts after checkpoint
        if (i < checkpointIndex) {
            continue;
        }

        if (logRecords[i].isStart) {
            if (!isTransactionInList(undoList, undoCount, logRecords[i].transactionName) &&
                !isTransactionInList(redoList, redoCount, logRecords[i].transactionName)) {
                strcpy(undoList[undoCount++], logRecords[i].transactionName);
            }
        } else if (logRecords[i].isCommit) {
            removeFromUndoList(logRecords[i].transactionName);
            if (!isTransactionInList(redoList, redoCount, logRecords[i].transactionName)) {
                strcpy(redoList[redoCount++], logRecords[i].transactionName);
            }
        } else if (logRecords[i].isAbort) {
            // Aborted transactions are handled during execution and should not be re-undone/redone
            removeFromUndoList(logRecords[i].transactionName);
        }
    }
}

// Recovery function for Deferred Modification
void performDeferredRecovery(void) {
    printf("=== Executing Deferred Recovery ===\n");

    printf("REDO Phase (Forward Pass from index %d):\n", recoveryStartIndex);
    for (int i = recoveryStartIndex; i < logRecordCount; i++) {
        if (!logRecords[i].isStart && !logRecords[i].isCommit && 
            !logRecords[i].isAbort && !logRecords[i].isCheckpoint) {
            
            if (isTransactionInList(redoList, redoCount, logRecords[i].transactionName)) {
                updateDatabaseVariable(logRecords[i].variableName, logRecords[i].newValue);
                printf("REDO: Set %s = %d (by %s)\n", 
                       logRecords[i].variableName, logRecords[i].newValue, logRecords[i].transactionName);
            }
        }
    }

    printf("UNDO Phase: Skipped (Deferred modification does not require undoing)\n");
}

// Recovery function for Immediate Modification
void performImmediateRecovery(void) {
    printf("=== Executing Immediate Recovery ===\n");

    printf("REDO Phase (Forward Pass from index %d):\n", recoveryStartIndex);
    for (int i = recoveryStartIndex; i < logRecordCount; i++) {
        if (!logRecords[i].isStart && !logRecords[i].isCommit && 
            !logRecords[i].isAbort && !logRecords[i].isCheckpoint) {

            if (isTransactionInList(redoList, redoCount, logRecords[i].transactionName)) {
                updateDatabaseVariable(logRecords[i].variableName, logRecords[i].newValue);
                printf("REDO: Set %s = %d (by %s)\n", 
                       logRecords[i].variableName, logRecords[i].newValue, logRecords[i].transactionName);
            }
        }
    }

    printf("\nUNDO Phase (Backward Pass to index %d):\n", recoveryStartIndex);
    for (int i = logRecordCount - 1; i >= recoveryStartIndex; i--) {
        if (!logRecords[i].isStart && !logRecords[i].isCommit && 
            !logRecords[i].isAbort && !logRecords[i].isCheckpoint) {

            if (isTransactionInList(undoList, undoCount, logRecords[i].transactionName)) {
                updateDatabaseVariable(logRecords[i].variableName, logRecords[i].oldValue);
                printf("UNDO: Restored %s = %d (by %s)\n", 
                       logRecords[i].variableName, logRecords[i].oldValue, logRecords[i].transactionName);
            }
        }
    }
}

// Print Database State
void printDatabaseState(const char *label) {
    printf("%s:\n", label);
    for (int i = 0; i < databaseVariableCount; i++) {
        printf("  %s = %d\n", databaseVariables[i], databaseValues[i]);
    }
}

int main(void) {
    readAndParseFile("input.txt");

    // Detect Scheme Type
    int isImmediateScheme = 0;
    for (int i = 0; i < logRecordCount; i++) {
        if (logRecords[i].hasOldValue) {
            isImmediateScheme = 1;
            break;
        }
    }

    classifyTransactions(); // get redoList, undoList, and recoveryStartIndex

    printf("\n\n\nLog Scheme: %s Modification\n\n", isImmediateScheme ? "Immediate" : "Deferred");

    printf("REDO Transactions: ");
    for (int i = 0; i < redoCount; i++)
        printf("%s ", redoList[i]);

    printf("\nUNDO Transactions: ");
    for (int i = 0; i < undoCount; i++) 
        printf("%s ", undoList[i]);
    printf("\n\n");

    printDatabaseState("Initial Database Values");
    printf("\n");

    if (isImmediateScheme) {
        performImmediateRecovery();
    } else {
        performDeferredRecovery();
    }

    printf("\n");
    printDatabaseState("Final Database Values");

    return 0;
}