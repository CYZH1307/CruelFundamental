# Next Permutation in C++
- https://code.woboq.org/gcc/libstdc++-v3/include/bits/stl_algo.h.html

## 实现
- 长度小于等于1，立即返回
- 从右向左找到第一个更大的，然后接着找个第一个更小的
- 上面两个位置交换，中间的部分递减，所以要翻转变成递增
- 如果从右向左一直是更大，则该数为最大，翻转整个数，变成最小返回

```cpp

    // P1: find rightmost LT + rightmost smallest GT
    // Time: O(N)
    /* Steps:           [4, 1, 3, 2]
                        [4, 2, 1, 3]
        find the rightmost LT right
                            |
        find the right smallest GT step 1
                                  |
        switch          [4, 2, 3, 1]
        already sorted  
        reverse         [4, 2, 1, 3]
    */
    
      for(;;)
        {
          _BidirectionalIterator __ii = __i;
          --__i;
          if (__comp(__i, __ii))
            {
              _BidirectionalIterator __j = __last;
              while (!__comp(__i, --__j))
                {}
              std::iter_swap(__i, __j);
              std::__reverse(__ii, __last,
                             std::__iterator_category(__first));
              return true;
            }
        }
        
class Solution {
public:

  void nextPermutation(vector<int>& nums) {

    int n = nums.size();
    if (n < 2) {
      return;
    }

    // 1 2
    // 2 1
    // 1 2 3
    // 1　　2　　7　　4　　3　　1
    // 
    int i, j;
    for (i = n - 2; i >= 0; i--) {
      if (nums[i] < nums[i + 1]) {
        // cout << "i: " << i << "\tj: " << j << endl;
        for (j = n - 1; j > i; j--) {
          if (nums[j] > nums[i]) {
            break;
          }
        }
        // cout << i << "\t" << j << endl;
        swap(nums[i], nums[j]);
        reverse(nums.begin() + i + 1, nums.end());
        return;
      }
    }

    reverse(nums.begin(), nums.end());
  }
};
```
