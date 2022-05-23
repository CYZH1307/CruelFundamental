#### C++: 实现C++ std::next_permutation

先从后往前找第一个 $a[i] < a[i+1]$

然后从后往前找第一个$a[j] > a[i]$

然后交换$a[i]$和$a[j]$ `reverse(a[i+1], a[j])`