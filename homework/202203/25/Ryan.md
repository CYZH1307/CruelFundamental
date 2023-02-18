# C++: 为什么优先选用using别名声明，而非typedef？

主要两点：

    1.typedef不支持模板化，但using别名申明可以
    2.别名模板可以让人免写”::type“后缀，并且在模板内，对于嵌套typedef的引用经常要求加上typename前缀
