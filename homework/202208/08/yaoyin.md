# 什么是 TIME_WAIT 状态，为什么需要 TIME_WAIT 状态？时间是多久，为什么？

After client sent FIN and server sendt ACK and FIN, client turned into TIME_WAIT status and sent ACK to the server. It will stay for 2MSL.

Reasons:

- To implement TCP's full-duplex connection termination reliably
  - The last ACK sent by the client might be lost. In that case, the server will retransmit FIN. In order to receive this FIN, client has to wait for a while.
  - 2MSL is not really neccessary. But in consideration of the worst case, the RTO is 2MSL, then 2MSL as TIME_WAIT status could ensure the client could receive the retransmitted FIN.
- To allow old duplicate segments to expire in the network.
  - This could ensure that when we establish new connections, the old packets have gone.