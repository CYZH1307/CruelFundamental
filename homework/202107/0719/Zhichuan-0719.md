# Redis Mass Insert

> https://redis.io/topics/mass-insert
> https://redis.io/topics/pipelining

## RTT

redis is a TCP server and a C/S mode(request/response protocol)

```example
-> ping
<- pong
-> ping
<- pong
-> ping
<- pong
```

either localhost(0.044ms) or not(250ms) consumes time

## Protocol

generate a file matches redis protocol

```example
SET Key0 Value0
SET Key1 Value1
...
SET KeyN ValueN
```

## Pipelining

version >= 2.6

server can handle requests even if the client hasn't already read the old responses.

```example
-> ping\nping\nping
<- pong\npong\npong
```

> redis has to queue the pipelining using memory, so it is better to divide them into buckets