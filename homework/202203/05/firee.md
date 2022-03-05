#### C++: 简述 mechanism of new

new 是c++的关键字，用于动态分配内存，与delete搭配使用，与malloc不同，new封装了一些细节，使得用户不用直面对象内存大小的问题。new分配的内存在堆上，new和delete里用了一些系统调用来分配和回收内存。