#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

int main() {
    int p[2];   // p[0] = read end, p[1] = write end
    pid_t pid;
    char message[] = "this is a message from the parent process.";
    char read_buffer[100];

    // Create the pipe before forking (child must inherit both ends)
    if (pipe(p) < 0) {
        perror("pipe failed");
        exit(EXIT_FAILURE);
    }

    pid = fork();

    if (pid < 0) {
        // fork() failed
        perror("fork failed");
        exit(EXIT_FAILURE);
    }
    else if (pid == 0) {

        close(p[1]);  

        // Read message sent by parent
        int bytes_read = read(p[0], read_buffer, sizeof(read_buffer) - 1);
        if (bytes_read < 0) {
            perror("read failed");
            exit(EXIT_FAILURE);
        }
        read_buffer[bytes_read] = '\0'; 

        printf("Child received: %s\n", read_buffer);

        close(p[0]);  // done reading, close read end too

        execlp("whoami", "whoami", NULL);

        // Only reached if execlp fails
        perror("execlp failed");
        exit(EXIT_FAILURE);
    }
    else {

        close(p[0]);  

        if (write(p[1], message, strlen(message)) < 0) {
            perror("write failed");
            exit(EXIT_FAILURE);
        }

        close(p[1]);  

        if (wait(NULL) < 0) {
            perror("wait failed");
            exit(EXIT_FAILURE);
        }

        printf("Parent: child process finished, exiting now.\n");
    }

    return 0;
}