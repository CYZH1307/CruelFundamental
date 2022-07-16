# 什么是单例模式？

一个类只有一个对象, 经常用在全局的资源描述, 如服务器的连接对象, 日志对象等.

## 实现方式

### 懒汉式

需要的时候再new出来. 使用**双检锁**来保证线程安全.

```cpp
#include <cstdlib>
#include <iostream>
#include <mutex>
#include <thread>
#include <vector>

using namespace std;

/*
    懒汉式实现单例模型
    时间换空间, 存在线程安全和线程不安全的写法.
*/

class Singleton {
private:
    // 默认构造函数
    Singleton() {
    }
    // 拷贝构造函数
    Singleton(const Singleton &s) {
    }
    // 赋值操作
    Singleton &operator==(const Singleton &) {
    }
    // 类静态变量的申明(可以看成限定了访问权限的全局变量)
    static Singleton *instance;
    static mutex mtx;

public:
    // 辅助类来完成单例对象的内存释放
    // 利用了cpp的RAII(资源获取就是初始化): 保证使用前初始化, 使用后调用析构函数释放资源.
    class destory {
    public:
        ~destory() {
            if (instance == nullptr) {
                cout << "free mem" << endl;
                delete instance;
                instance = nullptr;
            }
        }
        destory() {
            cout << "called ctor" << endl;
        }
    };
    // 声明其静态变量
    static destory dest;
    // // 静态成员函数返回单例对象(非线程安全版)
    // static Singleton* getInstance () {
    //     if (instance == nullptr) {
    //         _sleep(10);
    //         instance = new Singleton();
    //     }
    //     return instance;
    // }

    // 静态成员函数返回单例对象(线程安全版)
    static Singleton *getInstance() {
        if (instance == nullptr) {
            mtx.lock();
            // 必须再次判断是否为空, 否则阻塞线程获取锁之后, 直接创建对象是错误的.
            if (instance == nullptr)
                instance = new Singleton();
            mtx.unlock();
        }
        return instance;
    }
};

// 类静态变量必须定义
Singleton *Singleton::instance = nullptr;
mutex Singleton::mtx;
Singleton::destory Singleton::dest;

void getAndPrint(int id) {
    // cout << "thread id = " << id << ", get instance address = " << Singleton::getInstance() << '\n';
    cout << Singleton::getInstance() << '\n';
}

int main() {
    vector<thread> threads(10);

    for (int i = 0; i < threads.size(); i++)
        threads[i] = thread(getAndPrint, i + 1);

    for (auto &t : threads)
        t.join();

    return 0;
}
```

### 饿汉式

使用类静态变量来提前创建出单例对象.

```cpp
#include <iostream>
#include <mutex>
#include <thread>
#include <vector>
#include <cstdlib>

using namespace std;

/*
    饿汉式实现单例模型
    空间换时间, 且是线程安全的.
*/

class Singleton {
private:
    // 默认构造函数
    Singleton () {}
    // 拷贝构造函数
    Singleton (const Singleton& s) {}
    // 赋值操作
    Singleton& operator == (const Singleton& ) {}
    // 类静态变量的声明(可以看成限定了访问权限的全局变量)
    static Singleton instance;

public:
    // 静态成员函数返回单例对象
    static Singleton* getInstance () {
        return &instance;
    }
};

// 类静态变量必须定义
Singleton Singleton::instance = Singleton();

void getAndPrint(int id) {
    // cout << "thread id = " << id << ", get instance address = " << Singleton::getInstance() << '\n';
    cout << Singleton::getInstance() << '\n';
}

int main() {
    vector<thread> threads(10);

    for (int i = 0; i < threads.size(); i ++ )
        threads[i] = thread(getAndPrint, i + 1);

    for (auto& t : threads)
        t.join();

    return 0;
}
```
