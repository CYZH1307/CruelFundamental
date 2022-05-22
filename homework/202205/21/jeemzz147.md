### C++: 实现C++ std::next_permutation

```
class Solution {
public:
    void nextPermutation(vector<int>& nums) {
        int n = nums.size();
        for(int i = n - 1; i > 0; i--){
            if(nums[i - 1] >= nums[i])continue;
            int x = i - 1;
            for(int j = n - 1; j > 0; j--){
                if(nums[j] > nums[x]){
                    swap(nums[j], nums[x]);
                    reverse(nums.begin() + i, nums.end());
                    return;
                }
            }
        }
        reverse(nums.begin(), nums.end());
    }
};
```
