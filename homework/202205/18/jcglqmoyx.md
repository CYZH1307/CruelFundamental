What are sticking and unpacking? The TCP protocol is a byte stream protocol with no record boundary. When we receive a
message, the packet that cannot be received artificially is an entire package message.

When a client sends multiple message data to a server, the TCP protocol may combine multiple message data into a single
packet for sending, which is called sticky packets.

When a client sends a message to a server that is too large, the tcp protocol may split a packet into multiple packets
to send it, which is unpacking.
***
How do we solve the problem of sticking and unpacking? One way is to use a specific separator in the string, and the
other way is to attach the length of the packet to the front when sending the packet.