# 用你熟悉的语言实现生产者消费者问题

## C++

```cpp
template <typename T>
class BlockQueue
{
public:
    BlockQueue(size_t size)
        : size_(size)
    {
    }

    bool push(const T &value)
    {
        std::unique_lock<std::mutex> lock(mtx_);
        while (not isFull())
            notFull_.wait(lock);
        dq_.push_back(value);
        notEmpty_.notify_one();
    }

    T pop()
    {
        std::unique_lock<std::mutex> lock(mtx_);
        while (dq.empty())
            notEmpty_.wait();
        auto ans = dq_.front();
        dq_.pop_front();
        notFull_.notify_one();
        return ans;
    }

private:
    bool isFull() { return dq.size() == size_; }
    const size_t size_;
    std::mutex mtx_;
    std::deque<T> dq_;
    std::condition_variable notFull_, notEmpty_;
};
```
