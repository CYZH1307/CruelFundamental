## Java: 阐述final、finally、finalize的区别。

### final

final可以用于修饰数据、方法、类。

（1）final修饰数据

final修饰数据主要用于表示一个永不改变的编译时常量，也可以用于在运行时被初始化的值，而且不希望他被改变。

- 当修饰数据类型时：编译期常量数值恒定不变。编译器可以将这个常量值带入所有含有这个final数据的计算式当中，可以在编译时执行计算式，减轻运行时负担。这类常量必须为基本数据类型，以final类型表示。并且这个常量在定义的时候必须赋值。
- 当修饰对象引用时：对象引用恒定不变。一旦引用被初始化为指向一个对象，就无法再将这个对象引用指向另外一个对象。但是这个指向的对象本身是可以修改的，同样适用于数组和对象。可以修改final引用的值，但是不能指向另一个新的对象。
- 一个数据被static和final同时修饰的时候，只会占据一段不能改变的存储空间。static final类型一般都用大写命名。
- 可以有空白final，声明为final的数据，但是没有给初值的域。无论什么情况，编译器都需要确保空白final可以在使用前被初始化，因此可以比较灵活的写在构造器里面，在定义这个对象的时候，进行对应的初值赋值，因此一个类中的final域可以根据对象不同而不同，但是可以保持在赋初值以后保持不变的特点。
- final参数列表：Java可以在参数列表里面以声明的方式将参数指定为final，含义是不能在方法中更改参数引用指向的对象。

```
void f(fianl int i) { i++; }	// 会报错 i不能被修改
int g(final int i) { return i + 1; } // 不会报错 
// 可以读参数 但是不能修改参数。主要可以向匿名内部类传递数据
```

（2）final修饰方法：

- 能够将方法锁定防止任何继承类修改这个方法的含义。
- 效率，早期Java版本会将final方法转为内嵌调用，但是方法过于庞大可能不会有多大提升，并且后期JVM也可以实现类似的机制，因此谨慎为了提高效率使用final修饰方法。

（3）final修饰类：将某个类修饰为final。将某个类的整体定义为final，代表你不打算继承这个类，而且也不允许其他人继承。



注：final修饰的如果想要修改，可以通过反射的方式修改。例如下面的例子（private字段默认为final的），但是我们可以进行反射赋值。

```
import java.lang.reflect.Field;

class Pojo {
    private final StringBuilder sb = new StringBuilder("111");

    public void printsb() {
        System.out.println(sb.toString());
    }
}

public class test {

    public static void main(String[] args) throws NoSuchFieldException {
        try {
            Pojo p = new Pojo();
            p.printsb();

            // 反射获取字段 sb字段
            Field nameField = p.getClass().getDeclaredField("sb");
            // private权限 打开权限
            nameField.setAccessible(true);
            // 反射赋值
            nameField.set(p, new StringBuilder("222"));
            p.printsb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

输出：

```
111
222
```

但如果将StringBuilder改为基础类型String，就会发现修改失败，因为JVM编译的时候，将final类型的String优化，编译时期就将String处理为常量，所以Pojo中的printsb方法就直接转化为System.out.println("111");

防止将final类型JVM编译的时候处理为常量，可以加一次运行判断，编译的时候就不会直接转为常量。

```java
class Pojo {
    private final String sb = new StringBuilder("111").toString();
    public void printsb() {
        System.out.println(sb);
    }
}
```

同时反射也可以修改同时被static final修饰的的，需要先将final字符去掉，再去修改再重新将final字符加回来。

```java
package Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class Pojo {
    private static final StringBuilder sb = new StringBuilder("111");

    public void printsb() {
        System.out.println(sb.toString());
    }
}

public class test {

    public static void main(String[] args) throws NoSuchFieldException {
        try {
            Pojo p = new Pojo();
            p.printsb();
            // 反射获取字段 sb字段
            Field nameField = p.getClass().getDeclaredField("sb");
            // private权限 打开权限
            nameField.setAccessible(true);
            Field modifiers = nameField.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            // 去掉fianl
            modifiers.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);
            // 修改与设置
            nameField.set(p, new StringBuilder("222"));
            // 加回final
            modifiers.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);
            p.printsb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

修改成功





### finally

用于异常处理的一部分，只用在try/catch中，附带一个语句块，不管有没有抛出异常总会执行。使用到finally的场景，由于已经有垃圾回收机制，所以不用考虑内存释放，finally中可以放一些除内存以外的资源恢复初始状态，例如断开网络连接，或者关闭文件等等。



### finalize

一旦垃圾回收器准备好设防对象占用的存储空间，首先调用finalized()方法，并且在下一次垃圾回收动作发生的时候，才会真正的回收对象占用的内存。所以要是打算使用finalize()，就可以在垃圾回收的时候做一些清理工作。

finalize()在Java.lang.Object里面定义，每个对象都有一个方法，在GC启动，该对象被回收的时候调用。其实一般GC可以回收大部分的对象，finalize一般不用去实现。







