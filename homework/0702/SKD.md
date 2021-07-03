String vs StringBuffer vs StringBuilder
The string is one of the most important topics in the core java interview. If you are writing a program that prints something on the console, you are use String.

This tutorial is aimed to focus on major features of String class. Then we will compare the StringBuffer and StringBuilder classes.

String in Java
String class represents character strings, we can instantiate String in two ways.
String str = "ABC";
// or
String str = new String("ABC");
String is immutable in Java. So it’s suitable to use in a multi-threaded environment. We can share it across functions because there is no concern of data inconsistency.
When we create a String using double quotes, JVM first looks for the String with the same value in the string pool. If found, it returns the reference of the string object from the pool. Otherwise, it creates the String object in the String pool and returns the reference. JVM saves a lot of memory by using the same String in different threads.
If the new operator is used to create a string, it gets created in the heap memory.
The + operator is overloaded for String. We can use it to concatenate two strings. Although internally it uses StringBuffer to perform this action.
String overrides equals() and hashCode() methods. Two Strings are equal only if they have the same character sequence. The equals() method is case sensitive. If you are looking for case insensitive checks, you should use equalsIgnoreCase() method.
The string uses UTF-16 encoding for the character stream.
String is a final class. All the fields as final except “private int hash”. This field contains the hashCode() function value. The hashcode value is calculated only when the hashCode() method is called for the first time and then cached in this field. Furthermore, the hash is generated using the final fields of String class with some calculations. So every time the hashCode() method is called, it will result in the same output. For the caller, it seems like calculations are happening every time but internally it’s cached in the hash field.
String vs StringBuffer
Since String is immutable in Java, whenever we do String manipulation like concatenation, substring, etc. it generates a new String and discards the older String for garbage collection.

These are heavy operations and generate a lot of garbage in heap. So Java has provided StringBuffer and StringBuilder classes that should be used for String manipulation.

StringBuffer and StringBuilder are mutable objects in Java. They provide append(), insert(), delete(), and substring() methods for String manipulation.

StringBuffer vs StringBuilder
StringBuffer was the only choice for String manipulation until Java 1.4. But, it has one disadvantage that all of its public methods are synchronized. StringBuffer provides Thread safety but at a performance cost.

In most of the scenarios, we don’t use String in a multithreaded environment. So Java 1.5 introduced a new class StringBuilder, which is similar to StringBuffer except for thread-safety and synchronization.

StringBuffer has some extra methods such as substring, length, capacity, trimToSize, etc. However, these are not required since you have all these present in String too. That’s why these methods were never implemented in the StringBuilder class.

StringBuffer was introduced in Java 1.0 whereas StringBuilder class was introduced in Java 1.5 after looking at shortcomings of StringBuffer.

If you are in a single-threaded environment or don’t care about thread safety, you should use StringBuilder. Otherwise, use StringBuffer for thread-safe operations.