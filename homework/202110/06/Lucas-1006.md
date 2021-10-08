能不能请教大佬们几个问题，谢谢了！
1. 当时Facebook的服务器是不是都还在工作啊
2. 能不能通过IP访问这些服务器啊
3. DNS是不是分布式缓存的啊，浏览器，操作系统，运营商域名服务器是不是存有Facebook域名到IP的映射啊
4. 就算Facebook的域名服务区宕机，用户能不能通过DNS缓存，找到Facebook服务器的IP，并访问啊
5. DNS服务器是如何禁用BGP广告的啊

To ensure reliable operation, our DNS servers disable those BGP advertisements if they themselves can not speak to our data centers, 
since this is an indication of an unhealthy network connection.
