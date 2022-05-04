* Group messages by unique session ids, create multiple queues in memory and insert messages with the same session id
  into one queue. When consuming messages, we let a consumer read messages from its corresponding queue with the
  specific session id.