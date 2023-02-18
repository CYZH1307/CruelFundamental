Vectors are same as dynamic arrays with the ability to resize itself automatically when an element is inserted or
deleted, with their storage being handled automatically by the container. Vector elements are placed in contiguous
storage so that they can be accessed and traversed using iterators. In vectors, data is inserted at the end. Inserting
at the end takes differential time, as sometimes there may be a need of extending the array. Removing the last element
takes only constant time because no resizing happens. Inserting and erasing at the beginning or in the middle is linear
in time. Lists are sequence containers that allow constant time insert and erase operations anywhere within the
sequence, and iteration in both directions.

List containers are implemented as doubly-linked lists; Doubly linked lists can store each of the elements they contain
in different and unrelated storage locations. The ordering is kept internally by the association to each element of a
link to the element preceding it and a link to the element following it.Compared to other base standard sequence
containers (array, vector and deque), lists perform generally better in inserting, extracting and moving elements in any
position within the container for which an iterator has already been obtained, and therefore also in algorithms that
make intensive use of these, like sorting algorithms.