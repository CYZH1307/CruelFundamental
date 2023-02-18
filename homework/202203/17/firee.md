#### 2G物理内存，new一个3G的数组时发生什么？

##### 实验一

![image-20220318100752435.png](https://github.com/fireeeeeeeee/img-folder/blob/master/img-folder/image-20220318100752435.png?raw=true)

猜测windows上这个new操作也是写入时复制，所以内存占用小

##### 实验二

![](https://github.com/fireeeeeeeee/img-folder/blob/master/img-folder/image-20220318101256747.png?raw=true)

这时候系统有大量的换入换出操作