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
- IP protocol
- transport-layer packet -> __datagram__

## Link Layer
- delivers the datagram to the next node along the route
- Ethernet, WiFi
- as datagrams typically need to traverse several links to travel from source to destination, a datagram may be handled by different link-layer protocols at different links along its route.
- link-layer packet -> __frame__

## Physical Layer
- move the individual bits within the frame from one node to the next

# The OSI Model
Proposed by ISO, Open Systems Interconnection, Not widely used today, but still lingering on textbooks
- application layer
- presentation layer
- session layer
- transport layer
- network layer
- data link layer
- physical layer

## Representation Layer
- provide services that allow communicating applications to interpret the meaning of data exchanged, including data compression & encryption

## Session Layer
- for delimiting and synchronization of data exchange, including the means to build a checkpointing and recovery scheme
