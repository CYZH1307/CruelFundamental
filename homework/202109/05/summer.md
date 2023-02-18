Why threadlocal causes memory leak?

TreadLocal variables are supposed to be garbage collected once the holding thread is no longer alive. ThreadLocal instances are typically private static fields in classes that wish to associate state with a thread. 

PermGen exhaustions in combination with ThreadLocal are often caused by classloader leaks.

By definition, a reference to a ThreadLocal value is kept until the "owning" thread dies or if the ThreadLocal itself is no longer reachable. In this case the only references to the ThreadLocal are in the static final field of a class that has now become a target for GC, and the reference from the worker threads. However, the references from the worker threads to the ThreadLocal are WeakReferences!

The values of a ThreadLocal are not weak references, however. So, if you have references in the values of a ThreadLocal to application classes, then these will maintain a reference to the ClassLoader and prevent GC. However, if your ThreadLocal values are just integers or strings or some other basic object type, there should not be a problem.

To avoid such problems, it is recommended to always clean up TreadLocal variables using the remove() method to remove the current thread's value for the ThreadLocal variable.

