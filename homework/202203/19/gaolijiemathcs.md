## Java: 什么是序列化?什么是反序列化?

### 什么是序列化

序列化的原本意图是希望对一个Java对象作一下“变换”，变成字节序列，这样一来方便持久化存储到磁盘，避免程序运行结束后对象就从内存里消失，另外变换成字节序列也更便于网络运输和传播，所以概念上很好理解：

- **序列化**：把Java对象转换为字节序列。
- **反序列化**：把字节序列恢复为原先的Java对象。

序列化机制从某种意义上来说也弥补了平台化的一些差异，毕竟转换后的字节流可以在其他平台上进行反序列化来恢复对象。



### 如何序列化？

**对象如何序列化？**

然而Java目前并没有一个关键字可以直接去定义一个所谓的“可持久化”对象。

对象的持久化和反持久化需要靠程序员在代码里手动**显式地**进行序列化和反序列化还原的动作。

举个例子，假如我们要对`Student`类对象序列化到一个名为`student.txt`的文本文件中，然后再通过文本文件反序列化成`Student`类对象：

1、定义类对象

```java
public class Student implements Serializable {

    private String name;
    private Integer age;
    private Integer score;
    
    @Override
    public String toString() {
        return "Student:" + '\n' +
        "name = " + this.name + '\n' +
        "age = " + this.age + '\n' +
        "score = " + this.score + '\n'
        ;
    }
    
    // ... 其他省略 ...
}
```



2、序列化

```java
public static void serialize(  ) throws IOException {

    Student student = new Student();
    student.setName("CodeSheep");
    student.setAge( 18 );
    student.setScore( 1000 );

    ObjectOutputStream objectOutputStream = 
        new ObjectOutputStream( new FileOutputStream( new File("student.txt") ) );
    objectOutputStream.writeObject( student );
    objectOutputStream.close();
    
    System.out.println("序列化成功！已经生成student.txt文件");
    System.out.println("==============================================");
}
```



3、反序列化

```java
public static void deserialize(  ) throws IOException, ClassNotFoundException {
    ObjectInputStream objectInputStream = 
        new ObjectInputStream( new FileInputStream( new File("student.txt") ) );
    Student student = (Student) objectInputStream.readObject();
    objectInputStream.close();
    
    System.out.println("反序列化结果为：");
    System.out.println( student );
}
```



结果

```
序列化成功！已经生成student.txt文件
==============================================
反序列化结果为：
Student:
name = CodeSheep
age = 18
score = 1000
```

