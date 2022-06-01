# C++: 讲讲C++17的新特性
 * 模板类无需指定类型，如 vector a;
 * 结构化绑定：pair、tuple等在声明时自动推导每一个元素的类型
 * if与switch语句中允许初始化只有该语句作用域使用的变量，如if (int x = Func(); x < 10) {}
 * 全新的字符串转化函数from_chars和to_chars，使用步骤上和可读性上较为不便，但性能优越，它的性能是 stoi 的 4.5 倍、atoi 的 2.2 倍、istringsteam 的 50 倍。
 * 全新的字符串函数std::string_view，用以避免在使用临时字符串变量时触发整串的拷贝。
 * 全新的读写锁接口std::shared_mutex。
