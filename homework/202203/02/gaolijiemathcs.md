### strstr的实现 Native, B-M, B-M-H, KMP 及其复杂度

#### [28. 实现 strStr()](https://leetcode-cn.com/problems/implement-strstr/)

strstr() 是个字符串匹配的函数。

strstr(str1,str2)用于判断字符串str2是否为str1的子串，如果是就返回str2在str1首次出现的位置。

int n = str1.length(), m = str2.length();

- Native：暴力，从左到右依次匹配，当出现不匹配的时候，将字符串挪到下一个发起点，重新匹配。时间复杂度:O(n*m); 空间复杂度:O(1)

- KMP：KMP从左往右匹配，出现不匹配的时候，会检查匹配成功的部分是否存在相同的前缀和后缀，如果存在，就跳转到相同前缀的的下一个位置继续向下匹配。引入next数组加速找下一个匹配点过程，时间复杂度O(n+m) 空间复杂度O(m)
- BM：模式串从右向左匹配，不匹配模式串从左往右滑动，滑动距离依据引入好后缀和坏字符数组规则来取滑动距离，根据坏字符串进行字符串移动。
- BMH：只用坏字符，改进BM算法。



