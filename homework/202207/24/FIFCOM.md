#### 什么是洗牌算法？

洗牌算法代码通过随机获取元素并交换来产生随机结果。 比较著名、常用且实现很简洁的是Knuth-Shuffle 算法。 原理：将数组分为已打乱和未打乱的前后两部分（初始时两者分别由0、n个元素），每次随机从未打乱部分中选择一个元素加入到已打乱部分中。 易得种数为n!

#### 10000个员工抽10个人中奖应该如何使用该算法完成？
类似问题：[用Rand7()实现Rand10()](https://leetcode.cn/problems/implement-rand10-using-rand7/)

```cpp
#include <bits/stdc++.h>
#define RAND_MAX 65536 // 设置std::rand()的范围为[0, 65536]

unsigned int rand10000() {
	// edge*10000为最接近65536*65536且是10000的倍数的数
	unsigned int edge = (RAND_MAX * (RAND_MAX - 1) + RAND_MAX) / 10000;
	edge *= 10000;
	int ret = 0;
	while (1) {
		// 构造[0, 65536 * 65536]均匀出现
		ret = RAND_MAX * (rand() - 1) + rand();
		// 剔除大于edge的数，使[0, edge]均匀出现
		if (ret <= edge) break;
	}
	// 取模，构造[0, 10000)均匀出现
	return ret % 10000;
}

int main() {
	// 设置id
	vector<int> person(10000);
	for (int i = 0; i < 10000; i ++) person[i] = i + 1;
	// 随机选取10人，移动到数组开头
	for (int i = 0; i < 10; i ++) {
		int x = rand10000();
		swap(person[i], person[x]);
	}
	// 输出中奖者id
	for (int i = 0; i < 10; i ++) {
	 	cout << person[i] << " ";
	}
	cout << endl;
	return 0;
}
```