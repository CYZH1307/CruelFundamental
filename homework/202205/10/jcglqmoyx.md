# OSI model

We’ll describe OSI layers “top down” from the application layer that directly serves the end user, down to the physical
layer.

7. Application Layer

The application layer is used by end-user software such as web browsers and email clients. It provides protocols that
allow software to send and receive information and present meaningful data to users. A few examples of application layer
protocols are the Hypertext Transfer Protocol (HTTP), File Transfer Protocol (FTP), Post Office Protocol (POP), Simple
Mail Transfer Protocol (SMTP), and Domain Name System (DNS).

6. Presentation Layer

The presentation layer prepares data for the application layer. It defines how two devices should encode, encrypt, and
compress data so it is received correctly on the other end. The presentation layer takes any data transmitted by the
application layer and prepares it for transmission over the session layer.

5. Session Layer

The session layer creates communication channels, called sessions, between devices. It is responsible for opening
sessions, ensuring they remain open and functional while data is being transferred, and closing them when communication
ends. The session layer can also set checkpoints during a data transfer—if the session is interrupted, devices can
resume data transfer from the last checkpoint.

4. Transport Layer

The transport layer takes data transferred in the session layer and breaks it into “segments” on the transmitting end.
It is responsible for reassembling the segments on the receiving end, turning it back into data that can be used by the
session layer. The transport layer carries out flow control, sending data at a rate that matches the connection speed of
the receiving device, and error control, checking if data was received incorrectly and if not, requesting it again.

3. Network Layer

The network layer has two main functions. One is breaking up segments into network packets, and reassembling the packets
on the receiving end. The other is routing packets by discovering the best path across a physical network. The network
layer uses network addresses (typically Internet Protocol addresses) to route packets to a destination node.

2. Data Link Layer

The data link layer establishes and terminates a connection between two physically-connected nodes on a network. It
breaks up packets into frames and sends them from source to destination. This layer is composed of two parts—Logical
Link Control (LLC), which identifies network protocols, performs error checking and synchronizes frames, and Media
Access Control (MAC) which uses MAC addresses to connect devices and define permissions to transmit and receive data.

1. Physical Layer

The physical layer is responsible for the physical cable or wireless connection between network nodes. It defines the
connector, the electrical cable or wireless technology connecting the devices, and is responsible for transmission of
the raw data, which is simply a series of 0s and 1s, while taking care of bit rate control.
***

# TCP/IP model

The OSI Model we just looked at is just a reference/logical model. It was designed to describe the functions of the
communication system by dividing the communication procedure into smaller and simpler components. But when we talk about
the TCP/IP model, it was designed and developed by Department of Defense (DoD) in 1960s and is based on standard
protocols. It stands for Transmission Control Protocol/Internet Protocol. The TCP/IP model is a concise version of the
OSI model. It contains four layers, unlike seven layers in the OSI model. The layers are:

* Process/Application Layer
* Host-to-Host/Transport Layer
* Internet Layer
* Network Access/Link Layer

1. Network Access Layer – This layer corresponds to the combination of Data Link Layer and Physical Layer of the OSI
   model. It looks out for hardware addressing and the protocols present in this layer allows for the physical
   transmission of data. We just talked about ARP being a protocol of Internet layer, but there is a conflict about
   declaring it as a protocol of Internet Layer or Network access layer. It is described as residing in layer 3, being
   encapsulated by layer 2 protocols.

2. Internet Layer – This layer parallels the functions of OSI’s Network layer. It defines the protocols which are
   responsible for logical transmission of data over the entire network. The main protocols residing at this layer are :

* IP – stands for Internet Protocol and it is responsible for delivering packets from the source host to the destination
  host by looking at the IP addresses in the packet headers. IP has 2 versions:
  IPv4 and IPv6. IPv4 is the one that most of the websites are using currently. But IPv6 is growing as the number of
  IPv4 addresses are limited in number when compared to the number of users.

* ICMP – stands for Internet Control Message Protocol. It is encapsulated within IP datagrams and is responsible for
  providing hosts with information about network problems.

* ARP – stands for Address Resolution Protocol. Its job is to find the hardware address of a host from a known IP
  address. ARP has several types: Reverse ARP, Proxy ARP, Gratuitous ARP and Inverse ARP.

3. Host-to-Host Layer – This layer is analogous to the transport layer of the OSI model. It is responsible for
   end-to-end communication and error-free delivery of data. It shields the upper-layer applications from the
   complexities of data. The two main protocols present in this layer are :

* Transmission Control Protocol (TCP) – It is known to provide reliable and error-free communication between end
  systems. It performs sequencing and segmentation of data. It also has acknowledgment feature and controls the flow of
  the data through flow control mechanism. It is a very effective protocol but has a lot of overhead due to such
  features. Increased overhead leads to increased cost.
* User Datagram Protocol (UDP) – On the other hand does not provide any such features. It is the go-to protocol if your
  application does not require reliable transport as it is very cost-effective. Unlike TCP, which is connection-oriented
  protocol, UDP is connectionless.

4. Application Layer – This layer performs the functions of top three layers of the OSI model: Application, Presentation
   and Session Layer. It is responsible for node-to-node communication and controls user-interface specifications. Some
   of the protocols present in this layer are: HTTP, HTTPS, FTP, TFTP, Telnet, SSH, SMTP, SNMP, NTP, DNS, DHCP, NFS, X
   Window, LPD. Have a look at Protocols in Application Layer for some information about these protocols. Protocols
   other than those present in the linked article are :

* HTTP and HTTPS – HTTP stands for Hypertext transfer protocol. It is used by the World Wide Web to manage
  communications between web browsers and servers. HTTPS stands for HTTP-Secure. It is a combination of HTTP with SSL(
  Secure Socket Layer). It is efficient in cases where the browser need to fill out forms, sign in, authenticate and
  carry out bank transactions.
* SSH – SSH stands for Secure Shell. It is a terminal emulations software similar to Telnet. The reason SSH is more
  preferred is because of its ability to maintain the encrypted connection. It sets up a secure session over a TCP/IP
  connection.
* NTP – NTP stands for Network Time Protocol. It is used to synchronize the clocks on our computer to one standard time
  source. It is very useful in situations like bank transactions. Assume the following situation without the presence of
  NTP. Suppose you carry out a transaction, where your computer reads the time at 2:30 PM while the server records it at
  2:28 PM. The server can crash very badly if it’s out of sync.
