It may happen that when we add an element while iterating the vector, the size of the vector can get more than the
maximum size due to which a new memory is allocated to the vector and all elements are copied there. But, our iterator
still points to the previous old memory address. So, now we can say that iterator gets invalidated. This is one example
of invalidation. Given below are some rules for iterator invalidation.
***
The main difference between vector resize() and vector reserve() is that resize() is used to change the size of vector
where reserve() doesn’t. reserve() is only used to store at least the number of the specified elements without having to
reallocate memory. But in resize(), if the number is smaller than the current number then it resizes the memory and
deletes the excess space over it.