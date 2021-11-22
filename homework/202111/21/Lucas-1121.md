# Elasticsearch 的数据一致性
- https://zhuanlan.zhihu.com/p/34830403
- https://zhuanlan.zhihu.com/p/35299145
- https://www.jianshu.com/p/61dd9fb7d785

## 集群节点
- 一个MasterNode，node.master: true，为候选主节点
- 多个DataNode，node.data: true，会有数据的Shard
- 多个ProxyNode，both of node.data and node.master are false
- 节点发现：ZenDiscovery，不依赖于ZooKeeper
- 主节点选举：discovery.zen.minimum_master_nodes: 2，至少有两个节点选举一个节点？
- TODO
