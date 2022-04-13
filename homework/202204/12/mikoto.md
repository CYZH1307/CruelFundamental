### 如何人为避免out-of-order execution

乱序执行（out-of-order execution）各单元不按照规定顺序执行完指令后再由相应电路将运算结果按原来指令顺序重新排列后返回，加快运行速度

乱序执行有两种情况：
1. 编译期，编译器进行指令重排
2. 运行期，CPU进行指令乱序执行


#### compiler fence
有时候乱序执行会给并发编程带来意想不到的错误，因此引入了“compiler fence”

`asm volatile("": : :"memory");`

这是GCC扩展的`compile fence`的写法，该指令告诉编译器：

1. 防止该fence指令上方的内存访问操作被移到下方，同时防止下方的内存访问操作被移到上方，就像"fence"一样，防止了乱序
2. 让编译器把所有缓存在寄存器中的内存变量flush到内存中，然后从内存中重新读取。

对于第二点，全部重新刷新或许不符合我们预期，我们可能只是防止特定指令被乱序执行，全部刷新带来的开销很大，因此也支持指定变量

```cpp
write(x);
asm volatile("": "=m"(y) : "m"(x));
read(y);

中间的指令高速编译器不要把write(x)和read(y)乱序    

#### cpu fence

对应第二种情况，虽然引入“compiler fence”后编译期不会进行指令重排，但是cpu仍然存在乱序执行的问题，也可能对特定情况下的并发编程带来问题。

因此又引入“cpu fence”

写法上类似，功能上理解起来也差不多
`asm volatile("mfence": : :"memory");`




```

