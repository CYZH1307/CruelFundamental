# 没有accept可以建立TCP连接吗
建立连接的过程中根本不需要accept() 参与， 执行accept()只是为了从全连接队列里取出一条连接。

## 参考
动图图解！没有accept，能建立TCP连接吗？ - 小白debug的文章 - 知乎
https://zhuanlan.zhihu.com/p/422385138