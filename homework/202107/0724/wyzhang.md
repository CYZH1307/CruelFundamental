## 针对你熟悉的一门语言，描述数组排序的实现

C++ std::sort()

The implementation of sort is IntroSort, which is the hybrid sorting algorithm using three sorting alorithm. (quick sort, heap sort, and insertion sort).

Here is the code implementation of IntroSort. 

basically,
```C++
if (array length < 16) {
  insertionSort();
} 
if (useable recursion depth == 0) {
  heapSort()
} 
// default
quickSort()
```

quickSort() implementation

```C++
void quickSort(vector<int>& nums, int low, int high) {
    int pivot = nums[high]; 
    int i = (low - 1); 
    for (int j = low; j <= high - 1; j++) {
        if (nums[j] < pivot) {
            i++; 
            swap(nums[i], nums[j]);
        }
    }
    swap(nums[i + 1], nums[high]);
    return (i + 1);
}
int partition(vector<int>& nums, const int low, const int high) {
  int pivot = nums[low];
  while (low <= high) {

  }

}
```



Reference:

https://www.geeksforgeeks.org/internal-details-of-stdsort-in-c/