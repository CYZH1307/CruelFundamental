> 会话层有什么作用？表示层有什么作用？应用层有什么作用？

In OSI Model: Application -> __Presentation -> Session__ -> Transport -> Network ...

# Application layer
- in most cases, directly used by network applications (programmers)
- HTTP, FTP, SMTP, DNS, etc. (a lot number)
- how to create and deploy our own new application-layer protocol?

# Presentation layer
- used by application layer to provide an easy to use data model
- functionalities: data compression, data encryption, etc.
- not very related to biz logic in application layer

# Session layer
- delimiting and synchronization of data exchange, including the means to build a checkpointing and recovery scheme.

