Solution:
TCP uses the congestion window (cwnd) maintained by the sender.


Algorithm: 
Slow start and congestion avoidance algorithms will be used by TCP sender. (preconnection state)
Congestion window: limit the amount of data the sender can transmit into the nextwork before receiving an ack.
Receiver's Advertised Window (rwnd) is a receiver-side limit on amount of outstanding data.
Slow start threshold (sshresh) is used to determine whether the slow start or congestion avoidance algorithm is used to control data transmission.

TCP probe the network with slow start algorithom at the start / after repairing loss detected.
slow start initiates ack clock in the slow start, congestion avoidance and loss recover algorithoms.

cwnd initialization: (old seg size) / (new seg size)
ssthresh initial as very high but reduced in response to congestion.

slow start: cwnd > ssthresh
congestion avoidance: cwnd > ssthresh
when == , the sender may use either on
