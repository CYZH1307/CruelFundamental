## 2022/03/04

### 请你来说一下socket编程中服务器端和客户端主要用到哪些函数

#### 服务端：

- 创建socket，使用socket（）
- 绑定信息，使用bind（）
- 设置连接，使用listen（）
- 接收客户端连接，使用accept（）
- 受发数据，使用send（），recv（）

#### 客户端：

- 创建socket，使用socket（）
- 连接服务器，使用connect（）
- 受发数据，使用send（），recv（）