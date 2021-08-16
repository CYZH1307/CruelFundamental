# redis consistency

* 由于采用异步日志复制, redis 不支持强一致性, 所以在某些情况下还是会发生写丢失
    * 复制还未完成前, 主crash
    * 脑裂

* 解决
    * 不使用 redis 集群, 仅使用 master-slave 架构
    * 如果对于一致性要求比较强, 可以使用 wait 命令模仿同步复制, 回对性能造成比较大的影响
    * 合理设置 node timeout 参数, 减少脑裂时产生的影响