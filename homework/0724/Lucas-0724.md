# Java内建数组的排序算法
- https://www.jianshu.com/p/d7ba7d919b80

## 快排临界阈值 QUICKSORT_THRESHOLD
- 数组长度小于286，则用快排
- 大于286，用归并排序

## 插入排序临界阈值 INSERTION_SORT_THRESHOLD
- 数组长度小于47，则用插入排序

## 归并排序临界阈值 MAX_RUN_COUNT
- 在归并排序前检测，是否具有分组排序
- 如果组数大于MAX_RUN_COUNT/67时，仍用归并排序
