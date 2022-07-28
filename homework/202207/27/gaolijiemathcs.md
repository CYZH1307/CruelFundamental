### 介绍一下GFS, Big Table & MapReduce

都是Google的产品。

#### GFS

GFS，为Google的一个大型分布式文件系统，大数据处理提供存储的平台，与MapReduce和BigTable技术结合紧密。核心解决如何在频繁的故障当中确保能够不间断提供数据存储服务。

主从模式：由Master server和多个Chunk Server组成。

Master Server负责维护系统的命名空间，访问控制信息，文件到块的映射和块当前位置等元数据，与Chunk Server通信。

Chunk Server负责具体的存储工作，数据以文件的形式存储在Chunk Server上，Client是应用程序访问GFS的接口。

优势：

- Client和Master Server之间只有控制流，没有数据流，降低了Master Server的负载。
- 由于Client和Chunk Server之间直接传输数据流，并且文件分为多个Chunk，所以Client可以同时并行访问多个Chunk Server 让系统IO并行度提高。

Google减少Client与Master Server交互解决Master Server访问性能瓶颈问题，Client直接与ChunkServer通信，Master Server只提供数据块查询功能。

特点：

- 采用中心服务器模式
  - 方便增加Chunk Server
  - Master Server可以掌握系统内所有Chunk Server的情况，方便负载均衡。
  - 不存在元数据的一致性问题。
- 不缓存数据
  - 流式读写，不存在大量重复读写，缓存对性能提高不大。
  - Chunk Server数据存储在本地文件系统上，即使真的频繁读写，本地缓存也可以支持。
  - 建立缓存需要考虑一致性问题。



#### Big Table

BigTable是一个键值对映射的，非关系型的数据库，稀疏分布式、持久化存储的多维度排序映射表。

Google需要存储多种数据，并且可以响应海量请求。BigTable具有能够支持多种数据存储，服务器弹性可扩展性好，高可用。

BigTable服务器，也是主从模式，一个主服务器，多个片服务器。Bigtable会将表（table）进行分片，片（tablet）的大小维持在100-200MB范围，一旦超出范围就将分裂成更小的片，或者合并成更大的片。每个片服务器负责一定量的片，处理对其片的读写请求，以及片的分裂或合并。片服务器可以根据负载随时添加和删除。这里片服务器并不真实存储数据，而相当于一个连接Bigtable和GFS的代理，客户端的一些数据操作都通过片服务器代理间接访问GFS。



#### MapReduce

MapReduce解决如何，从海量数据获取期望计算值的问题。

是Google开发的针对大规模群组中海量数据请求的分布式编程模型。

实现Map和Reduce功能，Map将一个函数应用于集合中的所有成员，返回一个基于这个处理的结果集。

Reduce是将两个或者多个Map通过多个线程、进程或者独立系统进行并行处理得到的结果集进行分类和归纳。

MapReduce 封装了并行处理、容错处理、本地化计算、负载均衡等细节，具有简单而强大的接口。





http://c.biancheng.net/view/3565.html

https://blog.csdn.net/OpenNaive/article/details/7532589