# Linux查询tcp连接处理CLOSE_WAIT的状态的数目
- netstat -anp | grep CLOSE_WAIT | wc
- https://www.cnblogs.com/cangqinglang/p/13185825.html

## TCP连接状态
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
