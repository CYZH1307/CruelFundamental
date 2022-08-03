### 用你熟悉的语言写一个并行从0到123456789求和的函数


```
#include <iostream>
#include <thread>

using namespace std;

typedef long long ll;


const int N = 123456789;

ll sum = 0;

void add_x(int u) {
    for(int i = u; i <= N; i += 4){
        sum += i;
        cout << "add " << i << "  thread_id = " << this_thread::get_id() << endl;
    }
} 

int main() {

    thread ths[4];
    for(int i = 0; i < 4; i++) ths[i] = thread(add_x, i);
    
    for(int i = 0; i < 4; i++) 
        ths[i].detach();

    printf("sum = %lld\n", sum);

    system("pause");
    return 0;
}
```
