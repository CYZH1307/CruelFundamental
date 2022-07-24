### 什么是洗牌算法？10000个员工抽10个人中奖，如何使用该算法完成？

洗牌算法是通过随机取元素交换来获得随机性。

```java
// 得到一个在闭区间[min, max]的随机整数
int randInt(int min, int max);

// 第一种写法
void shuffle0(int[] arr) {
	int n = arr.length;
	for(int i = 0; i < n; i++) {
		int rand = randInt(i, n - 1);
        swap(arr[i], arr[rand]);
	}
}

// 第二种写法
void shuffle1(int[] arr) {
	int n = arr.length;
	for(int i = n - 1; i >= 0; i--) {
        int rand = randInt(0, i);
        swap(arr[i], arr[rand]);
    }
}

```

洗牌算法正确性准则：产生的记过必须有n!种可能。（满足长度为n的数组 全排列的个数）

ref:https://www.cnblogs.com/labuladong/p/12320471.html