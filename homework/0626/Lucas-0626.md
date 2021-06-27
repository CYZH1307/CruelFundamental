# Java内存模型

## 分类
- 方法区：线程共享，存放类信息，常量，静态变量，JIT代码
- 堆：线程共享，存放对象和数组，由回收器管理
- 方法栈：线程私有，存储局部变量，函数栈，动态链接？
- 本地方法栈：线程私有，使用C/C++接口
- 程序计数器：线程私有，存放字节码行号

## 方法区
- 永久代，Java 8中不存在
- -XX:PermSize最小方法区大小，-XX:MaxPermSize最大方法区大小
- 常量池回收，卸载类型

## 堆
-Xms最小堆空间大小，-Xmx最大堆空间大小

### 新生代 Young Generation
- --XX:NewSize最小新生代大小，-XX:MaxNewSize最大新生代大小
- --Xmn新生代大小
- --XX:NewRatio新生代和老年代的大小比例，默认1/15
- Minor GC，复制算法

#### Eden 伊甸园
- 对象初始分配区

#### From Survivor, 幸存者空间，S0 / S1
- -XX:SurvivorRatio，幸存区和伊甸园大小比例
- 在伊甸园没被回收，将移入幸存区
- 每次Minor GC，移入另外一块幸存区
- 每熬过一次Minor GC，年龄+1，15岁移入老年区

### 老年代 Old Generation
- Major/Full GC，标记清除法

## 方法栈，元空间？
- -Xss线程栈大小

