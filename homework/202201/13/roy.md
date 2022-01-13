### GET与POST的区别

1. GET 请求一般是幂等的，而POST一般不是，所以浏览器可以cache GET请求，但这都可以被设计改变。
2. GET 请求URL限制1024KB，POST没有。
3. GET 请求一般不用request body。
4. 其余的区别基本都可以定制，没有语义上的要求。
