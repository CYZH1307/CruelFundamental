# epoll的两种触发模式？

## LT（level triggered）
LT-水平触发模式。在该模式下，当有事件发生并调用epoll_wait后，若未及时处理，下一次调用epoll_wait仍会继续通知。即同一个事件有可能被重复通知，这是默认模式，编程较为简单。

## ET(edge trigger)
ET-边缘触发模式，其与水平模式的区别就是，调用epoll_wait通知过的事件，不论是否经过处理，再次调用epoll_wait不会再次通知了，ET模式在很大程度上降低了同一个epoll事件被重复触发的次数，因此ET模式效率比LT模式高。
