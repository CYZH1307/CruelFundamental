针对你熟悉的编程语言，描述字符串类型的内存管理机制，比如String，StringBuffer, StringBuilder区别
java
String是字符串常量，是不可变的，字符串一旦创建之后，值就不能改变。
private final byte[] value; //因为String的数据是存储在final的byte数组里的，所以一旦赋值就不能改变了。
但是String变量却可以指向新的String对象，如果想让String 引用指向的对象不在改变只需要把String的引用也设置成final的，如：
final String s=new String（“123”）；//这时无论是String实例还是，指向实例的引用都不能修改了，可以起到不能修改内容的作用；
String 在初始化的时候的构造方法如下：
public String(String original) {
        this.value = original.value;//byte数组赋值
        this.coder = original.coder;//coder 表示byte数组的编码格式，0表示LATIN1， 1表示UTF16
        this.hash = original.hash; //保存字符串的hash值
    }
StringBuffer是线程安全的可变的字符序列，在某一时刻，字符串的内容是唯一的，但是字符串的长度和内容可以通过方法修改，这个对象的方法在必要的
时候进行了同步方法加了synchronized，所以多线程修改字符串内容或长度时，就像是在顺序执行一样，并且执行顺序是和单个线程的执行顺序是一样的。
StringBuffer的主要方法是append和insert，并且这些方法都重载了，支持不同类型的数据，append总是把字母加载最后，insert可以把字母插入到任意指定位置，
StringBuffer对象的内容是存储在AbstractStringBuilder的字节数组里的，这个字节数组不是final的所以是可以改变的，在插入的时候会先判断数组是否会越界，
否则就扩容，扩容还没看。。
StringBuilder在初始化的时候使用父类构造方法AbstractStringBuilder(int capacity)，在这个方法中和StringBuffer初始化的时候一样创建了16个字节的
字节数组，StringBuilder不是线程安全的，但同时因为没有阻塞机制进行多线程的同步，这个类的性能会快一些，当不是多线程场景下时更推荐这个类。
这个类实现了Comparable接口的compareTo方法，但是却没有重写父类Object.equals方法，而父类的Object.equals方法是用来判断两个引用是否只想同一个对象实例
这样会造成两个对象compareto相等内容相等但如果用equals却不相等的不一致情况，所以在使用SortedMap和SortedSet的时候需要注意。
