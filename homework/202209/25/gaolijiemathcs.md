### 什么是检查点？（数据库）

检查点是一个数据库事件，将修改的数据从高速缓存写入磁盘，并且更新控制文件和数据文件。

检查点分为三类：

- 局部检查点：单个实例执行数据库所有数据文件的一个检查点操作，属于此实例的全部脏缓存区写入数据文件。
  - 触发命令： svmrgrl>alter  system checkpoint local; 
  - 这条命令显示的触发一个局部检查点。

- 全局检查点：所有实例（对应并行数据  服务器）执行数据库所有所有数据文件的一个检查点操作，属于此实例的全部脏缓存区写入数据文件。
  - 触发命令 svrmgrl>alter system checkpoint global;
  - 这条命令显示的触发一个全局检查点。

- 文件检查点：所有实例需要执行数据文件集的一个检查点操作。
  - 热备份命令：alter   tablespace USERS begin backup，或表空间脱机命令alter tablespace USERS offline。
  - 将执行属于USERS表空间的所有数据文件的一个检查点操作。

ref: blog.51cto.com/itbull/1172734