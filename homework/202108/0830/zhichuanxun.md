# 线程joinable/detach区别和实现

ref: https://www.huaweicloud.com/articles/12611751.html

线程是joinable 或者 detached，两种状态

## joinable

joinable 可以被其它线程回收杀死，并且资源不会被释放除非被join

## detached

detached不能被其它线程回收杀死，资源由系统回收


## others

默认joinable

error当join和detach都没被call的时候，terminate

