```c++
#include <vector>

using namespace std;

void next_permutation(vector<int> &nums) {
    int n = nums.size();
    int i = n - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) i--;
    int j = n - 1;
    if (i >= 0) {
        while (nums[i] >= nums[j]) j--;
        swap(nums[i], nums[j]);
    }
    reverse(nums.begin() + i + 1, nums.end());
}
```