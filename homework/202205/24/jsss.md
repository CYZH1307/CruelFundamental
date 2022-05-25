# 进程间通信方式

进程之间为了**同步交互**, 就需要进行进程之间的通信, 由于每个进程拥有独立的虚拟地址空间, 并且通过进程对应的页表记录虚拟页和物理内存页的映射. 因此需要提供额外的资源, 这份资源可以让目标进程获取以及操作来帮助不同进行之间完成通信. 而根据提供资源方法的不同, 造成了不同的进程间通信方式.

## 匿名管道

**匿名管道**是进程之间通信的方式之一. 其方式是创建一个匿名管道**实现父子\兄弟**进程通信, 通过内核缓冲区提供这份公开的资源用于通信. 操作系统实现对这块缓冲区的管理. 使得写进程只管写, 读进程只管读, 屏蔽了底层的具体实现过程.

### 主要特点

- 实现父子\兄弟进程之间的通信
- 单向通信
- 面向字节流的服务
- 依赖文件系统
- 管道内部保持同步机制
- 依附于进程, 无法单独存在

### 测试样例

```cpp
#include <stdio.h>
#include <unistd.h>
#include <string.h>

#define BUFFSIZE 1024

char buff[BUFFSIZE];

int main() {
    // 文件描述符: fd[0]指向管道的读端,fd[1]指向管道的写端。fd[1]的输出是fd[0]的输入
    int fd[2];
    if (pipe(fd) == -1) {
        fprintf(stderr, "pipe error!");
        return -1;
    }
    // fork进程
    pid_t pid = fork();

    if (pid == 0) {
        // 子进程关闭写
        close(fd[1]);
        while (1) {
            char* msg = "read from father process: ";
            write(1, msg, strlen(msg));
            int s = read(fd[0], buff, BUFFSIZE - 1);
            if (s <= 0)
                break;
            // 将读入的数据输出到标准输出
            write(1, buff, s);
        }
        // 结束读
        char* readone = "son process read done.\n";
        write(1, readone, strlen(readone));
    } else {
        // 父进程关闭读
        close(fd[0]);
        int s = 0;
        // 从标准输入读入: 0-标准输入, 1-标准输出, 2-标准错误
        while (s = read(0, buff, BUFFSIZE - 1), buff[0] != '\n') {
            char* msg = "write to son process: ";
            write(1, msg, strlen(msg));
            write(fd[1], buff, s);
        }
        // 结束写
        char* writedone = "father process write done.\n";
        write(1, writedone, strlen(writedone));
    }

    return 0;
}

/* 输出
read from father process: Hi, Jsss ~
write to son process: Hi, Jsss ~
read from father process: Work Hard !
write to son process: Work Hard !
read from father process: Happy Life ~
write to son process: Happy Life ~
read from father process: Good Bye ~
write to son process: Good Bye ~
read from father process: 
father process write done.
son process read done.
*/
```

## 命名管道

匿名管道的局限在于通信进程只能是具有血缘关系的进程. 而更常见的情况是需要通信的双方不含有血缘关系. 针对这种情况, **命名管道**就发挥作用了.

我们可能会比较熟悉Linux中常用的**管道符** `|`. 它能将前一个进程的输出作为输入传入下一个进程, 快速的完成我们的一些需求. 如:

- 在某文件中查找特定字符及其位置.

```bash
# cat 输出文件内容作为 grep的输入
$ cat ubuntu_setup_env.sh | grep sudo -n
8:SUDOERS_FILE=/etc/sudoers
11:sudo apt-get update
14:sudo apt remove -y --purge openssh-server
15:sudo apt install -y openssh-server
18:sudo apt install -y cmake gcc clang gdb valgrind build-essential
21:sudo cp $SSHD_FILE ${SSHD_FILE}.`date '+%Y-%m-%d_%H-%M-%S'`.back
22:sudo sed -i '/^Port/ d' $SSHD_FILE
23:sudo sed -i '/^ListenAddress/ d' $SSHD_FILE
24:sudo sed -i '/^UsePrivilegeSeparation/ d' $SSHD_FILE
25:sudo sed -i '/^PasswordAuthentication/ d' $SSHD_FILE
26:echo "# configured by CLion"      | sudo tee -a $SSHD_FILE
27:echo "ListenAddress ${SSHD_LISTEN_ADDRESS}"  | sudo tee -a $SSHD_FILE
28:echo "Port ${SSHD_PORT}"          | sudo tee -a $SSHD_FILE
29:echo "UsePrivilegeSeparation no"  | sudo tee -a $SSHD_FILE
30:echo "PasswordAuthentication yes" | sudo tee -a $SSHD_FILE
32:sudo service ssh --full-restart
35:sed -i '/^sudo service ssh --full-restart/ d' ~/.bashrc
36:echo "%sudo ALL=(ALL) NOPASSWD: /usr/sbin/service ssh --full-restart" | sudo tee -a $SUDOERS_FILE
40:  sudo service ssh --full-restart
```

- 查看进程的状态

```bash
$ ps aux | grep ssh
root       377  0.0  0.0  12180  3068 ?        Ss   19:21   0:00 sshd: /usr/sbin/sshd [listener] 0 of 10-100 startups
jsss       768  0.0  0.0   8164   728 pts/5    S+   20:06   0:00 grep --color=auto ssh
#USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
```

### 主要特点

- 单向通信
- 支持任意进程间通信
- 依赖于文件系统

### 测试样例

```cpp
// read.c
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/stat.h>
#include <fcntl.h> 

#define BUFFSIZE 1024

char buff[BUFFSIZE];

int main() {
    // 从管道读取
    FILE* fp = fopen("fifo", "r");
    if (fp == NULL) {
        fprintf(stderr, "fopen error");
        return -1;       
    }

    while (fgets(buff, BUFFSIZE - 1, fp) != NULL)
        printf("read from FIFO: %s\n", buff);
    fclose(fp);

    printf("read over!");
    return 0;
}
```

```cpp
// write.c
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include<sys/types.h>
#include <sys/stat.h>
#include <fcntl.h> 

#define BUFFSIZE 1024

char buff[BUFFSIZE];

int main() {
    // 文件必须不存在
    int st = mkfifo("fifo", 0644);
    if (st != 0) {
        fprintf(stderr, "mkfifo error\n");
        return -1;
    }
    // 写入管道
    FILE* fp = fopen("fifo", "w");
    if (fp == NULL) {
        fprintf(stderr, "fopen error\n");
        return -1;       
    }

    for (int i = 10; i <= 20; i ++ )
        fprintf(fp, "cur = %d\n", i);

    fclose(fp);
    printf("write over!\n");
    return 0;
}
```

```bash
# 先写后读, 运行结果
read from FIFO: cur = 10

read from FIFO: cur = 11

read from FIFO: cur = 12

read from FIFO: cur = 13

read from FIFO: cur = 14

read from FIFO: cur = 15

read from FIFO: cur = 16

read from FIFO: cur = 17

read from FIFO: cur = 18

read from FIFO: cur = 19

read from FIFO: cur = 20

$ ls -al
prw-r--r-- 1 jsss jsss     0 Feb 28 22:20 fifo
# fifo的文件类型为p : 即命名管道文件
```

## 信号量

**信号量**是本质上是一个计数器, 用于控制多个共享进程对临界资源的访问. 信号量是操作系统内核所处理的, 因此不同的进程都可以访问的到信号量及其值. 从而实现多进程间的同步.

信号量主要支持两种操作, 且这两种操作均为**原子**操作, 即不会被打断: 
- `P`操作. `P`操作是**申请资源**, 让信号量的值减1. 如果信号量值大于0, 则申请成功; 如果值为小于等于0, 就阻塞等待, 直到信号量值大于0被唤醒.
- `V`操作. `V`操作是**释放资源**, 让信号量的值加1. 如果有进程被信号量阻塞, 则唤醒; 如果没有则给信号量值加1.

### 主要特点

- 操作系统提供了一组关于信号量的同步访问临界资源的函数
- 一般和共享内存方法搭配使用进行进程间通信
- 初始值为1的信号量可以看做互斥锁. 因为同一时刻最多一个进程可以申请持有该信号量.
- 需要**显式删除**. 可以在代码中使用`semctl`函数删除. 也可以在终端下使用`ipcrm -s`命令删除.

### 测试样例

测试样例和共享内存一起在下面给出, 是一个简单的**生产者-消费者**模型.


## 共享内存

**共享内存**允许多个进程共享同一个物理内存区域以实现进行之间的通信. 其核心思路是不同进程的页表中包含了同一块物理内存, 而虚拟内存页可以是不同的. 基本流程是先向内核申请一块共享内存区域, 接着将该内存区域映射到进程的虚拟地址空间, 从而实现不同进程操作同一块共享内存区域, 实现进程间通信.

### 主要特点

- 直接在内存中操作, 速度较快
- 支持任意进程间通信
- 未提供同步机制, 需要自行利用信号量等实现进程间同步
- 支持双向通信
- 需要**显式删除**. 可以在代码中使用`shmctl`函数删除. 也可以在终端下使用`ipcrm -m`命令删除.

### 测试样例

```cpp
// shmwrite
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <stdlib.h>
#include <time.h>

// 缓冲区大小
#define MAXN 10
// 生产者生产总数
#define ALL 20

// 临界资源
typedef struct buff {
    int arr[MAXN];
    int finish;
    int curIdx;
} Buff;


// 用作初始化信号量的结构
union semun{  
    int val;  //使用的值
    struct semid_ds *buf;  // IPC_STAT、IPC_SET 使用的缓存区
    unsigned short *arry;  // GETALL、SETALL 使用的数组
    struct seminfo *__buf; // IPC_INFO(Linux特有) 使用的缓存区 
}; 


// 信号量初始化为给定值
int initSem(int semid, int value);

// P操作函数
int P(int semid);

// V操作函数
int V(int semid);

// 获取信号量值
int getSem(int semid);

// 删除信号量
int deleteSem(int semid);


int main() {
    // 创建一块共享内存, 返回共享内存标识. 参数key_t是长整型（唯一非零）, 系统建立IPC通讯 （ 消息队列、 信号量和 共享内存） 时必须指定一个ID值.
    int shmid = shmget((key_t)9527, sizeof(Buff), IPC_CREAT | 0644);
    if (shmid == -1) {
        fprintf(stderr, "create shared memory error!\n");
        return -1;
    }
    // 将共享主存attach到当前进程, 即映射到当前进程的虚拟内存空间
    // shm指针指向共享内存区
    Buff* shm = (Buff*)shmat(shmid, 0, 0);
    shm -> finish = 0, shm -> curIdx = -1;

    if ((void*)shm == (void*)-1) {
        fprintf(stderr, "shmat error!\n");
        return -1;
    }

    /* 
        创建信号量用于同步临界区代码. 参数key是整数值（唯一非零），不相关的进程可以通过它访问一个信号量，
        它代表程序可能要使用的某个资源，程序对所有信号量的访问都是间接的，程序先通过调用semget()函数并提供一个键，
        再由系统生成一个相应的信号标识符（semget()函数的返回值），只有semget()函数才直接使用信号量键，
        所有其他的信号量函数使用由semget()函数返回的信号量标识符。如果多个程序使用相同的key值，key将负责协调工作。
        empty 记录空缓冲区的数量，full 记录满缓冲区的数量
    */
    int semid_empty = semget((key_t)99999, 1, IPC_CREAT | 0644);
    int semid_full = semget((key_t)66666, 1, IPC_CREAT | 0644);
    int semid_mutex = semget((key_t)12345, 1, IPC_CREAT | 0644);

    // 初始化
    if (initSem(semid_empty, MAXN) == -1 || initSem(semid_full, 0) == -1 || initSem(semid_mutex, 1) == -1) {
        fprintf(stderr, "init sem error!\n");
        return -1;
    }

    int cnt = 0;

    while (cnt < ALL) {
        // 先检查是否可以生产
        P(semid_empty);
        // 申请互斥锁mutex进行生产
        P(semid_mutex);

        // 临界区
        srand((unsigned int)time(NULL));
        ++ (shm -> curIdx);
        shm -> arr[shm -> curIdx] = cnt;
        ++ cnt;
        printf("produce cur-val = %d,  curIdx = %d, empty size = %d\n", cnt, shm->curIdx, getSem(semid_empty));

        // 释放锁以及一块满缓冲区
        V(semid_mutex);
        V(semid_full);
    }

    // 等待消费者消费完成
    while (!(shm -> finish)) {}

    //把共享内存从当前进程中分离
    if (shmdt(shm) == -1) {
        fprintf(stderr, "shmdt error!\n");
        return -1;
    }

    // 删除共享内存
    if (shmctl(shmid, IPC_RMID, 0) == -1) {
        fprintf(stderr, "shmctl error!\n");
        return -1;
    }

    // 删除信号量
    if (deleteSem(semid_full) == -1 || deleteSem(semid_empty) == -1 || deleteSem(semid_mutex) == -1) {
        fprintf(stderr, "deleteSem error!\n");
        return -1;
    }

    printf("write done!\n");
    return 0;
}

int initSem(int semid, int value) {
    // 信号量初始化
    union semun sem;
    sem.val = value;

    // semctl函数使用 SETVAL 初始化信号量
    if (semctl(semid, 0, SETVAL, sem) == -1 )  
        return -1;          
    return 0;
}

int P(int semid) {
    struct sembuf sem;
    sem.sem_num = 0;    // 信号量编号为0
    sem.sem_op = -1;   // -1 表示 P操作
    sem.sem_flg = SEM_UNDO; // 通常设置为SEM_UNDO,使操作系统跟踪信号量，并在进程没有释放该信号量而终止时，操作系统释放信号量

    if (semop(semid, &sem, 1) == -1) 
        return -1;
    return 0;
}

int V(int semid) {
    struct sembuf sem;
    sem.sem_num = 0;    // 信号量编号为0
    sem.sem_op = 1;   // -1 表示 P操作
    sem.sem_flg = SEM_UNDO; // 通常设置为SEM_UNDO,使操作系统跟踪信号量，并在进程没有释放该信号量而终止时，操作系统释放信号量

    if (semop(semid, &sem, 1) == -1) 
        return -1;
    return 0;    
}

int getSem(int semid) {
    int ret = semctl(semid, 0, GETVAL);
    return ret;
}

int deleteSem(int semid) {
    union semun sem;
    if (semctl(semid, 0, IPC_RMID, sem) == -1)
        return -1;
    return 0;
}
```


```cpp
// shmread
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h> 

// 缓冲区大小
#define MAXN 10
// 生产者生产总数
#define ALL 20

// 临界资源
typedef struct buff {
    int arr[MAXN];
    int finish;
    int curIdx;
} Buff;


// 用作初始化信号量的结构
union semun{  
    int val;  //使用的值
    struct semid_ds *buf;  // IPC_STAT、IPC_SET 使用的缓存区
    unsigned short *arry;  // GETALL、SETALL 使用的数组
    struct seminfo *__buf; // IPC_INFO(Linux特有) 使用的缓存区 
}; 


// 信号量初始化为给定值
int initSem(int semid, int value);

// P操作函数
int P(int semid);

// V操作函数
int V(int semid);

// 获取信号量值
int getSem(int semid);

// 删除信号量
int deleteSem(int semid);


int main() {
    // 创建一块共享内存, 返回共享内存标识. 参数key_t是长整型（唯一非零）, 系统建立IPC通讯 （ 消息队列、 信号量和 共享内存） 时必须指定一个ID值.
    int shmid = shmget((key_t)9527, sizeof(Buff), IPC_CREAT | 0644);
    if (shmid == -1) {
        fprintf(stderr, "create shared memory error!\n");
        return -1;
    }
    // 将共享主存attach到当前进程, 即映射到当前进程的虚拟内存空间
    // shm指针指向共享内存区
    Buff* shm = (Buff*)shmat(shmid, 0, 0);

    if ((void*)shm == (void*)-1) {
        fprintf(stderr, "shmat error!\n");
        return -1;
    }

    /* 
        创建信号量用于同步临界区代码. 参数key是整数值（唯一非零），不相关的进程可以通过它访问一个信号量，
        它代表程序可能要使用的某个资源，程序对所有信号量的访问都是间接的，程序先通过调用semget()函数并提供一个键，
        再由系统生成一个相应的信号标识符（semget()函数的返回值），只有semget()函数才直接使用信号量键，
        所有其他的信号量函数使用由semget()函数返回的信号量标识符。如果多个程序使用相同的key值，key将负责协调工作。
        empty 记录空缓冲区的数量，full 记录满缓冲区的数量
    */
    int semid_empty = semget((key_t)99999, 1, 0644);
    int semid_full = semget((key_t)66666, 1, 0644);
    int semid_mutex = semget((key_t)12345, 1, 0644);

    if (semid_full == -1 || semid_empty == -1 || semid_mutex == -1) {
        fprintf(stderr, "semget error!\n");  
        return -1;      
    }
    

    int cnt = 0;

    // 消费者进行消费
    while (cnt < ALL) {
        // 先检查是否可以消费
        P(semid_full);
        // 申请互斥锁mutex进行消费
        P(semid_mutex);

        // 临界区
        ++ cnt;
        printf("consume cur-val = %d, curIdx = %d, full size = %d\n", shm->arr[shm->curIdx] , shm->curIdx, getSem(semid_full));
        -- (shm -> curIdx);
        if (cnt == ALL)
            shm -> finish = 1;

        // 释放锁以及一块空缓冲区
        V(semid_mutex);
        V(semid_empty);
    }

    //把共享内存从当前进程中分离
    if (shmdt(shm) == -1) {
        fprintf(stderr, "shmdt error!\n");
        return -1;
    }

    printf("read done!\n");
    return 0;
}


int initSem(int semid, int value) {
    // 信号量初始化
    union semun sem;
    sem.val = value;

    // semctl函数使用 SETVAL 初始化信号量
    if (semctl(semid, 0, SETVAL, sem) == -1 )  
        return -1;          
    return 0;
}

int P(int semid) {
    struct sembuf sem;
    sem.sem_num = 0;    // 信号量编号为0
    sem.sem_op = -1;   // -1 表示 P操作
    sem.sem_flg = SEM_UNDO; // 通常设置为SEM_UNDO,使操作系统跟踪信号量，并在进程没有释放该信号量而终止时，操作系统释放信号量

    if (semop(semid, &sem, 1) == -1) 
        return -1;
    return 0;
}

int V(int semid) {
    struct sembuf sem;
    sem.sem_num = 0;    // 信号量编号为0
    sem.sem_op = 1;   // 1 表示 V 操作
    sem.sem_flg = SEM_UNDO; // 通常设置为SEM_UNDO,使操作系统跟踪信号量，并在进程没有释放该信号量而终止时，操作系统释放信号量

    if (semop(semid, &sem, 1) == -1) 
        return -1;
    return 0;    
}

int getSem(int semid) {
    int ret = semctl(semid, 0, GETVAL);
    return ret;
}

int deleteSem(int semid) {
    union semun sem;
    if (semctl(semid, 0, IPC_RMID, sem) == -1)
        return -1;
    return 0;
}
```

- 执行结果:

```bash
$ ./shmwrite.o 
produce cur-val = 1,  curIdx = 0, empty size = 9
produce cur-val = 2,  curIdx = 1, empty size = 8
produce cur-val = 3,  curIdx = 2, empty size = 7
produce cur-val = 4,  curIdx = 3, empty size = 6
produce cur-val = 5,  curIdx = 4, empty size = 5
produce cur-val = 6,  curIdx = 5, empty size = 4
produce cur-val = 7,  curIdx = 6, empty size = 3
produce cur-val = 8,  curIdx = 7, empty size = 2
produce cur-val = 9,  curIdx = 8, empty size = 1
produce cur-val = 10,  curIdx = 9, empty size = 0
produce cur-val = 11,  curIdx = 2, empty size = 7
produce cur-val = 12,  curIdx = 2, empty size = 7
produce cur-val = 13,  curIdx = 2, empty size = 7
produce cur-val = 14,  curIdx = 2, empty size = 7
produce cur-val = 15,  curIdx = 2, empty size = 7
produce cur-val = 16,  curIdx = 2, empty size = 7
produce cur-val = 17,  curIdx = 2, empty size = 7
produce cur-val = 18,  curIdx = 2, empty size = 7
produce cur-val = 19,  curIdx = 2, empty size = 7
produce cur-val = 20,  curIdx = 2, empty size = 7
write done!
```

```bash
$ ./shmread.o 
consume cur-val = 9, curIdx = 9, full size = 9
consume cur-val = 8, curIdx = 8, full size = 8
consume cur-val = 7, curIdx = 7, full size = 7
consume cur-val = 6, curIdx = 6, full size = 6
consume cur-val = 5, curIdx = 5, full size = 5
consume cur-val = 4, curIdx = 4, full size = 4
consume cur-val = 3, curIdx = 3, full size = 3
consume cur-val = 2, curIdx = 2, full size = 2
consume cur-val = 10, curIdx = 2, full size = 2
consume cur-val = 11, curIdx = 2, full size = 2
consume cur-val = 12, curIdx = 2, full size = 2
consume cur-val = 13, curIdx = 2, full size = 2
consume cur-val = 14, curIdx = 2, full size = 2
consume cur-val = 15, curIdx = 2, full size = 2
consume cur-val = 16, curIdx = 2, full size = 2
consume cur-val = 17, curIdx = 2, full size = 2
consume cur-val = 18, curIdx = 2, full size = 2
consume cur-val = 19, curIdx = 2, full size = 2
consume cur-val = 1, curIdx = 1, full size = 1
consume cur-val = 0, curIdx = 0, full size = 0
read done!
```
- ipcs 查看共享内存和信号量的使用情况.

```bash
$ ipcs
------ Message Queues --------
key        msqid      owner      perms      used-bytes   messages    

------ Shared Memory Segments --------
key        shmid      owner      perms      bytes      nattch     status      
0x00002537 18         jsss       644        48         1                       

------ Semaphore Arrays --------
key        semid      owner      perms      nsems     
0x0001869f 54         jsss       644        1         
0x0001046a 55         jsss       644        1         
0x00003039 56         jsss       644        1  
```

- 执行流程:
    - 先执行了`shmwrite`进行生产, 因此很快缓冲区被填满.
    - 接着执行`ipcs`查看IPC的使用情况.
    - 最后执行`shmread`进行消费, 消费了前八个后基本就是生产一个消费一个的动态平衡状态.


## 参考资料

- [信号量](https://blog.csdn.net/skyroben/article/details/72513985)
- [信号量](https://blog.csdn.net/mybelief321/article/details/9086151)
- [信号量](https://www.cnblogs.com/52php/p/5851570.html)
- [共享内存](https://blog.csdn.net/skyroben/article/details/72625028)