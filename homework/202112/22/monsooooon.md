# Five-Layer Internet Protocol Stack
When taken together, the protocols of the various layers are called the __protocol stack__.  
The Internet protocol stack consists of five layers: 
- physical layer (物理层)
- link layer (链路层)
- network layer (网络层)
- transport layer (传输层)
- application layer (应用层)

## Application Layer
- where network applications and their application-layer protocols reside.
- HTTP(web content r/w), SMTP(email), FTP(file trans), DNS, custom protocols
- application-layer packet -> __message__

## Transport Layer
- TCP(stream, connection-oriented), UDP (packet, connectionless)
- transport-layer packet -> __segment__

## Network Layer
- responsible for moving network-layer packets known as datagrams from one host to another.
- transport-layer packet -> __datagram__

## Transport Layer
