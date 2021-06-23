# 0623 - TCP Three-way Handshake
- https://en.wikipedia.org/wiki/Transmission_Control_Protocol

## Connection Establishment
- server will bind to a port and list before connection
- server will establish passive open, and client will initiating an active open
- client will use three-way / 3-step handshake

## 1. SYNC
- client will end a SYNC to server to perform active open
- client will use a random value A for the segment sequence number

## 2. SYNC-ACK
- server will reply SYNC-ACK in the respnose
- server will set A + 1 to the acknowledgment number.
- server will choose another random number B for the sequence number

## 3. ACK
- client will send ACK to server
- client will set A + 1 to the sequence number
- client will set B + 1 to the acknowledgment number

## Summay
- step 1 & 2 wil establish and acknowledgment number for one direction
- step 2 & 3 will do the same for the other direction
- both will receive acknowledgments
- it will establish a full duplex communication
