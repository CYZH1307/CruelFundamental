`volatile` is needed if you are reading from a spot in memory that, say, a completely separate process/device/whatever may write to.

`volatile` is needed when developing embedded systems or device drivers, where you need to read or write a memory-mapped hardware device. The contents of a particular device register could change at any time, so you need the `volatile` keyword to ensure that such accesses aren't optimised away by the compiler.



when you should use `volatile`.

- When inside a signal handler. Because writing to a `volatile` variable is pretty much the only thing the standard allows you to do from within a signal handler. Since C++11 you can use `std::atomic` for that purpose, but only if the atomic is lock-free.
- When dealing with `setjmp` [according to Intel](https://software.intel.com/en-us/blogs/2007/11/30/volatile-almost-useless-for-multi-threaded-programming).
- When dealing directly with hardware and you want to ensure that the compiler does not optimize your reads or writes away.



For example:

```cpp
volatile int *foo = some_memory_mapped_device;
while (*foo)
    ; // wait until *foo turns false
```

Without the `volatile` specifier, the compiler is allowed to completely optimize the loop away. The `volatile` specifier tells the compiler that it may not assume that 2 subsequent reads return the same value.

Note that `volatile` has nothing to do with threads. The above example does not work if there was a different thread writing to `*foo` because there is no acquire operation involved.



- ***volatile object*** - an object whose type is *volatile-qualified*, or a subobject of a volatile object, or a mutable subobject of a const-volatile object. Every access (read or write operation, member function call, etc.) made through a glvalue expression of volatile-qualified type is treated as a visible side-effect for the [purposes of optimization](https://en.cppreference.com/w/cpp/language/as_if) (that is, within a single thread of execution, volatile accesses cannot be optimized out or reordered with another visible side effect that is [sequenced-before](https://en.cppreference.com/w/cpp/language/eval_order) or sequenced-after the volatile access. This makes volatile objects suitable for communication with a [signal handler](https://en.cppreference.com/w/cpp/utility/program/signal), but not with another thread of execution, see [std::memory_order](https://en.cppreference.com/w/cpp/atomic/memory_order)). Any attempt to refer to a volatile object through a [glvalue](https://en.cppreference.com/w/cpp/language/value_category#glvalue) of non-volatile type (e.g. through a reference or pointer to non-volatile type) results in undefined behavior.

