#include <stdio.h>

int room = 4;          
int chopstick[5];      

void take_fork(int i) {

    if (room == 0) {
        printf("Philosopher %d is BLOCKED (Room Full).\n", i);
        return;
    }
    room--;  


    if (chopstick[i] == 0) {
        printf("Philosopher %d is BLOCKED (Waiting for Chopstick).\n", i);
        return;
    }
    chopstick[i]--;  


    if (chopstick[(i + 1) % 5] == 0) {
        printf("Philosopher %d is BLOCKED (Waiting for Chopstick).\n", i);
        return;
    }
    chopstick[(i + 1) % 5]--;  

    printf("Philosopher %d is EATING.\n", i);
}

int main() {
    int M, philosopher_id;

    FILE *fp = fopen("input.txt", "r");
    if (fp == NULL) {
        printf("Error: could not open input.txt\n");
        return 1;
    }

    for (int i = 0; i < 5; i++) {
        chopstick[i] = 1;
    }

    fscanf(fp, "%d", &M);

        for (int i = 0; i < M; i++) {
        fscanf(fp, "%d", &philosopher_id);
        take_fork(philosopher_id);
    }

    fclose(fp);
    return 0;
}