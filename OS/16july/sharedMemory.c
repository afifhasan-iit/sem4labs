#include <stdio.h>      
#include <stdlib.h>     
#include <string.h>   
#include <fcntl.h>      
#include <sys/mman.h>   
#include <sys/stat.h>  
#include <unistd.h>     
#include <sys/wait.h>   


typedef struct {
    int flag;              
    char message[1024];     
} SharedMemory;


int main() {

    const char *name = "/my_shared_memory";
    int fd = shm_open(name, O_CREAT | O_RDWR, 0666);    
    ftruncate(fd, sizeof(SharedMemory));
    SharedMemory *shm = (SharedMemory *)mmap(
        NULL,
        sizeof(SharedMemory),
        PROT_READ | PROT_WRITE,
        MAP_SHARED,
        fd,
        0
    );

    shm->flag = 0;

    pid_t pid = fork();
    if (pid > 0) {


        printf("Enter a message: ");
        fgets(shm->message, sizeof(shm->message), stdin);
        shm->flag = 1;


        while (shm->flag != 2);

  
        printf("Child replied: %s\n", shm->message);

        wait(NULL);

        munmap(shm, sizeof(SharedMemory));  
        close(fd); 
        shm_unlink(name);
    }


    else if (pid == 0) {

        while (shm->flag != 1);

        printf("Child received: %s", shm->message);
        strcpy(shm->message, "Message received successfully");
        shm->flag = 2;


        munmap(shm, sizeof(SharedMemory));
        close(fd);
    }

    return 0;
}