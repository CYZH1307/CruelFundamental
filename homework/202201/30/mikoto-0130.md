## Redis基本数据结构

Redis共有五种基本数据结构，string（字符串），list（列表），hash（字典），set（集合）以及zset（有序集合）

### string
string是redis中最简单的数据结构，redis中所有的key都是字符串类型，但是value可以有不同的数据结构，Redis中的字符串是动态字符串，内部可以修改（类似于c++ ？ 和python的字符串类型区分开）

redis中的string比较灵活，可以存储整数或者是浮点数。若字符串可以被解释为浮点数或者整数，则支持用户对其进行增or减操作，若对不存在的键or空串键进行增减操作，redis将其值当做0处理。若字符串无法被解释为整数或者浮点数，则返回一个错误。

### list
redis中的list其实是一个双向链表，两端插入或者是删除操作都是O(1)，而随机访问元素是O(n)的。其支持的一些操作与一般的双向链表比较类似

### set
redis中的set类似python的set，是一个无序集合，支持集合元素的插入、删除、查询以及集合间操作。例如交集、并集、差集等。具体操作类似

### zset
相比于set，zset增加另一个分值的概念，在将成员添加到集合中的时候额外赋予其一个分值的概念，然后按照分值排序。注意分值允许重复，加入有序集合中的元素不允许重复。支持查找元素分数以及元素排名，根据分值排名查找元素等操作

### hash
redis中和hash存储的同样是键值对，存储了字段到字段值的映射，一个hsah类型键值至多包含2^32 - 1个字段。

**注意**
redis中的数据类型不支持嵌套，如set。hash中的元素只能是字符串，不支持其它数据类型。
