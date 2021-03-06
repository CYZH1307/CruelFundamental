### strStr的实现 Native, B-M, B-M-H, KMP 及其复杂度



int strStr(string s1, string s2)用以判断字符串s2是否s1的子串，如果是，则返回s2在s1首次出现的位置往后的s1子串。否则返回NULL。例如s2 = "for"，s1 = "abcforxyz"，则返回“forxyz”

设n = s1.size(), m = s2.size()。实现如下：

1. Native

   朴素匹配，遍历s1每个位置，记为s1[i]，往后扫描m个字符，每个偏移位置记为j，看s1[i+j-1]是否等于s2[j]（j<m && i+j-1<n），如出现不符合的字符，则i++。最坏情况下，对于每个i（n级别），j都必须跑m - 1次，复杂度为**O(n*m)**。

2. B-M

   s2从后往前匹配，不匹配模式从前往后滑动，滑动距离根据好后缀数组和坏字符数组来取最大的值以求跳过最多的无效匹配。复杂度按场景在**O(n*m)**~**O(n/m)**浮动。

3. B-M-H

   BM的改进算法

4. KMP

   用一个名为next的数组记录s1上前 i+1 位字符串里面前缀和后缀的最长长度。从前往后匹配s1中的s2，若出现不匹配，则至少已经匹配到s2的前缀sub2，若s1里存在以当前sub2为前缀、以一段相同的sub2位后缀的子数组，那么可以利用next数组直接跳到这个后缀位置继续匹配。省去中间一大段的无效匹配。复杂度为**O(n+m)**。



