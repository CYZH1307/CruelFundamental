### Docker: container 和 image 的区别，如何创建一个image

------

container和image唯一的区别就是container中的最上层多了一层可写层，而image全部都是只读的。可以把image理解为一个程序运行环境描述文件，而container就是一个基于这个环境的运行程序。