A compiled language is one where the program, once compiled, is expressed in the instructions of the target machine. For example, an addition "+" operation in your source code could be translated directly to the "ADD" instruction in machine code.

An interpreted language is one where the instructions are not directly executed by the target machine, but instead read and executed by some other program (which normally *is* written in the language of the native machine). For example, the same "+" operation would be recognised by the interpreter at run time, which would then call its own "add(a,b)" function with the appropriate arguments, which would then execute the machine code "ADD" instruction.

You can do anything that you can do in an interpreted language in a compiled language and vice-versa - they are both Turing complete. Both however have advantages and disadvantages for implementation and use.

I'm going to completely generalise (purists forgive me!) but, roughly, here are the advantages of compiled languages:

- Faster performance by directly using the native code of the target machine
- Opportunity to apply quite powerful optimisations during the compile stage

And here are the advantages of interpreted languages:

- Easier to implement (writing good compilers is very hard!!)
- No need to run a compilation stage: can execute code directly "on the fly"
- Can be more convenient for dynamic languages

Note that modern techniques such as bytecode compilation add some extra complexity - what happens here is that the compiler targets a "virtual machine" which is not the same as the underlying hardware. These virtual machine instructions can then be compiled again at a later stage to get native code (e.g. as done by the Java JVM JIT compiler).






Neither approach has a clear advantage over the other - if one approach was always better, chances are that we'd start using it everywhere!

*Generally speaking*, compilers offer the following advantages:

1. Because they can see all the code up-front, they can perform a number of analyses and optimizations when generating code that makes the final version of the code executed faster than just interpreting each line individually.
2. Compilers can often generate low-level code that performs the equivalent of a high-level ideas like "dynamic dispatch" or "inheritance" in terms of memory lookups inside of tables. This means that the resulting programs need to remember less information about the original code, lowering the memory usage of the generated program.
3. Compiled code is generally faster than interpreted code because the instructions executed are usually just for the program itself, rather than the program itself plus the overhead from an interpreter.

*Generally speaking,* compilers have the following drawbacks:

1. Some language features, such as dynamic typing, are difficult to compile efficiently because the compiler can't predict what's going to happen until the program is actually run. This means that the compiler might not generate very good code.
2. Compilers generally have a long "start-up" time because of the cost of doing all the analysis that they do. This means that in settings like web browsers where it's important to load code fast, compilers might be slower because they optimize short code that won't be run many times.

*Generally speaking*, interpreters have the following advantages:

1. Because they can read the code as written and don't have to do expensive operations to generate or optimize code, they tend to start up faster than compilers.
2. Because interpreters can see what the program does as its running, interpreters can use a number of dynamic optimizations that compilers might not be able to see.

*Generally speaking*, interpreters have the following disadvantages:

1. Interpreters typically have higher memory usage than compilers because the interpreter needs to keep more information about the program available at runtime.
2. Interpreters typically spend some CPU time inside of the code for the interpreter, which can slow down the program being run.

Because interpreters and compilers have complementary strengths and weaknesses, it's becoming increasingly common for language runtimes to combine elements of both. Java's JVM is a good example of this - the Java code itself is compiled, and initially it's interpreted. The JVM can then find code that's run many, many times and compile it directly to machine code, meaning that "hot" code gets the benefits of compilation while "cold" code does not. The JVM can also perform a number of dynamic optimizations like inline caching to speed up performance in ways that compilers typically don't.

Many modern JavaScript implementations use similar tricks. Most JavaScript code is short and doesn't do all that much, so they typically start off using an interpreter. However, if it becomes clear that the code is being run repeatedly, many JS engines will compile the code - or at least, compile bits and pieces of it - and optimize it using standard techniques. The net result is that the code is fast at startup (useful for loading web pages quickly) but gets faster the more that it runs.

One last detail is that *languages* are not compiled or interpreted. Usually, C code is compiled, but there are C interpreters available that make it easier to debug or visualize the code that's being run (they're often used in introductory programming classes - or at least, they used to be.) JavaScript used to be thought of as an interpreted language until some JS engines started compiling it. Some Python implementations are purely interpreters, but you can get Python compilers that generate native code. Now, some languages are *easier* to compile or interpret than others, but there's nothing stopping you from making a compiler or interpreter for any particular programming language. There's a theoretical result called the [Futamura projections](https://en.wikipedia.org/wiki/Partial_evaluation#Futamura_projections) that shows that anything that can be interpreted can be compiled, for example.