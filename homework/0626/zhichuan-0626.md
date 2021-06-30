# Memory model

TODO： http://dmitrysoshnikov.com/compilers/writing-a-memory-allocator/

## JS

datatype: basic, reference(object)

references

- reference-counting garbage collection
(circular reference)

- mark-and-sweep algorithm
(not possible to explicitly trigger garbage collection)

## RUST

Threads
  single
    ownership
      unique -> allocate
        stack -> interior-mutability
          not need -> T or mut
          need -> Cell<T> RefCell<T>
        heap ->Box<T> or mut
      shared -> Mutable
        immutable -> Rc<T>
        mut -> Rc<Cell<T>> Rc<RefCell<T>>
  Multiple
    ownership
      unique -> R/W -> AtomicT, Mutex<T>, RwLock<T>
    shared -> Mutable
      immutable -> Arc<T>
      mut -> R/W -> Arc<AtomicT>, Arc<Mutex<T>>, Arc<RwLock<T>>
           

## TCMalloc

Thread Cache allocator

- page (x64 8k)
- span [page, page...] base unit
- thread cache(no lock, each thread has its own cache)
- central cache(the number equals to thread cache, lock, thread cache can borrow size from it and need to return)
- page heap(the abstract of heap, central cache will ask for space from page heap)

Go TCMalloc + gc 