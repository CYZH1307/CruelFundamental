ref: https://github.com/x-wqs/CruelFundamental/blob/main/homework/202112/22/monsooooon.md#network-layer

# Transport Layer
- TCP
  - stream, break large data into shorter segments
  - connection-oriented
  - guaranteed delivery
  - flow control (window) , congestion control (slow start, exp backoff)
- UDP 
  - packet
  - connectionless
  - best effort (no reliability, no flow control)
- transport-layer packet -> __segment__

# Network Layer
- responsible for moving network-layer packets known as datagrams from one host to another. from source host to target host
- IP protocol, network address (IPv4, IPv6)
- router use IP address to route datagram, with some routing protocols
- transport-layer packet -> __datagram__
