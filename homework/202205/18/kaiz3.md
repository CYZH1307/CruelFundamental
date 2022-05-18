## What's sticky packets?

When using TCP protocol to setup a connection between two hosts, sometimes host A sends two or more packets to host B separately but B receives receive a big packet which is the combination of those packets. 

## Reason

TCP is a reliable streaming protocol with retranmission of data if it's lost accross the network. If teach packet is small, it doesn't fill up the the buffer, TCP will combine multiple packets. Similarly, if the packet is larger than the send buffer, it will be unpacked and sent over multiple packets.

## Solution

Principle is to identify the boundary of each packet.

1. Define fixed packet size for each packet. If it's less than the predefined size, add special content. If it's larger than the fixed size, devide it to equal size.(potentially add special character to the last packet). This solution is not roburst.

2. Define a special indentifier/tail for each packet. At receiver side, use the special identifier to recongnize the packet.

3. In TCP packet head, it define the packet info, including the fixed size of packet head and the size of packet body.


