```cpp
#include <iostream>
#include <mutex>
#include <thread>
#include <vector>

constexpr int thread_num = 4;
using ll = long long;

void add_a_part(int start, int end, ll &sum, std::mutex &mtx) {
  int temp_sum{};
  for (int i = start; i < end; ++i) {
    temp_sum += i;
  }
  mtx.lock();
  sum += temp_sum;
  mtx.unlock();
}

int main() {
  int start = 0, end = 123456789;
  ll sum{};
  int size_of_one_part = (end - start) / thread_num;
  std::vector<std::thread> ths;
  std::mutex mtx;
  for (int i = 0; i < thread_num - 1; ++i) {
    ths.push_back(std::thread{add_a_part, start + i * size_of_one_part,
                              start + i * size_of_one_part + size_of_one_part,
                              std::ref(sum), std::ref(mtx)});
  }
  ths.push_back(std::thread{
      add_a_part, start + size_of_one_part * thread_num - size_of_one_part,
      end + 1, std::ref(sum), std::ref(mtx)});
  for (auto &t : ths) {
    t.join();
  }
  std::cout << sum;
}
```cpp
