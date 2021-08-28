Redis Consistent Hashing Algorithm

Consistent hashing:
1. It represents the resource requestors and server nodes are known as hashring.
2. The number of locations is no longer fixed, but the ring is considered to have an infinite number of points and server nodes can be placed at random locations on this ring.
3. The requests (users, computers or serverless programs) are analogous to key in classic hashing approach are also placed on the same ring using the same function.


Each server node 'owns' a range of hashring and any requests coming in at this range will be served by the same server node. 

If a node fails, the range of the next server node widens and request coming in all of this range, goes to the new server node. 

The classic hashing technique in which the change in size of the hash table effectively disturbs all of the mapping. Thanks to the consistent hashing, only a portion of the requests will be affected by a given ring change.


The implementation of consistent hashing: 
mapping --> add / remove a node --> find the requests affected by a ring change --> find affected hashes

