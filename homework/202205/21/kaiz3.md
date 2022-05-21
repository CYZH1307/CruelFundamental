#include <vector>

using namespace std;

class Solution {
public:
    void nextPermutation(vector<int>& nums) {
        int n = nums.size();
        
        int cur = n - 2;
        
        while (cur >= 0 && nums[cur] >= nums[cur + 1]) {
            cur--;
        }
        
        int i = n - 1;
        
        if (cur >= 0) {
            while (nums[cur] >= nums[i]) {
                i--;
            }
            swap(nums[i], nums[cur]);
        }
        
        reverse(nums.begin() + cur + 1, nums.end());
    }
};