class Solution {
public:
    void nextPermutation(vector<int>& nums) {
    	int n = nums.size(), k, l;
      // find the first asending number from backwards
    	for (k = n - 2; k >= 0; k--) {
            if (nums[k] < nums[k + 1]) {
                break;
            }
        }
    	if (k < 0) {
          // the whole array is descending, the next permutation is ascending
    	    reverse(nums.begin(), nums.end());
    	} else {
          // find the first number > the first asending number
    	    for (l = n - 1; l > k; l--) {
                if (nums[l] > nums[k]) {
                    break;
                }
          }
          // swap the two numbers
    	    swap(nums[k], nums[l]);
          // sort from k+1 to the end of the array to ascending order
          // if not using the library, use two pointers can avoid the edge case of integer division problem
    	    reverse(nums.begin() + k + 1, nums.end());
        }
    }
}; 
