# 什么是MVCC Multi-Version Concurrent Control？

## MVCC是什么：

它是一种在不加锁情况下实现高效事务并发的方法。每一条记录都保存若干条历史版本，任何事务来读取它时都按照事务本身的时间戳来选取对应的版本。相比必然会降低时间性能的加锁并发，MVCC属于用时间换空间，提升了数据库事务并发性能。

## MVCC实现方式：

同一个记录的多个版本使用链表的形式前后连接，称为“版本链”，有以下特点：
 
 - i.链表节点性质：版本链上的老版本以undo log的形式存在，既可以做回滚，又可以做MVCC。
 - ii.链表节点内部性质：每个版本都有trx_id（事务提交的时间戳，相当于版本序号）和roll pointer（相当于链表的next）。
 - iii.事务对版本的可见性：每个事务都会在执行时生成一个Read View，里面含有当前未提交的事务id列表list = {min_id, ..., max_id}。  
   - 1.若某个链表节点的trx_id < min_id，则必然可见；  
   - 2.若某个链表节点的trx_id > max_id，则必然不可见；  
   - 3.若某个链表节点的trx_id ∈ [min_id, max_id]且trx_id ∉ list，可见；  
   - 4.若某个链表节点的trx_id ∈ [min_id, max_id]且trx_id ∈ list，则视乎该节点的数据是否事务本身修改的，是则可见，否则不可见；
