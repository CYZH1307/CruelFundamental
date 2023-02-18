# Java: String和StringBuilder、StringBuffer的区别？

### String 字符串常量

String字符常量，字符长度不可变。包含如下定义：

```java
private final char value[];	// final 只能赋值一次 不能再改变
private final int offset;
private final int count;
```

String是不可变对象，每次对String类型进行改变的时候，都会生成一个新的String对象，然后将新的指针指向Strig对象。

使用注意事项：经常改变内存的字符串最好不要用String，因为每次生成新的对象都对系统性能影响，尤其内存中无引用对象多了，JVM的GC就开始工作，性能就降低。

### StringBuffer 字符串变量(线程安全)

StringBuffer线程安全的字符变量，如果频繁对字符串内容修改，最好使用StringBuilder。转为String，用toString()方法。可以将字符串缓冲区安全的用于多线程。

- append(String str) 追加字符串、insert(int offset, int/String i) 将int/String参数字符串插入序列、delete(int start, int end)移除字符串字符 、reverse(int start, int end, String str);

### StringBuilder 字符串变量(非线程安全)

StringBuilder字符串变量，可以看做是一个包含字符串序列的变长数组，可变字符序列，JDK5.0新增，提供了一个与StringBuilder兼容的API，但是不保证同步，被设计用作StringBuffer的替换，字符串缓冲区被单个线程使用的时候。



### 小结

String类型和StringBuffer区别：String不可变对象，每次对String类型改变的时候，都会生成一个新的String对象，指针指向新的String对象，如果经常改变内容的字符串最好不用String。尽量少用String中的+来频繁拼接。

StringBuffer，每次对StringBuffer对象操作，不生成新的对象或改变对象引用，多数推荐用StringBuffer，尤其是字符串对象经常改变。

关于StringBuilder，因为是线程不安全的，可以在方法内部完成类似"+"的操作，线程不安全，用完就丢弃，而StringBuffer主要用于全局变量。相同的情况使用StringBuilder相比StringBuffer，只提高10%~15%性能，却冒险多线程不安全。因此除非确保单线程，没有多线程分析，才使用StringBuilder，否则用StringBuffer.



ref:

https://blog.csdn.net/kingzone_2008/article/details/9220691

https://www.runoob.com/java/java-stringbuffer.html