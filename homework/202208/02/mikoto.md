## 用你熟悉的语言写一个并行从0到123456789求和的函数

```
using ll = long long;
ll sum() {
    ll res = 0;
    int n = 123456789;
    auto pre = async([n](){
            ll res = 0;
            for(int i = 0; i < n / 2; i ++)
                res += i;
            return res;
           )};
    for(int i = n / 2; i <= n; i ++) {
        res += i;
    }
    return res + pre.get();
}
```
