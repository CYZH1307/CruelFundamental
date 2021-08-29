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
