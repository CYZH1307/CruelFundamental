# Linux查询tcp连接处理CLOSE_WAIT的状态的数目
- netstat -anp | grep CLOSE_WAIT | wc
- https://www.cnblogs.com/cangqinglang/p/13185825.html

## TCP连接状态
- CLOSE_WAIT，被动关闭，对方发出关闭信号，或者网络异常，然后本机状态进入CLOSE_WAIT
- TIME_WAIT，主动关闭，本地主动调用close()，在收到对方结束信号后，进入TIME_WAIT状态

| Action \ Role     | Client                | Server             | Notes |
|-------------------|-----------------------|--------------------|-------|
| 3-way handshake   |                       | CLOSED             |       |
|                   |                       | LISTEN             |       |
|                   | CLOSED, SYN, SYN_SENT |                    |       |
|                   |                       | SYNC_RCVD, SYN_ACK |       |
|                   | ESTABLISHED, ACK      |                    |       |
|                   |                       | ESTABLISHED        |       |
|                   |                       |                    |       |
| Data Transfer     | DATA                  |                    |       |
|                   |                       | ACK                |       |
|                   |                       | DATA               |       |
|                   | ACK                   |                    |       |
|                   |                       |                    |       |
| 4-way termination | FIN_WAIT_1, FIN       |                    |       |
|                   |                       | CLOSE_WAIT, ACK    |       |
|                   | FIN_WAIT_2            |                    |       |
|                   |                       | LAST_ACK, FIN      |       |
|                   | TIME_WAIT, ACK        |                    |       |
|                   |                       | CLOSED             |       |
|                   | CLOSED                |                    |       |
|                   |                       |                    |       |
