## 2022/08/06

### 什么是基数树(`Radix Trie`)？

一种字典树的扩展，每一次分支为几个方向可以自己决定

#### `cpp`实现

```cpp
#include <bits/stdc++.h>
using namespace std;
const int N = 5, M = 50000;
struct RadixT {
    string val;
    int p[N];
} rt[M];

int idx = 0;

int insert(int x, string s) {
    int f = 0;
    for(int j = x; j; j /= N) {
        int son = j % N;
        if(rt[f].p[son] == 0) {
            rt[f].p[son] = idx;
            f = idx++;
        } else f = rt[f].p[son];
    }
    rt[f].val = s;
}
string query(int x) {
    int f = 0;
    for(int j = x; j; j /= N) {
        int son = j % N;
        f = rt[f].p[son];
    }
    return rt[f].val;
}

int main(){
	ios::sync_with_stdio(0);cin.tie(0);cout.tie(0);
	
	int n; cin >> n;
	for(int i = 0; i < n; i++) {
	    int x; string s; cin >> x >> s;
	    
	    insert(x, s);
	}
	
	int q; cin >> q;
	while(q--) {
	    int x; cin >> x;
	    cout << query(x) << '\n';
	}
	
	return 0;
}
```

