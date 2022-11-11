## 什么是MapReduce框架

MapReduce是一个编程框架，它允许我们在分布式环境中对大数据集进行分布式并行处理

MapReduce包含两个完全不同的任务-Map和Reduce

正如MapReduce名字所暗示的，Reducer阶段是在Mapper阶段完成之后进行。

因此，首先是map作业，它会读取数据块并进行处理然后产生key-value对作为中间输出结果

Mapper或者说是map作业的结果（key-value对）将作为Reducer的输入

reducer从多个map作业处接收key-value对

然后reducer将这些中间数据（中间key-value对）合并成更小的元组或者key-value对作为最终输出。

  
#### Mapper类

Mapper类是使用MapReduce进行数据处理的第一个阶段。

在这里，RecordReader会将每条输入记录处理成相应的key-value对。

Hadoop的Mapper会在本地磁盘存储这些中间数据。

InputSplit（输入分片）：它是数据的逻辑表示。它代表一个工作块，包含在MapReduce程序中的一个map任务。

RecordReader：它和InputSplit交互把获取的数据转换成key-value对

#### Reducer类

mapper生成的中间输出会提供给reducer，reducer会处理它，并生成最终输出，然后存储到HDFS中。

#### Driver类
Driver类负责设置MapReduce作业，并在Hadoop中运行。你可以在该类中指定Mapper和Reducer类的名字接收的数据类型以及作业名。

## 请以WordCount(分布式的词频统计)为例，简述MapReduce的处理过程

Map过程：并行读取文本，对读取的单词进行map操作，每个词都以<key,value>形式生成。  
我的理解：

如读取第一行Hello World Bye World ，分割单词形成Map。

<Hello,1> <World,1> <Bye,1> <World,1>

Reduce操作：对map的结果进行排序，合并，最后得出词频。
我的理解：

经过进一步处理(combiner),将形成的Map根据相同的key组合成value数组。

<Bye,1,1,1> <Hadoop,1,1,1,1> <Hello,1,1,1> <World,1,1>

循环执行Reduce(K,V[])，分别统计每个单词出现的次数。

<Bye,3> <Hadoop,4> <Hello,3> <World,2>