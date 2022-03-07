## Java:Java语言如何进行异常处理,关键字:throws、throw、try、catch、finally如何使用?

### Java异常处理思路

（1）异常抛出的情况：异常是阻止当前方法或者作用域继续执行的问题。需要将当前运行环境下无法处理的情况，需要抛出异常，而不是顺着原来路径执行。

（2）创建异常对象：抛出异常将会在堆上创建异常对象。当前程序执行路径被终止，弹出异常对象引用。(异常对象都是从Throwable类继承实现来的)。Throwable对象可以分为两种，Exception为可以抛出的基本类型，Error为编译和系统错误。常见的异常对象IOException(IO异常),RuntimeException(运行时异常 不受检查的异常 需要单独去看是什么异常 其他异常从这里继承继续实现。)，ArrayIndexOutBoundsException(数组越界异常)等等

（3）异常处理程序：将程序从错误状态恢复，要么换一种方式继续执行，要么继续执行。



### 异常处理关键词

#### throws

throws后面接一个所有潜在异常类型的列表。用于在方法上声明改方法要抛出的异常，可以在方法内部抛出对应的异常对象。可以用自己定义的异常类型，也可以用系统自带的异常类型。

#### throw

用于抛出具体类型的异常，跟着一个异常对象(用new 创建异常对象后传给throw)。

#### try

try块在方法抛出异常，这个抛出异常的方法将会在抛出异常的情况下，在上一级try的时候接受这个异常，捕获在try块中产生的异常，并进行后续的异常处理。

```java
try {
    // Code that might generate exceptions
}
```

#### catch

异常处理程序，接受并处理不同类型的异常。必须要跟在try后面，不能单独在，并且catch只有类型匹配的第一个处理程序进行处理。

```java
try {
	// Code that might generate exceptions
} catch(Etype1 e1) {
	// handle exceptions of type1;
} catch(Etype2 e2) {
	// handle exceptions of type2
} catch(Etype3 e3) {
	// handle exceptions of type3
}
...
```

#### finally

无论try块的异常是否抛出，finally里面的都能得到执行。一般内存回收(GC完成)以外的步骤都能在这里处理。

什么时候使用finally? 要把除内存以外的资源恢复到初始状态。

```java
try {
	// Code that might generate exceptions
} catch(Etype1 e1) {
	// handle exceptions of type1;
} catch(Etype2 e2) {
	// handle exceptions of type2
} catch(Etype3 e3) {
	// handle exceptions of type3
} finally {
    // Activities that happen every time
}
...
```



### 使用样例

```java
// 自定义异常类型Type1 Type2
class EType1 extends Exception {}
class EType2 extends Exception {}
public class ExampleClass {
    // throws 用于声明这个类 抛出异常的类型
    public void f(int num) throws EType1, EType2 {
        // 抛出具体的异常对象 需要创建异常对象
        if(num == 1) {
            System.out.println("Throw NewException from f("+num+")");
            throw new EType1();
        } else if(num == 2) {
            System.out.println("Throw NewException from f("+num+")");
            throw new EType2();
        } else {
            System.out.println("hello");
        }
        System.out.println("hi");
    }
    public static void main(String[] args) {
        ExampleClass example = new ExampleClass();
        // try块用于捕获异常
        int num = 0;
        while(true) {
            try {
                example.f(num);
            } catch(EType1 e1) {
                System.out.println("We caught e1 !");
            } catch (EType2 e2) {
                System.out.println("We caught e2 !");
            } finally {
                System.out.println("In finally clause." + " num = " + num);
                num++;
                if(num == 3) break;
            }
        }
        System.out.println("i'm out");
    }
}
```

输出:

```
hello
hi
In finally clause. num = 0
Throw NewException from f(1)
We caught e1 !
In finally clause. num = 1
Throw NewException from f(2)
We caught e2 !
In finally clause. num = 2
i'm out
```



