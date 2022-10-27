## 什么排序的时间复杂度下限可以突破O(nlogn)

基于比较的排序不可能快过O(nlogn)，只有不基于比较的排序算法才可能突破此下限。

如[基数排序](https://zh.wikipedia.org/zh-hans/%E5%9F%BA%E6%95%B0%E6%8E%92%E5%BA%8F)的时间复杂度为O(kN)，在某些情况下快过O(nlogn)