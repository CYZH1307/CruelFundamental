洗牌算法：随机排列的数组。

``` c++
int a[N] = {0, 1, 2, 3, 4 ... N-1};

for(int i = 0; i < N; i++) {
    int tmp = rand()%(N-i);
    swap(a[i],a[i+tmp]);
}
```
