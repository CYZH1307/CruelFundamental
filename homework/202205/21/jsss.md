# C++: 实现C++ std::next_permutation

[题目链接](https://leetcode.cn/problems/next-permutation/)

## 思路

1. 从后往前找到第一个位置`sep`, 满足`nums[sep] < nums[sep + 1]`
2. 将`sep`处的数和后边从后往前走第一个大于`nums[sep]`的位置的数交换.
3. `reverse(nums + sep + 1, nums + n)`

## 代码

```cpp
class Solution {
public:
    void nextPermutation(vector<int>& nums) {
        int n = nums.size();
        if (n < 2)
            return ;
        int sep = n - 2;
        while (sep >= 0 and nums[sep] >= nums[sep + 1])
            -- sep;
        if (sep >= 0) {
            int mx = sep + 1;
            for (int i = sep + 1; i < n and nums[i] > nums[sep]; i ++ )
                mx = i;
            swap(nums[sep], nums[mx]);
        }
        reverse(nums.begin() + sep + 1, nums.end());                 
    }
};
```