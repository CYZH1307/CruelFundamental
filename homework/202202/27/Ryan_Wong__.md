# C++：简述vtable
- vtable是虚函数表，虚函数是C++中用virtual关键字修饰的函数，多用于基类的可继承函数的抽象定义，在运行时起作用。  
- 函数加了virtual修饰之后，便会绕开静态编译期，直接被存入vtable。
- 所有子类实例都会生成和虚函数个数相等的虚指针放在实例头部，这些虚指针共同构成了该类的虚表。若子类重写了虚函数，则虚指针指向自己实现的函数地址，否则指向基类的虚函数地址
![image](https://user-images.githubusercontent.com/10019865/155893985-fa529454-91f2-4da0-87c7-7449599aed4e.png)
    
  示例：基类为Animal，拥有2个虚函数makeSound和walk，各种动物子类分别实现了自己的makeSound，但没有实现walk。则子类的vtable指向性如下：
  ![image](https://user-images.githubusercontent.com/10019865/155894087-d396cfca-8088-4c8f-ac18-b24a8251f177.png)


