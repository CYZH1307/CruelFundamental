# 一致性算法
- https://zhuanlan.zhihu.com/p/35596768

## 一致性
- 集群的所有节点数据完全相同且能一致达成某个提案
- 有限时间内完成，最终结果相同，某个节点提出结果
- 严格一致性，依赖绝对的全局时间，获取到最近的一次写操作
- 一致性越强，处理能力越弱
- 强一致性
-   顺序一致性，所有操作都是按顺序可行
-   线性一致性，每个操作有一个全局时间戳
- 弱一致性，能容忍后续部分或全部访问不了
- 最终一致性，过一段时间，能访问到所有更新
