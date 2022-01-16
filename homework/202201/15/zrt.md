0115
http请求包含了什么？

例如GET百度：
```
> GET / HTTP/1.1
> Host: www.baidu.com
> User-Agent: curl/7.64.1
> Accept: */*
>
```

以HTTP/1.1为例，包含请求行，包含http动词，路径，http版本。
请求头，包含host，user-agent等信息。
如果是POST等类型，在一个\r\n后还包含请求体。

请求体使用para=value这种形式进行编码，使用&进行分割，类似URI的query parameters。
