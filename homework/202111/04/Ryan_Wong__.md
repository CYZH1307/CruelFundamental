# MongoDB原理 #

    1.DataModel：MongoDB是四大NoSQL家族里面的文档型数据库，其row的组织形式是文档，在MongoDB中称为BSON，可存储的信息丰富、支持BSON嵌套。这样会更有利于存储应用中的丰富信息，提升复合查询的性能；相应缺点是占用空间大。
    2.Storage-Index：使用B-树+hash。因为MongoDB的row支持嵌套，因此在使用中可使用单点查询一定程度代替范围扫描，无需像MySQL一样采用空间占用更大的B+树。
    3.Storage-Heap：特色在于拥有内置的autoSharding分片技术，实现灵活分布式配置下的负载均衡。
    4.Storage-Trx：采用弱一致性（最终一致性），提升批量读写性能，但限制了在强一致性场景的使用（带有金钱交易的任意场景）。
    5.Storage-Trx-Undo/Redo：采用较为通用的WAL。
  
