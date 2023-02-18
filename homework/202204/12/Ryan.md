# 如何人为避免out-of-order execution
加入compiler barriers，思想是在barrier后面的操作必须等待barrier前面的执行完了之后才能执行。从操作类型来看，C++乱序可以是以下4种：

    1.读读乱序，适用于读读barrier。
    2.读写乱序，适用于读写barrier和写读barrier。
    3.写写乱序，适用于写写barrier。
    
避免乱序最直接的手段就是在相应变量前面加上volatile关键字，被该关键字修饰的变量：  

1.被读的时候，会在其后面插入读读barrier和读写barrier。确保它被读取之后，才能进行后面的任意读写操作（两个barriers都是读在前）。  
2.被写的时候，会在其后面插入写读barrier、在前面插入写写barrier。写读barrier放在后面确保它被写了之后，读操作可以读到任意改变。而写写barrier放在前面，是为了保证当前操作最后一个写，不会被覆盖。
