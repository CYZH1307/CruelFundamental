## Java: 反射机制优缺点和应用场景

### 反射的优点和缺点

~~~
1、优点：可以动态的创建和使用对象（也是框架底层的核心），动态类加载使用灵活，没有反射机制框架技术就失去底层支撑，例如支持动态代理。
2、缺点：使用反射基本是解释执行，对执行速度有影响
~~~

~~~java
public class Reflection02 {
    public static void main(String[] args) throws Exception {
        demo1();
        demo2();
    }
    // 传统方法调用hello
    public static void demo1() {
        Cat cat = new Cat();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 900000000; i++) {
            cat.hello();
        }
        long end = System.currentTimeMillis();
        System.out.println("传统方法hello 耗时：" + (end - start));
    }

    // 反射机制调用hello
    public static void demo2() throws Exception{
        Class catClass = Class.forName("com.gao.Cat");
        Object o = catClass.newInstance();
        Method helloMethod = catClass.getMethod("hello");
        long start = System.currentTimeMillis();
        for(int i = 0; i < 900000000; i++) {
            helloMethod.invoke(o);
        }
        long end = System.currentTimeMillis();
        System.out.println("放射机制hello 耗时：" + (end - start));

    }
}
~~~

输出：

~~~
传统方法hello 耗时：7
放射机制hello 耗时：4402
~~~



### 使用场景

- JDBC 的数据库的连接
- Spring 框架的使用：如Bean加载过程









