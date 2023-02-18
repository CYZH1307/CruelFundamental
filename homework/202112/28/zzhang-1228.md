# TCP 滑动窗口、拥塞控制

TCP协议的滑动窗口：

- 16 位字段，标准窗口大小为2^16=65535个子节。TCP选项字段中包含一个TCP窗口扩大因子，可以用来扩大TCP窗口。

- 分为接受窗口和发送窗口 => TCP是双工协议，会话的双方都可以同时接受、发送数据，因此会话的双方都各自维护一个发送窗口和一个解说窗口。

- 接收端窗口只有在前面所有的段都确认的情况下才会移动左边界。收到窗口后面的字节，不会ACK，确保发送端会对这些数据重传。

- 发送窗口只有收到对方对于本段发送窗口内字节的ACK，才会移动发送窗口的左边节。

  

TCP协议的拥塞控制

1. 慢启动 slow start: 
   - 初始化拥塞窗口为1 => 每当收到一个ACK，window size +1，线性上升 => 没当过了一个RTT，window size 乘2，指数上升 => 到达threshold，采用拥塞避免算法
2. 拥塞避免 congestion avoidance
   - 收到一个ACK，window size 加 1/windowsize => 每过一个RTT，window size + 1
3. 拥塞发生
   - 认定进入拥塞状态：超时重传 或 收到3个重复ACK
   - 超时重传：将慢启动threshold设置为当前的一半；window size设置为1，进入慢启动
   - 收到3个重复ACK => *快速重传*：window size 设置为当前的一半，慢启动threshold设置为缩小后的window size，进入快速恢复算法
4. 快速恢复 fast recovery
   - windowsize 加 3 * MSS, [MSS: maximum segment size] => 重传DACKs制动的数据包 => 若再收到DACKs，window size + 1；如果收到新的ACK，表明重传的包成功，退出快速恢复算法，将window size设置为threshold，进入拥塞避免算法。

