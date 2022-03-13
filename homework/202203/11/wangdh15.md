## C++ Lock-free queue的实现，lock-free和lock-base的区别

lock-free不会有加锁上锁的操作，线程不会被显示地休眠。主要基于的操作是cas。

lock-base可能会有线程休眠和唤醒的损耗。
