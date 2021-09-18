# 延迟队列的实现方法

## 需求
- 下单半小时未支付，自动取消
- 订餐成功，一分钟后通知用户
- 新商家未上传商品信息，一个月自动冻结

## JDK - DelayQueue
- BlockingQueue 无界阻塞
- 封装的PriorityQueue，Delay最小排在最小堆顶
- 优先队列完全二叉树，完美2^N-1，完满节点无子女或俩
- put方法线程安全，重入锁，poll()非阻塞直接返回，tak()阻塞等待

## Quartz 定时任务
- spring-boot-starter-quartz
- @EnableScheduling
- @Scheduled(cron = "0/5 * * * *? ");

## Redis Sorted Set - Zset
- zadd delayqueue 3 order3
- zrem

## TODO: Redis 过期回调

## TODO-RabbitMQ

## TODO-时间轮
