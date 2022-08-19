```cpp
#include <chrono>
#include <iostream>
#include <mutex>
#include <random>
#include <thread>

using namespace std;

int myrand(int min, int max) {
  static std::mt19937 rnd(std::time(nullptr));
  return std::uniform_int_distribution<>(min, max)(rnd);
}

std::mutex mo;

void phil(int ph, std::mutex &ma, std::mutex &mb) {
  for (;;) {
    int duration = myrand(1000, 2000);
    {
      std::lock_guard<std::mutex> g{mo};
      cout << ph << " thinks " << duration << "ms\n";
    }
    this_thread::sleep_for(chrono::milliseconds(duration));
    scoped_lock sco{ma, mb};
    {
      lock_guard<mutex> g{mo};
      cout << "\t\t" << ph << "got ma,mb\n";
    }

    duration = myrand(1000, 2000);
    {
      lock_guard<mutex> g{mo};
      cout << "\t\t\t\t" << ph << " eats " << duration << "ms\n";
    }
    this_thread::sleep_for(chrono::milliseconds{duration});
  }
}

int main() {
  mutex forks[5];
  thread t0(phil, 0, ref(forks[0]), ref(forks[1]));
  thread t1(phil, 1, ref(forks[1]), ref(forks[2]));
  thread t2(phil, 2, ref(forks[2]), ref(forks[3]));
  thread t3(phil, 3, ref(forks[3]), ref(forks[4]));
  thread t4(phil, 4, ref(forks[4]), ref(forks[0]));
  t0.join();
  t1.join();
  t2.join();
  t3.join();
  t4.join();
}
```

