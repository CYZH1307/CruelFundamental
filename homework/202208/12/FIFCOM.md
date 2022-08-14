## 堆排序原理？
```cpp
class eHeap {
private:
    int idx; // 堆已用的
    vector<int> heap; // 堆数组，从下标1开始
public:
    eHeap(signed size, vector<int> &arr) {
        idx = size;
        heap = arr;
        // 从size / 2的地方开始排序，可以使复杂度降至nlogn
        for (int i = size / 2; i >= 0; i --) 
            down(i);
    }

    void down(int x) {
        // 想象一个三个点的二叉树，x是树根，其他两个是a, b
        // 目的是为了将较大的heap[x]向下移动至heap[a]或者heap[b]
        // 二叉树的左节点下标是a = 2 * x，右节点b = 2 * x + 1

        // 找到三个点中的最小值，首先将x设为最小
        int min_ = x, a = 2 * x, b = 2 * x + 1;
        // 如果有左、右节点，则分别与其比较，最终得出最小值下标
        if (a <= idx && heap[a] < heap[min_]) min_ = a;
        if (b <= idx && heap[b] < heap[min_]) min_ = b;
        // 将x与最小值下标交换，并且让最小值继续下沉
        if (x != min_) {
            swap(heap[x], heap[min_]);
            down(min_);
        }
    }

    void pop() {
        // 弹出最小值，就是将堆数组末尾最大的数覆盖第一个数
        // 然后重排堆(由于是最大的数所以只需要down操作)
        heap[1] = heap[idx --];
        down(1);
    }

    int top() {
        // 小根堆的堆顶最小
        return heap[1];
    }
};
```

## 归并排序原理？

```cpp
void msort(vector<int> &arr, int l, int r) {
    if (l >= r) return;
    int mid = (l + r) >> 1;
    msort(arr, l, mid);
    msort(arr, mid + 1, r);
    // sort
    int cnt = 0, i = l, j = mid + 1;
    while (i <= mid && j <= r) {
        if (arr[i] < arr[j]) tmp[cnt ++] = arr[i ++];
        else tmp[cnt ++] = arr[j ++];
    }
    while (i <= mid) tmp[cnt ++] = arr[i ++];
    while (j <= r) tmp[cnt ++] = arr[j ++];
    // merge
    for (int i = l, j = 0; i <= r; i ++, j ++) arr[i] = tmp[j];
}
```

## 什么是稳定排序？
假定在待排序的记录序列中，存在多个具有相同的关键字的记录，若经过排序，这些记录的相对次序保持不变，即在原序列中，`a[i]=a[j]`，且`a[i]`在`a[j]`之前，而在排序后的序列中，`a[i]`仍在`a[j]`之前，则称这种排序算法是稳定的；否则称为不稳定的。

## 哪些排序算法是稳定的，哪些不稳定？

常见的稳定排序：
1. 冒泡排序
2. 插入排序
3. 折半插入排序
4. 归并排序

常见的不稳定排序：
1. 堆排序
2. 快速排序
3. 希尔排序
4. 直接选择排序
