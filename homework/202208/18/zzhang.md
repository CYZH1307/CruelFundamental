``` c++
// producer consumer model 有点问题，还是会竞争
#include <stdio.h>
#include <thread>
#include <pthread.h>
#include <stdlib.h>
#include <chrono>

#define N 20
#define true 1
#define producerNum  3
#define consumerNum  2
#define sleepTime 1000

// typedef int semaphore;
typedef int item;

item buffer[N] = {0}; // N message
int in = 0;
int out = 0;
int msgCount = 0;

volatile int empty = N, full = 0;
pthread_mutex_t proCmutex, mutex;

void * producer(void * a){
    while(true){
        pthread_mutex_lock(&proCmutex);
        int curmsg = msgCount ++;
        // printf("Produce messgae id %d\n", msgCount);
        pthread_mutex_unlock(&proCmutex);

        while(empty <= 0){
            printf("Queue is full\n");
            std::this_thread::sleep_for(std::chrono::milliseconds(sleepTime));
        }
        

        // write message 
        pthread_mutex_lock(&mutex);
        empty--; 
        buffer[in] = curmsg;
        printf("Write messgae id %d at position %d\n", curmsg, in);
        in = (in + 1) % N;
        full++;
        pthread_mutex_unlock(&mutex);
        
        std::this_thread::sleep_for(std::chrono::milliseconds(sleepTime));
    }
}

void * consumer(void *b){
    while(true){
        while(full <= 0){
            printf("Empty queue\n");
            std::this_thread::sleep_for(std::chrono::milliseconds(sleepTime));
        }
        // read message
        pthread_mutex_lock(&mutex);
        full --;
        int nextc = buffer[out];
        printf("> Consume message id %d at position %d\n", nextc, out);
        buffer[out] = 0;
        out = (out + 1) % N;
        empty ++;
        pthread_mutex_unlock(&mutex);
        std::this_thread::sleep_for(std::chrono::milliseconds(sleepTime));
    }
}

int main() {
    pthread_t threadPool[producerNum+consumerNum];
    int i;
    // create producers
    for(i = 0; i < producerNum; i++){
        pthread_t temp;
        if(pthread_create(&temp, NULL, producer, NULL) == -1){
            printf("ERROR, fail to create producer%d\n", i);
            exit(1);
        }
        threadPool[i] = temp;
    }
    // create consumers
    for(i = 0; i < consumerNum; i++){
        pthread_t temp;
        if(pthread_create(&temp, NULL, consumer, NULL) == -1){
            printf("ERROR, fail to create consumer%d\n", i);
            exit(1);
        }
        threadPool[i + producerNum] = temp;
    }
    // thread join
    void * result;
    for(i = 0; i < producerNum + consumerNum; i++){
        if(pthread_join(threadPool[i], &result) == -1){
            printf("fail to recollect\n");
            exit(1);
        }
    }
    return 0;
}
```
