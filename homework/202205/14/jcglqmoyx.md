这几种容器都是通过红黑树实现的，支持在O(log N) 时间内添加/删除某个元素，元素都以节点的形式存储。其中map和multimap以key-value对的形式
存储元素，set和multiset以value的形式存储元素。 map和set中不允许出现相同的键/值，而multimap和multiset中则允许相同的键/值。