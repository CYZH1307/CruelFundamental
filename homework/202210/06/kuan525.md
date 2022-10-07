### bios到kernel的启动过程，实模式和保护模式所做的操作，转换后mmu的作用。
kernel启动流程第一阶段简单说明
```arch/arm/kernel/head.S```

* kernel入口地址对应stext
> ENTRY(stext)


* 第一阶段要做的事情，也就是stext的实现内容

1. 设置为SVC模式，关闭所有中断
2. 获取CPU ID，提取相应的proc info
3. 验证tags或者dtb
4. 创建临时内核页表的页表项
5. 配置r13寄存器，也就是设置打开MMU之后要跳转到的函数。
6. 使能MMU
7. 跳转到start_kernel，也就是跳转到第二阶段