#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#define _POSIX_C_SOURCE 200809L
#define ARRAY_SIZE 1000000

int arr[ARRAY_SIZE];

typedef struct {
    int start;
    int end;
    long long partial_sum;
} ThreadArgs;       //now ThreadArgs is a new type


// Worker function for each thread
void *calculate_sum(void *arg) {
    ThreadArgs *bounds = (ThreadArgs *)arg;
    bounds->partial_sum = 0;

    for (int i = bounds->start; i < bounds->end; i++) {
        bounds->partial_sum += arr[i];
    }

    printf("Start: %d, End: %d, Partial Sum: %lld\n", bounds->start, bounds->end, bounds->partial_sum);


    return NULL;
}

// Threaded sum
long long MultiThreaded_sum(int num_threads) {
    pthread_t threads[num_threads];
    ThreadArgs args[num_threads];

    int chunk = ARRAY_SIZE / num_threads;
    int remainder = ARRAY_SIZE % num_threads;
    for (int i = 0; i < num_threads; i++) {
        args[i].start = (i == 0) ? 0 : args[i-1].end;
        args[i].end   = args[i].start + chunk + (i < remainder ? 1 : 0);
        pthread_create(&threads[i], NULL, calculate_sum, &args[i]);
    }


    long long total = 0;
    for (int i = 0; i < num_threads; i++) {
        pthread_join(threads[i], NULL);
        total += args[i].partial_sum;
    }

    return total;
}

// Single-threaded sum (no threads)
long long singleThreaded_sum() {
    long long total = 0;
    for (int i = 0; i < ARRAY_SIZE; i++) {
        total += arr[i];
    }
    return total;
}

int main() {
    srand(time(NULL)); 

    for (int i = 0; i < ARRAY_SIZE; i++) {
        arr[i] = rand() % 100;  
    }

    int num_threads;
    printf("Enter number of threads: ");
    scanf("%d", &num_threads);

    long long result_single = singleThreaded_sum();

    long long result_threaded = MultiThreaded_sum(num_threads);

    printf("Single-threaded sum: %lld\n", result_single);
    printf("Multi-threaded sum: %lld\n", result_threaded);  

    return 0;
}