#堆排序原理？归并排序原理？什么是稳定排序？哪些排序算法是稳定的，哪些不稳定？

## Heapsort

### Heapify operation
For current node, assume its subtree are heaps, exchange the current node with it children to make the whole tree as a heap.

### Heap sort

- Build a heap
- Exchange the first element with the last element.
- Remove the last element
- Heapify the first element
- Until all elements removed

## Merge sort

- Divide and conquer
- Split the array evenly
- Recursively merge sort two arrays
- Merge two sorted array.

## Stable vs not stable

Two elements have the same value. Will them change the ralated position after sorting? If yes, then the algorithm is untable, otherwise it's stable.

Unstable: quick sort
Stable: merge sort, radix sort
