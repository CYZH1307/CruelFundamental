# C++: STL中deque底层原理是什么？

deque维护了

* 1.一个连续数组map，每个map元素是指向一个子数组的指针。子数组里面的成员才是deque的真正成员
* 2.名为start的deque 迭代器，其中有cur、first、last、node四个成员，cur指向第一个子数组中第一个有效的成员、first指向第一个子数组最左边的成员（有可能是无效的）、last指向第一个子数组最右边的成员、node指向第一个子数组对应的map元素。
* 3.名为finish的deque 迭代器，其中有cur、first、last、node四个成员，cur指向最后一个子数组中第一个有效的成员、first指向最后一个子数组最左边的成员、last指向最后一个子数组最右边的成员（有可能是无效的）、node指向最后一个子数组对应的map元素。

如图所示：
![image](https://user-images.githubusercontent.com/10019865/168890989-fe3878eb-5d8a-4baf-b833-f8905bfdd7d1.png)
