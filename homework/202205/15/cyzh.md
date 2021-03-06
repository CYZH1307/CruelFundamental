## 2022/05/15

### TCP怎么保证传输过程的可靠性?

#### 校验和

每个数据段的每16加起来，然后取反，得到校验和

接收方收到后，对校验和进行比对

#### 序号和确认号

- **序号**：一个报文段的序号，应该是该报文段的首字节的字节流编号
- **确认号**： TCP 中并没有给出处理失序报文的明确规定
  - 接收方丢弃失序报文段
  - 接收方保留失序报文段
- 初始序号随机化：假定两台主机中存在两次连接（恰好两次连接采用同样的端口号），前一个连接的确认号会影响后一个连接

#### 流量控制

TCP的接收方有一个缓存区，并不是接收数据后直接读取数据，当发送方发送的数据太快太多时，缓存区可能溢出。

TCP会通过让接收方维护一个**接收窗口**（receive window）的变量来提供流量控制

- **LastByteRead**:主机上的应用进程从缓存读出的数据流的最后一个字节编号
- **LastByteRecv**:从网络中到达的并且已经放入主机B接收缓存中的数据流的最后一个字节编号

$LastByteRevd - LastByteRead <= RevBuffer$

接收窗口用$rwnd$来表示：$rwnd = RevBuffer - (LastByteRevd - LastByteRead)$

rwnd 一直是变化的，主机B一直会将rwnd的值放在接收报文中，让A知道rwnd的值

当rwnd = 0时，主机A会持续发送只有1个字节数据的报文段，这些报文被B（接收方）接收确认后会返回rwnd的新值