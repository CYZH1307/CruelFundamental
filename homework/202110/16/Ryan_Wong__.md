# main函数原理 #

    1.在有操作系统的环境里，C和Cpp才需要一个默认的函数作为系统调用入口，若在没有操作系统的生产环境里则不需要。这个函数默认名称为main。  
    
    2.系统内核执行C或Cpp的可执行文件时，内核会先启动一个预备进程，将启动参数，包括命令行参数（个数argc、内容argv）和环境变量（envp）搜集完毕，然后作为main函数的输入。  标准的C和Cpp的main函数需要以上几个入口参数，若是不需要命令行参数的程序，可简单写为int main()，本地独立执行的程序可以用这种形式的写法。
    
    3.main函数return后，系统返回预备进程，销毁搜集的启动参数，结束调用。
