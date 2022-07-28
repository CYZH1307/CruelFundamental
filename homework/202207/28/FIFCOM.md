[//]: fcn.ign
## 如何设计一个短链接服务？
这个简单（  

这里简单使用Mysql和php来实现

- 第一步：在数据库中存入ID和原始链接的映射关系，即有数据表：

```sql
create table `liteurl`
(
    `id`   int not null auto_increment comment 'ID',
    `url`  varchar(2100) not null comment '原始链接'
    primary key (`id`)
) engine = InnoDB default charset = utf8 comment '短网址';
```
之所以是varchar(2100)是因为url最长限制2083个字符

示例：

| ID  | URL  |
| :------------: | :------------: |
| `114514` | `'https://www.baidu.com'` |


- 第二步：将十进制的ID转换为62进制(即base62编码)，生成短链接

62进制即数字 + 小写字母 + 大写字母，则`114514`将转换为`"tN0"`

则最终短链接为：https://lurl.xx/tN0

- 第三步：解析短链接为原始链接，并使用302 Found跳转至原址

即将62进制转换为10进制，并在数据库中找到原始链接

```sql
# base62_decode() 的实现略
select url from liteurl where id = base62_decode('tN0');
```

并跳转：
```php
header('Location: ' . $originUrl);
```
