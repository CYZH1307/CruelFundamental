# Rabbit原理 #
Rabbit是一种分布式系统的消息传递系统，基于队列实现，实际结构类似于路由器+队列，名称是Exchange & Queue。

    1.Exchange接收从生产者来的信息，Queue供消费者拿信息。
    2.每个Queue有唯一的路由标记ROUTE_KEY，供Exchange调用以push消息，也供消费者订阅用以pop消息。
    3.每个Exchange都有一个路由策略，包括但不限于：  
        i.全量推送给所有预绑定的Queue
        ii.精确ROUTE_KEY匹配Queue推送
        iii.ROUTE_KEY模式匹配Queue推送
        iv.header内容匹配推送
    
    4.Exchange可以和Queue进行预绑定。
