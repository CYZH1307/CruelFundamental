Kafka提供了 HA 机制，即 replica（复制品） 副本机制。每个 partition 的数据都会同步到其它机器上，形成自己的多个 replica 副本。所有 replica 会选举一个 leader 出来。
生产和消费都跟这个 leader 打交道，其他 replica 是 follower。写的时候，leader 会负责把数据同步到所有 follower 上去，读的时候读 leader 上的数据即可。
只能读写 leader？很简单，要是你可以随意读写每个 follower，那么就要 care 数据一致性的问题，系统复杂度太高，很容易出问题。Kafka 会均匀地将一个 partition 的所有 replica 分布在不同的机器上，这样可提高容错性。
