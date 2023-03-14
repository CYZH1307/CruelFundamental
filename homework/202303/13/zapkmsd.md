- 为什么TIME_WAIT等待时间是2MSL？

Maximun Segment Lifetime (MSL)

The reasons for the TIME_WAIT state and its 2MSL wait time are to ensure that the remote host has received the final ACK confirmation message and to prevent old connection requests from interfering with new ones.