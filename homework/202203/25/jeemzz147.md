#### C++: 为什么优先选用别名声明，而非typedef？（Effective Modern C++）
* 涉及函数指针时更容易理解
* 别名声明可以模板化， typedef无法直接做到 
* 别名模板可以让人不用写::type后缀，并且在模板内不用像内嵌typedef一样加typename前缀。
