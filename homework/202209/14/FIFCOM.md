## HTTP中 get 与 post 的区别，哪个更安全一些

POST 更安全

1. GET的参数显式地通过URL传递，这可能导致参数被存储在服务器日志和浏览器历史记录中。
2. 第三方不能通过链接强制你发起POST请求（但可以通过js脚本来实现），但可以强制发起GET请求。如：

```html
<a href="http://exp.malicious.site/?param=somecontent">点我领奖</a>
```