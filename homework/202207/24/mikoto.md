### 什么是洗牌算法？10000个员工抽10个人中奖应该如何使用该算法完成？


洗牌算法就是随机打乱数组的方法，经典洗牌算法保证对于一个长度为n的数组，经过洗牌后，对于任一元素a，它出现在n个下标的概率是相等的。

经典实现：
```
vector<int> nums;
int n = nums.size();
mt19937 rd(time(0))
for(int i = n - 1; i >= 0; i --) {
    swap(nums[i], nums[rd() % (i + 1));
}
```

使用该算法抽奖很简单，只需要将10000个员工放在数组中，然后执行一次洗牌算法，取前10个即可
