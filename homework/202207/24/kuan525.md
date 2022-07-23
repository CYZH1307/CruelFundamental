## 什么是洗牌算法？10000个员工抽10个人中奖，如何使用该算法完成？

### Fisher-Yates Shuffle算法

最早提出这个洗牌方法的是 Ronald A. Fisher 和 Frank Yates，即 Fisher–Yates Shuffle，其基本思想就是从原始数组中随机取一个之前没取过的数字到新的数组中，具体如下：
1. 初始化原始数组和新数组，原始数组长度为n(已知)；
2. 从还没处理的数组（假如还剩k个）中，随机产生一个[0, k)之间的数字p（假设数组从0开始）；
3. 从剩下的k个数中把第p个数取出；
4. 重复步骤2和3直到数字全部取完；
5. 从步骤3取出的数字序列便是一个打乱了的数列。

```c++
#define N 10
#define M 5
void Fisher_Yates_Shuffle(vector<int>& arr,vector<int>& res)
{
     srand((unsigned)time(NULL));
     int k;
     for (int i=0;i<M;++i)
     {
     	k=rand()%arr.size();
     	res.push_back(arr[k]);
     	arr.erase(arr.begin()+k);
     }
}
```
**时间复杂度为O(n*n),空间复杂度为O(n).**


### Knuth-Durstenfeld Shuffle  
Knuth 和 Durstenfeld  在Fisher 等人的基础上对算法进行了改进，在原始数组上对数字进行交互，省去了额外O(n)的空间。该算法的基本思想和 Fisher 类似，每次从未处理的数据中随机取出一个数字，然后把该数字放在数组的尾部，即数组尾部存放的是已经处理过的数字。
算法步骤为：
1. 建立一个数组大小为 n 的数组 arr，分别存放 1 到 n 的数值；
2. 生成一个从 0 到 n - 1 的随机数 x；
3. 输出 arr 下标为 x 的数值，即为第一个随机数；
4. 将 arr 的尾元素和下标为 x 的元素互换；
5. 同2，生成一个从 0 到 n - 2 的随机数 x；
6. 输出 arr 下标为 x 的数值，为第二个随机数；
7. 将 arr 的倒数第二个元素和下标为 x 的元素互换；
8. ……
9. 如上，直到输出 m 个数为止

``` c++
void Knuth_Durstenfeld_Shuffle(vector<int>&arr)
{
	for (int i=arr.size()-1;i>=0;--i)
	{
		srand((unsigned)time(NULL));
		swap(arr[rand()%(i+1)],arr[i]);
	}
} 
```

**时间复杂度为O(n),空间复杂度为O(1),缺点必须知道数组长度n.**


###  Inside-Out Algorithm
Knuth-Durstenfeld Shuffle 是一个内部打乱的算法，算法完成后原始数据被直接打乱，尽管这个方法可以节省空间，但在有些应用中可能需要保留原始数据，所以需要另外开辟一个数组来存储生成的新序列。

Inside-Out Algorithm 算法的基本思思是从前向后扫描数据，把位置i的数据随机插入到前i个（包括第i个）位置中（假设为k），这个操作是在新数组中进行，然后把原始数据中位置k的数字替换新数组位置i的数字。其实效果相当于新数组中位置k和位置i的数字进行交互。

如果知道arr的lengh的话，可以改为for循环，由于是从前往后遍历，所以可以应对arr[]数目未知的情况，或者arr[]是一个动态增加的情况。

证明如下：

原数组的第 i 个元素（随机到的数）在新数组的前 i 个位置的概率都是：(1/i) * [i/(i+1)] * [(i+1)/(i+2)] *...* [(n-1)/n] = 1/n，（即第i次刚好随机放到了该位置，在后面的n-i 次选择中该数字不被选中）。

原数组的第 i 个元素（随机到的数）在新数组的 i+1 （包括i + 1）以后的位置（假设是第k个位置）的概率是：(1/k) * [k/(k+1)] * [(k+1)/(k+2)] *...* [(n-1)/n] = 1/n（即第k次刚好随机放到了该位置，在后面的n-k次选择中该数字不被选中）。         

``` c++
void Inside_Out_Shuffle(const vector<int>&arr,vector<int>& res)
{
	res.assign(arr.size(),0);
	copy(arr.begin(),arr.end(),res.begin());
	int k;
	for (int i=0;i<arr.size();++i)
	{
		srand((unsigned)time(NULL));
		k=rand()%(i+1);
		res[i]=res[k];
		res[k]=arr[i];
	}
} 
```