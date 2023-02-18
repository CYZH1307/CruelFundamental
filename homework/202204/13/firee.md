### 讲一下建立一个HTTP request的过程

http是应用层协议，用了tcp传输他的报文，知道资源所在的ip后可以点对点进行通信(tcp)

可以通过"Get"之类的文本信息表达自己的诉求

一般在浏览器上和DNS一起使用

比如

https://baike.baidu.com/item/ABC/1579?fr=aladdin

DNS可以把域名转化为ip地址 

http把ip塞到tcp的目标IP里 端口默认80

再把后面的塞到报文里面，就能发出去了

