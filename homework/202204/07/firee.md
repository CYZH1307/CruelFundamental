### 搜索引擎倒排原理及实现方式

搜索引擎的目标是让用户输入关键词，搜索引擎找到含有这些关键词的网站

定位到网站就可以找到关键词，所以关键词找网站这一步叫倒排(猜的)



实现：

每个关键词拉一个链表，链表里是有这个关键词的文章，这样查询的时候把多个关键词对应的链表取个交集就能找到目标文件。