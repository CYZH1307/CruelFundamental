## join

```python
thread t(func);
t.join
```

会在主线程中等待子线程的退出, 可以做一些释放资源的事情. 如果不join的话, 主线程先退出的情况下, 子线程也会被迫退出.

## detach

```python
thread t(func)
t.detach()
```

子线程独立于主线程, 主线程退出后, 子线程并不退出, 而是在后台运行, 结束后被运行时回收.

## joinable: 线程是否可以 join 或者 detach

- 没有join 或者 detach的线程, 返回 True
- 不是有 thread() 未含参数生成, 返回True
- thread没有被被move过