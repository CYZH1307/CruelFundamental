### 如何设计一个短链接服务？

#### 短链作用

链接变短，内容长度有限制的平台发文，短链能够减少内容长度占用。

二维码生成时短链的二维码密度小易识别。

灵活易配置，能够修改重定向方向。

#### 短链生成方法

- Hash方法：对原始链取Hash值。可能存在Hash碰撞。需要选择一个碰撞率低的算法。例如MurmurHash计算短链。

```
1.开始
2.输入原始链接+用户信息
3.MurmurHash计算短链
4.布隆过滤 判断是否存在（已经有原始连接+用户信息+特殊信息 则跳转回3）否则跳转到5
5.存储并且返回短链
6.结束
```

- 统一发号器：已经有一个短链前缀，通过一个统一发号器在前缀后增加分配一个ID。统一发号器可以通过Redis自增/MySQL自增主键/Snowflake等。这种方法对于同一个原始连接，会根据用户、地点等维度生成不同的链接。

#### 存储短链

- 关系型数据库
- 非关系型数据库

#### 短链请求

```
1.请求的短链字符串
2.布隆过滤器
3.缓存数据
4.数据库存储
```

布隆过滤器防止缓存穿透。

HTTP重定向编码：

- 301为永久重定向，浏览器第一次请求拿到重定向地址，从浏览器缓存中获取重定向地址，不会再请求短链服务，减少服务请求次数，降低服务器负载，后面浏览器不再向后端发送请求，获取不到真正的点击数。
- 302，代表临时重定向，每次浏览器都会向服务器发起请求获取新的地址。一般使用302，可以获取到连接的真实点击数。