# three-way handshake

* Step 1: In the first step, the client establishes a connection with a server. It sends a segment with SYN and informs
  the server about the client should start communication, and with what should be its sequence number.
* Step 2: In this step server responds to the client request with SYN-ACK signal set. ACK helps you to signify the
  response of segment that is received and SYN signifies what sequence number it should able to start with the segments.
* Step 3: In this final step, the client acknowledges the response of the Server, and they both create a stable
  connection will begin the actual data transfer process.

Here is a simple example of the three-way handshake process that is consists of three steps:
Host X begins the connection by sending the TCP SYN packet to its host destination. The packets contain a random
sequence number (For example, 4321) that indicates the beginning of the sequence numbers for data that the Host X should
transmit. After that, the Server will receive the packet, and it responds with its sequence number. It’s response also
includes the acknowledgment number, that is Host X’s sequence number incremented with 1 (Here, it is 4322). Host X
responds to the Server by sending the acknowledgment number that is mostly server’s sequence number that is incremented
by 1. After the data transmission process is over, TCP automatically terminates the connection between two separate
endpoints.

## Summary

* TCP 3-way handshake or three-way handshake or TCP 3-way handshake is a process which is used in a TCP/IP network to
  make a connection between server and client.
* Syn use to initiate and establish a connection
* ACK helps to confirm to the other side that it has received the SYN.
* SYN-ACK is a SYN message from local device and ACK of the earlier packet.
* FIN is used for terminating a connection.
* TCP handshake process, a client needs to initiate the conversation by requesting a communication session with the
  Server
* In the first step, the client establishes a connection with a server
* In this second step, the server responds to the client request with SYN-ACK signal set
* In this final step, the client acknowledges the response of the Server
* TCP automatically terminates the connection between two separate endpoints.

# 4-way handshake

4-Way Handshake is the sign that everything went smoothly during the transmission of the data and the data has been
reached its destination successfully. A four-Way handshake terminates the connection between sender and receiver so that
someone else can use the bandwidth for their data transmission. If the sender does not finish the connection then the
receiver will expect other packets from the sender and if does not receive then it may think that some error might have
occurred during the transmission.

At the end of the data transmission, the sender sends a “FIN” flag which tells the receiver that I have finished sending
the data you may now close the connection. the receiver sends the “ACK” flag which means that “Okay I have received all
of your data along with the FIN flag”. the receiver sends “FIN” flag to the sender which means that “Okay then if no
data is left let’s close the connection”. The sender sends “ACK” flag to know the receiver that “I have received your
FIN flag as well and now I am closing the connection from my side”. When the receiver receives the last “ACK” flag it
immediately closes the connection with the sender.

After the Four-Way Handshake, if the sender wants to send other data to the same receiver, it must repeat the process
from a 3-Way handshake.