### 什么是洗牌算法？10000 个员工抽 10 个人中奖，如何使用该算法完成？

```
vector<int> nums;
srand(time(0));
int n = nums.size();
for(int i = 0; i < k; i++){
    int j = rand() % (n - i);
    swap(nums[i], nums[j]);
}
// 前 i 个数字就是随机采样结果
```
