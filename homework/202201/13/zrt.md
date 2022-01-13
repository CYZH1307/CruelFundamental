0113
了解REST API吗？

REST(REpresentational State Transfer ) API是一种客户端与服务器交互形式的规范。
它指的是资源对应一个URI，并利用HTTP的动词来对资源的不同操作进行处理。并且每次操作是无状态的。
例如，GET可以表示获取。POST表示新建/提交修改。DELETE是删除。
服务器通过http的status code描述操作的结果。
这种设计模式把HTTP和对资源CRUD的操作直接结合起来，而不是单纯把HTTP套在外面。
