针对你熟悉的一门语言，描述数组排序的实现？

1.首先判断数组长度是不是小于286，如果小于的话使用快速排序（Dual-Pivot Quicksort），

```
static void sort(int[] a, int left, int right,
                     int[] work, int workBase, int workLen) {
        // Use Quicksort on small arrays
        if (right - left < QUICKSORT_THRESHOLD) {
            sort(a, left, right, true);
            return;
        }
        ...
        }
```

进入这个快速排序先判断数组长度是否小于47，小于47就使用插入排序代替快速排序。

```
 private static void sort(int[] a, int left, int right, boolean leftmost) {
        int length = right - left + 1;

        // Use insertion sort on tiny arrays
        if (length < INSERTION_SORT_THRESHOLD) {
            if (leftmost) {
                /*
                 * Traditional (without sentinel) insertion sort,
                 * optimized for server VM, is used in case of
                 * the leftmost part.
                 */
                for (int i = left, j = i; i < right; j = ++i) {
                    int ai = a[i + 1];
                    while (ai < a[j]) {
                        a[j + 1] = a[j];
                        if (j-- == left) {
                            break;
                        }
                    }
                    a[j + 1] = ai;
                }
                ...
}
```

数组长度大于47就用快速排序（Dual-Pivot Quicksort）进行排序，首先用五个等分点将数组均匀的分为七份，然后用插入排序将这五个等分点排序，

1.1如果五个节点都是不相等的，然后以第二第四个等分点作为双轴进行快排因为最接近三等分点。

1.2.否则就使用单轴的快速排序

2.当数组长度大于286的时候，使用归并排序。使用count统计归并了多少次，当count达到了67次的时候，改用快排

