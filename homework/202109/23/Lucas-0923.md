# Linux 子进程
- https://www.jianshu.com/p/7620f5a49794
- https://www.cnblogs.com/anker/p/3271773.html

## 命令
- 查询进程信息，ps -ef
- 进程子进程，pstree
- 结束进程，kill -9

## 结束父进程，子进程也退出
- kill？

## 子进程被 init 领养，成为孤儿进程
- 父进程直接退出，子进程睡眠5秒

## 子进程变成僵尸
- 子进程直接退出
- 父进程等待2秒
-
## 僵尸进程解决办法
- 子进程退出，发送SIGCHILD
- 父进程捕捉信号，等待僵尸进程
- TODO: fork 两次


