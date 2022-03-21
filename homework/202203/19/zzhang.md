C++: 指针和引用的差别

- References are used to refer an existing variable in another name whereas pointers are used to store address of variable 指针存的是地址
- References cannot have a null value assigned but pointer can 指针可以是NULL
- A reference variable can be referenced by pass by value whereas a pointer can be referenced by pass by reference
- A reference must be initialized on declaration while it is not necessary in case of pointer. A pointer can be re-assigned. 引用必须初始化不能reassign
- A reference shares the same memory address with the original variable but also takes up some space on the stack whereas a pointer has its own memory address and size on the stack. 指针在stack上有存自己的memory address 和大小
