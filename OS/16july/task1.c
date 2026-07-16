#include <stdio.h>      // printf(), fgets()
#include <stdlib.h>     // General utility functions
#include <string.h>     // strcpy()
#include <fcntl.h>      // O_CREAT, O_RDWR
#include <sys/mman.h>   // shm_open(), mmap(), munmap(), shm_unlink()
#include <sys/stat.h>   // Permission constants like 0666
#include <unistd.h>     // fork(), ftruncate(), close()
#include <sys/wait.h>   // wait()

// Structure that will be stored inside shared memory (only one object where both processes can read/write)
typedef struct {
    int flag;               // Used for synchronization
    char message[1024];     // Message buffer
} SharedMemory;

int main() {

    // Name of the shared memory object.
    // Must start with '/'
    const char *name = "/my_shared_memory";

    // -------------------------------------------------------
    // STEP 1: Create (or open) a shared memory object
    //
    // O_CREAT -> Create if it does not exist.
    // O_RDWR  -> Open for both reading and writing.
    // 0666    -> Read/write permission for everyone.
    // -------------------------------------------------------
    int fd = shm_open(name, O_CREAT | O_RDWR, 0666);    // freates the shared memory object (just a chunk of memory in the kernel)
                                                        // and returns a file descriptor( and int to identify the resource . eg: keyboard, mouse, file, etc) to access it.

    // -------------------------------------------------------
    // STEP 2: Allocate enough memory for our structure.
    //
    // Shared memory is initially of size 0.
    // ftruncate() increases its size.
    // -------------------------------------------------------
    ftruncate(fd, sizeof(SharedMemory));

    // -------------------------------------------------------
    // STEP 3: Map the shared memory into this process.
    //
    // NULL               -> OS chooses the address.
    // sizeof(...)        -> Number of bytes to map.
    // PROT_READ          -> Can read.
    // PROT_WRITE         -> Can write.
    // MAP_SHARED         -> Parent and child share updates.
    // fd                 -> Shared memory file descriptor.
    // 0                  -> Start from beginning.
    // -------------------------------------------------------
    SharedMemory *shm = (SharedMemory *)mmap(
        NULL,
        sizeof(SharedMemory),
        PROT_READ | PROT_WRITE,
        MAP_SHARED,
        fd,
        0
    );

    // Initially nobody has written anything.
    // 0 = Empty
    // 1 = Parent has written
    // 2 = Child has replied
    shm->flag = 0;

    // -------------------------------------------------------
    // STEP 4: Create a child process.
    //
    // Parent gets child's PID (>0)
    // Child gets 0
    // -------------------------------------------------------
    pid_t pid = fork();

    // =======================================================
    // PARENT PROCESS
    // =======================================================
    if (pid > 0) {

        // Ask the user for a message.
        printf("Enter a message: ");
        fgets(shm->message, sizeof(shm->message), stdin);

        // Tell the child that the message is ready.
        shm->flag = 1;

        // Wait until child changes the flag to 2.
        while (shm->flag != 2);

        // Child has written its reply.
        printf("Child replied: %s\n", shm->message);

        // Wait until child process terminates.
        wait(NULL);

        // Remove mapping from memory.
        munmap(shm, sizeof(SharedMemory));  //so shm no longer points to the shared memory object

        // Close shared memory descriptor.
        close(fd);      //deleting fd from the process table

        // Delete shared memory object from the system.
        shm_unlink(name);
    }

    // =======================================================
    // CHILD PROCESS
    // =======================================================
    else if (pid == 0) {

        // Wait until parent writes something.
        while (shm->flag != 1);

        // Read parent's message.
        printf("Child received: %s", shm->message);

        // Write reply into shared memory.
        strcpy(shm->message, "Message received successfully");

        // Notify parent that reply is ready.
        shm->flag = 2;

        // Remove mapping.
        munmap(shm, sizeof(SharedMemory));

        // Close descriptor.
        close(fd);
    }

    return 0;
}