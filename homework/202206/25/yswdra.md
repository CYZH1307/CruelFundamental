### C++: new/delete和malloc/free之间有什么关系？delete和delete[]有什么区别？使用malloc申请的内存能否通过delete释放？使用new申请的内存能否使用free释放?

------

new、delete底层调用了malloc和free来实现空间得分配和释放，new、delete屎malloc、free的上层封装，使得使用更加便捷。
delete是按照指针类型删除其指向的空间，而delete[]会通过读取指针所指内容处相邻地方所存（也可能存在别的地方）的信息，释放若干个该类型的空间。
malloc申请的内存可以通过delete释放，new申请的内存可以由free释放。