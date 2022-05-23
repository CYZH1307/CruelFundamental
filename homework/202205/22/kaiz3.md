A process is an instance of a running program. A process contaisn it's code, data and heap section files in it's memory space. It also contains one or more threads. 

A thread is a basic unit of execution in a process. Multiple threads in the same process share the same memory space, but each thread has it's own register values 
and stack.


A coroutine is a function that can be suspend execution to be resumed later. Coroutines are stackless: they suspend execution by returning to the caller and the data 
that is required to resume execution is stored separately from the stack.This allows for sequential code that execustes asynchronousely, and also supports algorithms on 
lazy-computed infinite sequences and other uses.
