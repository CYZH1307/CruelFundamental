fast retransmit

TCP: timeout-triggered retransmission.

# problem
- tcp retransmission timeout could be too long
- if a segement is lost, the sender has to wait to resend -> incr delay

# observation
- in TCP, msg has seq num, and is sent/recved with incr seq num
- if a msg is recved, recver respond ACK to the sender
- the recver can possibly send duplicate ACKs to the sender
  - why? because recver want to recv msg in order, but there may be gap btw msgs' seq num
  - since there are many msg transmitted, if one is lost, the recver respond many duplicated ACKs to the sender

# algorithm
- if the sender has >= 3 ACKs for that has the next={ID}, then it knows that msg with seq num = {ID} has lost

# conclusion
- no need for timeout, sender can detect lost msg now!
