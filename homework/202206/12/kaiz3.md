1.指令
~.对存储访问重排序，因而可以减少冲突失效
~.进行Profiling来检测冲突

2.数据
~.合并数组(data merge):通过将两个独立数组合并为一个复合元素的数组来改进空间局部性
~.循环交换(loop interchange): 通过改变循环嵌套来按序访问存储器中存储的数据
~.循环合并(loop fusion): 将两个具有相同循环类型且有一些变量重叠的独立循环合并
~.块化(blocking):通过不断使用一些数据块（而不是完整地遍历一行和一列）来改进时间局部性
————————————————
版权声明：本文为CSDN博主「MarsProbe」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/MarsProbe/article/details/1658540