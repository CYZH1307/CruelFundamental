C++： volatile关键字作用
volatile通常用于建立语言级别的内存屏障。

用volatile声明的类型变量表示可以被某些编译器未知的因素更改，比如：操作系统、硬件或者其它线程等。遇到这个关键字声明的变量，编译器对访问该变量的代码就不再进行优化，从而可以提供对特殊地址的稳定访问（系统总是重新从它所在的内存读取数据，且读取的数据立刻被保存）。

#include <bits/stdc++.h>
 
int main()
{
    int i = 10;
    int a = i;
 
    printf("i = %d", a);
 
    // 下面汇编语句的作用就是改变内存中 i 的值
    // 但是又不让编译器知道
    __asm {
        mov dword ptr [ebp-4], 20h
    }
 
    int b = i;
    printf("i = %d", b);
	return 0;
}
输出结果为：

i = 10
i = 10
对i加上volatile关键字后: volatile int i = 10;

输出结果为：

i = 10
i = 32
