#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_OPS 100 
#define MAX_TXN 20

typedef struct {
    int transaction;    
    char operation_type;  
    char item;  
} Operation;

// Global vars
Operation ops[MAX_OPS];
int opCount = 0;

int graph[MAX_TXN][MAX_TXN];
int txnList[MAX_TXN];
int txnCount = 0;

int visited[MAX_TXN];
int recStack[MAX_TXN];

// Helper function
int txnExists(int t) {
    for (int i = 0; i < txnCount; i++)
        if (txnList[i] == t) return 1;
    return 0;
}

void readSchedule(const char* filename) {
    FILE* f = fopen(filename, "r");
    if (!f) {
        printf("Error: Cannot open file '%s'\n", filename);
        exit(1);
    }

    char txnName[10], type[5], item[5];

    while (fscanf(f, "%s %s %s", txnName, type, item) == 3) {
        int txnNum = atoi(txnName + 1);
        ops[opCount].transaction  = txnNum;
        ops[opCount].operation_type = type[0];
        ops[opCount].item = item[0];
        opCount++;

        if (!txnExists(txnNum))
            txnList[txnCount++] = txnNum;
    }

    fclose(f);
    printf("Read %d operations, %d transactions.\n\n", opCount, txnCount);
}

void buildGraph() {
    memset(graph, 0, sizeof(graph));

    for (int i = 0; i < opCount; i++) {
        for (int j = i + 1; j < opCount; j++) {
            
            if (ops[i].transaction == ops[j].transaction) continue;

            
            if (ops[i].item != ops[j].item) continue;

            // Check conflict: at least one is W
            if (ops[i].operation_type == 'W' || ops[j].operation_type == 'W') {
                // Ti came before Tj, so directed edge Ti -> Tj
                graph[ops[i].transaction][ops[j].transaction] = 1;
            }
        }
    }
}

int dfs(int txn) {
    visited[txn] = 1;
    recStack[txn] = 1;

    for (int neighbor = 0; neighbor < MAX_TXN; neighbor++) {
        if (graph[txn][neighbor]) {
            if (!visited[neighbor] && dfs(neighbor))
                return 1;
            else if (recStack[neighbor])
                return 1;
        }
    }

    recStack[txn] = 0;
    return 0;
}

int isCyclic() {
    memset(visited, 0, sizeof(visited));
    memset(recStack, 0, sizeof(recStack));

    for (int i = 0; i < txnCount; i++) {
        int t = txnList[i];
        if (!visited[t]) {
            if (dfs(t)) return 1;
        }
    }

    return 0;
}

void printResult() {
    if (isCyclic()){
        printf("found cycle in the precedence graph.\n");
        printf("Result: Schedule is NOT conflict serializable.\n");
    }
    else{
        printf("No cycle found in the precedence graph.\n");
        printf("Result: Schedule IS conflict serializable.\n");
    }
}

int main() {
    char filename[] = "schedule.txt"; 

    readSchedule(filename);
    buildGraph();
    printResult();

    return 0;
}