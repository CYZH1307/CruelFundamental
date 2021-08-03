# 0802 python rclock


```python
import threading

lock = threading.Lock()
rlock = threading.RLock()

rlock.acquire()
rlock.acquire()
rlock.acquire()

<locked _thread.RLock object owner=4609113600 count=3 at 0x10c3abf00>
可以看到, 上面的count 为 3
```

RLock 可以被同一个线程多次 lock, 其实现就是 普通lock + count
* 在第一次获取 acquire 时, 会分配 `self._block.acquire(blocking, timeout)`, 并将 count 置为1
* 在后续同一个线程 acquire 时, 只加count
* 因为在用完后, 需要 release 多次

