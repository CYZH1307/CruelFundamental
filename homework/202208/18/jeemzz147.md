```

#include <bits/stdc++.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
using namespace std;
  
// Declaration
int r1, items = 0;
  
// Semaphore declaration
sem_t notEmpty, notFull;
  
// Producer Section
void* produce(void* arg)
{
    while (1) {
        sem_wait(¬Full);
        sleep(rand() % 100 * 0.01);
        cout << 
      "Producer produces item.Items Present = "
             << ++items << endl;
        sem_post(¬Empty);
        sleep(rand() % 100 * 0.01);
    }
}
  
// Consumer Section
void* consume(void* arg)
{
    while (1) {
        sem_wait(¬Empty);
        sleep(rand() % 100 * 0.01);
        cout << 
     "Consumer consumes item.Items Present = "
             << --items << endl;
        sem_post(¬Full);
        sleep(rand() % 100 * 0.01);
    }
}
  
int main(int argv, char* argc[])
{
  
    int N;
    cout << 
      "Enter the capacity of the buffer" << endl;
    cin >> N;
  
    // thread declaration
    pthread_t producer, consumer;
  
    // Declaration of attribute......
    pthread_attr_t attr;
  
    // semaphore initialization
    sem_init(¬Empty, 0, 0);
    sem_init(¬Full, 0, N);
  
    // pthread_attr_t initialization
    pthread_attr_init(&attr);
    pthread_attr_setdetachstate(&attr,
             PTHREAD_CREATE_JOINABLE);
  
    // Creation of process
    r1 = pthread_create(&producer, &attr, 
                        produce, NULL);
    if (r1) {
        cout << 
          "Error in creating thread" << endl;
        exit(-1);
    }
  
    r1 = pthread_create(&consumer, &attr, 
                        consume, NULL);
    if (r1) {
        cout << 
          "Error in creating thread" << endl;
        exit(-1);
    }
  
    // destroying the pthread_attr
    pthread_attr_destroy(&attr);
  
    // Joining the thread
    r1 = pthread_join(producer, NULL);
    if (r1) {
        cout << "Error in joining thread" << endl;
        exit(-1);
    }
  
    r1 = pthread_join(consumer, NULL);
    if (r1) {
        cout << "Error in joining thread" << endl;
        exit(-1);
    }
  
    // Exiting thread
    pthread_exit(NULL);
  
    return 0;
}
```
