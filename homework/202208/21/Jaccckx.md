右值引用
判断某个表达式是左值还是右值：

可位于赋值号（=）左侧的表达式就是左值；反之，只能位于赋值号右侧的表达式就是右值。

有名称的、可以获取到存储地址的表达式即为左值；反之则是右值。

声明：

int num = 10;
//int && a = num;  //错误，右值引用不能初始化为左值
int && a = 10;

a += 1; // 正确，可以直接对右值引用进行修改
std::cout << a << std::endl;
万能引用
template<typename T>
void func(T& p) {
    cout << "左值" << endl;
}
template<typename T>
void func(T&& p) {
    cout << "右值" << endl;
}
int main() {
    int num = 114514;
    func(num); // 左值
    func(114514); // 右值
    return 0;
}
万能引用（Universal Reference）：使用T&&类型的形参既能绑定右值，又能绑定左值。注意：只有发生类型推导的时候，T&&才表示万能引用；否则表示右值引用。

上面的例子可以使用万能引用精简为：

template<typename T>
void func(T&& p) {
    cout << p << endl;
}
int main() {
    int num = 114514;
    func(num);
    func(114514);
    return 0;
}
Footer
© 2022 GitHub, Inc.
Footer navigation
Terms
Privacy
