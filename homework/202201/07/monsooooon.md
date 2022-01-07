# ref
[mozilla org](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#redirection_messages)
[廖雪峰博客](https://www.liaoxuefeng.com/wiki/1252599548343744/1328761739935778)

diff btw 转发 (forward) / 重定向 (redirect)

# 重定向
在HTTP response status code的定义里, 只有redirect 3XX  
例如:
- 302 Found
- 301 Moved Permanently
- 307 Temporary Redirect
- 308 Permanent Redirect
Response里通过 `Location` 来告诉client, 重新请求新的URI, 对于浏览器, 会自动再次访问新的URI

# 转发
和HTTP status code没有关系  
指的是在HTTP server内部, 将client请求的URI用其他的URI handler进行处理, 执行操作. 对client无感知
