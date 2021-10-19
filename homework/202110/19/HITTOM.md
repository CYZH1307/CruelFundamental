Q: redis槽位原理
A:
redis默认16384个哈希槽,均匀分到各个节点上.
扩容时,各个节点尽量均匀抽出转移到新节点的槽,缩容时同理.
迁移时,每次移动一个槽上$count数量的key.
客户端维护一个每个槽在哪个节点的映射表,客户端收到moved指令时,会更新映射表.
获取key的slot: cluster keyslot "helloworld"
