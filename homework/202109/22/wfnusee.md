# why kafka removing zookeeper
reference: https://www.confluent.io/blog/removing-zookeeper-dependency-in-kafka/

- Make Operation work more complex; You will need to maintain one more cluster
- Many duplicate work in the system; monitor | network | security ...
- Not efficient... additional java thread
- Limit the scalability... Need to load full state in ZK
- Limit the performance... ZooKeeper means **strong consistence**
