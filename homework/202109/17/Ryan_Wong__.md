# RedLock brief #
The naive key of Redis is efficient but gets a bug in the master-slave switch scene:  
Step One.The master-node got the lock;  
Step Two.The master-node coredumps before the lock is synced to the slave-node;  
Step Three.The slave-node is promoted to master and loses the lock.  
  
 When applying RedLock, the client and different nodes in the distributed server system(N sized) shared a same lock-ID(ID) and a same lock-expired-time(ET). 
 For each server system, only one client is allowed to hold the lock in a time.
 Only if the client and more than half nodes (>= N/2 + 1) get the lock in ET, the system can be seem as locked. Otherwise, the locking is failed and all the nodes must be unlocked ASAP. 
 If only M (N/2 + 1 <= M < N) nodes get the lock, the client will only communicate with them and ignore the rest (N - M) ones.
