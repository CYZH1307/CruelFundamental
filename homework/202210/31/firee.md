### 为什么 Linux 默认页大小是 4KB？什么是大页？什么情况适合用大页？

为什么 Linux 默认页大小是 4KB？
Linux 同时支持正常大小的内存页和大内存页（Huge Page），绝大多数处理器上的内存页的默认大小都是 4KB。4KB 的内存页其实是一个历史遗留问题，在上个世纪 80 年代确定的 4KB 一直保留到了今天。
<img width="673" alt="image" src="https://user-images.githubusercontent.com/40649825/199154074-b85f322e-8dd5-4bab-ae46-affb50359821.png">



什么是大页？
大于 4KB 的页；

什么情况适合用大页？
需要使用大量内存，且无法忍受多层寻址时间，例如数据库
