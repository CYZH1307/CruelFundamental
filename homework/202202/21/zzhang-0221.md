什么是五级流水线，数据冒险是什么

五级流水线：

1. IF: Instruction Fetch 取指

2. ID: Instruction Decode 译码

3. EX: Execute 执行

4. MEM: Memory Access  内存数据读或者写

5. WB: Write Back 数据写回通用寄存器中



数据冒险：

- 指令乱序执行时，可能会发生读取数据与写入数据之间的时序与空间的相关性，成为数据冒险
- 三种数据冒险的可能性以及解决方案
  - Read after Write => 写指令和读指令之间插入流水线冒泡，Register forwarding 来使用流水线最新的计算结果
  - Write after Read 
  - Write after Write 

- 例子

  ```
  R2 <- R1 + R3
  R4 <- R2 + R3
  ```

  指令2依赖于指令1写入R2的值。

