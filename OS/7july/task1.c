#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    pid_t pid;


    printf("Parent process: PID = %d\n", getpid());


    pid = fork();

    if (pid < 0) {
        perror("fork failed");
        exit(EXIT_FAILURE);
    }
    else if (pid == 0) {
        
        printf("Now we are in the child process.\n");
        printf("Child process: PID = %d,\nParent PID = %d\n", getpid(), getppid());

  
        execlp("whoami", "whoami", NULL);

        
        perror("execlp failed");
        exit(EXIT_FAILURE);
    }
    else {
      
        printf("Now we are in the parent process.\n");
        int status;

        
        printf("Parent registered child PID = %d\n", pid);

      
        if (wait(NULL) < 0) {
            perror("wait failed");
            exit(EXIT_FAILURE);
        }

        printf("Parent: child process finished, exiting now.\n");
    }

    return 0;
}