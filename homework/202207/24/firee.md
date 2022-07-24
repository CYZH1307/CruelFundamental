### 什么是洗牌算法？10000 个员工抽 10 个人中奖，如何使用该算法完成？

洗牌算法相当于等概率随机选一个排列

```python

import random


def fun(a, x):
    n = len(a)
    for i in range(x):
        j = random.randint(i, n-1)
        a[i], a[j] = a[j], a[i]
    return a[:x]


a = list(range(10000))

print(fun(a, 10))
#[3106, 3742, 3001, 1124, 5817, 6294, 4912, 7622, 9982, 9802]

```

对于每个位置 i，每个数能放到那个位置上的概率是 n-1/n _ n-2/n _ .... \* i/n = 1/n
