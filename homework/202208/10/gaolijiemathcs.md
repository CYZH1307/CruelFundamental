## epoll的两种触发模式？

LT(level trigger水平模式)和ET(edge trigger边缘模式)



### LT(level trigger水平模式)

LT为epoll的默认操作模式，当epoll_wait函数检测到有事件发生并将通知应用程序，应用程序不一定立即进行处理，这样epoll_wait再次检测到这个事件的时候才会通知应用程序。

### ET(edge trigger边缘模式)

ET模式，只要epoll_wait函数检测到事件发生，通知应用程序立即处理，后面epoll_wait函数不会再检测这种事件，ET降低了同一个事件被epoll触发的次数，因此效率比LT模式高。



默认是LT：内核会告诉你一个文件描述符是否就绪了，然后就可以对这个就绪的fd进行IO操作，如果不做任何的操作，内核还是会继续通知，这种编程模式出错的可能性更小一点。