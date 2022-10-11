缓存穿透，怎么解决？
1、定义：缓存穿透是请求缓存和数据库都不存在的数据，但是用户不断发起请求。导致直接绕过缓存去请求数据库。请求方很可能是攻击者。

2、解决方案

接口增加校验。例如用户鉴权校验，参数校验，不合法直接返回。
布隆过滤器：防止缓存穿透。快速判断key是否在数据库中，不存在直接返回。优点：位运算空间开销小。缺点：存在误判率，同时删除存在困难，并且数据量支持有限。
访问key未在DB中，也将空值写入缓存，key-null形式，设置较短时间。
IP拉黑，错误请求超阈值拉黑。