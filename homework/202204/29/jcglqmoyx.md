A message queue is a component used to store packet of data that applications create for other applications to use. When
an application generates data and store data into a message queue, the message queue got enlarged, and when other
applications read information from the message queue, the message queue contracts.

Using message queue in a system has several benefits:

* It decouples different parts of a system thus makes the system more robust. When an error occurs in a service or an
  application, other services or applications won't be affected and can still receive messages from the message queue.
* It uses asynchronous messaging mechanism to prevent data loss. Suppose there is a large influx of requests coming in
  at some specific point, without a message queue a system have to process every single request one followed by another
  instantly, and this demands a great deal of computing resources. The system maybe fail to process so many requests in
  a short time and the client probably won't get their expected results.
