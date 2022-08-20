## 2022/08/18

### 用你熟悉的语言实现生产者消费者问题

```cpp
#include<iostream>
#include<mutex>
#include<chrono>
#include<thread>
using namespace std;

int n=10;				// 缓存区大小
int in=0, out = 0;		// 生产指针,消费指针
int full = 0, empty=10;  // 空与满
int buffer[10];			// 缓存区
mutex mtx;				// 互斥量

//生产者
void producer(){
	do{
		while(full==n);	
		this_thread::sleep_for(chrono::seconds(1));			
		mtx.lock();		
		buffer[in] = 1;	
		in = (in + 1) % n; //生产指针偏移
		cout << "生产者生产:" << in << endl; 
		empty--;
		full++;
		mtx.unlock();
	}while(true);
}
//消费者
void consumer(){
	do{
		while(empty==10);
		mtx.lock();
		buffer[out] = 0;
		out=(out+1)%n; //消费指针偏移
		cout <<"消费者消费:" << out << endl; 
		empty++;
		full--;
		mtx.unlock();
		this_thread::sleep_for(chrono::seconds(2));
	}while(true);
}
int main(){
	thread t1{producer};
	thread t2{consumer};
	
	t1.join();
	t2.join();
	return 0;
} 
```