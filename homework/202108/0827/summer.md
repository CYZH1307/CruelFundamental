C++11 perfect forwarding

What is perfect forwarding?
If a function templates forward its arguments without changing its lvalue or rvalue characteristics, we call it perfect forwarding.
It means that a function template can pass its arguments through to another function whilst retaining the lvalue/rvalue nature of the function arguments by using std::forward. 



template<typename T>
void function(T t) {
    otherdef(t);
}

This function call the otherdef() function. If the function() received the t as lvalue, the t which is passed to otherdef() should also be lvalue. On the other side, if function() received t as rvalue, the t passed to otherdef() should be rvalue.

Possible problems:
Variadic Templates are templates that can get an arbitrary number of arguments. That is exactly the missing feature of the perfect factory method.



Solution to the incompatibility between lvalue and rvalue:
Overload method
Use std::forward

