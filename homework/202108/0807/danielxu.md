## 必要性

* 多核情况下, 代码被转化为 CPU instructions 后, 对应多条 instructions, 由于调度的关系, 很可能读/写非原子化, 造成 data race
* 单核情况下, 同一条 CPU instruction, 在地址不对齐的情况下, 也可能会非原子化执行, 产生 data race
* std::atomic 的引入将原子类型读写操作最小化到单个CPU instruction, 避免了 data race

## 用法

* 最基本

```
std::atomic<uint64_t> atomic_test(0);

void storeValue()
{
    atomic_test.store(0x100000002, std::memory_order_relaxed);
}

uint64_t loadValue()
{
    return atomic_test.load(std::memory_order_relaxed);
}

int main() {
    storeValue();
    cout << loadValue() << endl;
}
```

* 其他

```
std::atomic<>::fetch_add()
std::atomic<>::fetch_sub()
std::atomic<>::fetch_and()
std::atomic<>::fetch_or()
std::atomic<>::fetch_xor()
std::atomic<>::exchange()
std::atomic<>::compare_exchange_strong()
std::atomic<>::compare_exchange_weak()
```
