Visitor Pattern:

Movitation:
avoid to use the if-else condition to check the instance

Visitor has double dispatch to allow dynamic binding alongside with overloaded methods

What we can do use visitor pattern?
1. Use the visitor when you need to perform an operation all elements of a complext project structure. (execute the operation over a set of objects)
2. clean up the business logic (make the primary classes of your app)
3. use the pattern when a behavior makes sense in some classes of a class hirerachy

How to implement:
1. visitor interface
2. concrete visitors
3. element interface 
4. concreate classes with accept(v: Visitor)
5. application we can execute the accept() with different concrete visitor

