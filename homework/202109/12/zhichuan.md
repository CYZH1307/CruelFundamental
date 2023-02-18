# epoll

## process

1. NIC 接收到数据，DMA写入内存，NIC发出IRQ中断请求
2. kernel到内核态，处理中断
3. kernel拿到packet，切换到用户态给程序

context switch频繁，所以有IO多路复用

## epoll

红黑树+ready list + event model

红黑树里有所有fd和event
readlist里有对应的callback

对应fd的event发生后，epoll_callback会把它放到ready list里

epoll——wait的时候，通过ep——poll扫描ready list，将事件和data拷贝到用户态中


## LT level trigger

LT默认，应用程序可以不立即处理event，在下次调用epoll_wait的时候，会再次通知

支持block 和 non-block

## ET edge trigger

ET模式下，程序必须立即处理，只支持no-block socket

理论上效率比较高，避免重复epoll