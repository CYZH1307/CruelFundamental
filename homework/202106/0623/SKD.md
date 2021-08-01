ACK: Client sends TCP packet with SYN = 1, packet also indicates server IP, port, and initial sequence number X
SYN-ACK: Server sends back SYN-ACK (SYN = 1, ACK = 1), and make acknowledge number to be client's seuqnence number + 1, which is X + 1
ACK: Clients sends server packet ACK (SYN = 0, ACK = 1), and make sequence number to be acknowledge number + 1

After server sends SYN-ACK, server is in Syn_RECV, after server receives ACK, server goes to ESTABLISHED
