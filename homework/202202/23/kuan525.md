#### 从内存里读一个byte计算机内部是怎么样实现的

概述：计算机的工作是基于二进制原理进行的，计算机内部所存储的信息都是用二进制来记录和表示的。

** 实现原理：** 
1. 计算机的存储器使用半导体集成电路构成的，它包括几亿个小的脉冲单元（二极管元件）。
2. 每个二极管元件如同一个开关，有两种稳定的工作状态：“ 导通 ”和“ 截止 ”，即电脉冲的“ 有 ”和“ 无 ”状态。用“ 1 ”和“ 0 ”表示。
磁盘的两面涂有磁性材料，根据N，S可以来记录0，1两种状态。
读入：写入数据时，磁头电磁铁改变极性材料的磁性来写数据。
读出：读入数据时，磁头的读取器可以得到磁性材料的极性然后还原成 0 ，1电信号。 

