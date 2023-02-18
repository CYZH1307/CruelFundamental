# tiny url

## basic

tiny url -> server

<-302: original url

301: 可以有cache，服务器压力小
302: 可以做statistic，感觉如果是self-host，可以把statistic的任务给原来的service

## bloom filter

用布隆过滤器check是否存在，减小数据库压力

## id

1. uuid
性能差 b+tree index split

2. inc
依赖数据库

3. snowflake
timestamp + serverid + seqid
依赖时钟一致性：禁用时钟回拨

4. hash

64bit Murmur64

## 回收



