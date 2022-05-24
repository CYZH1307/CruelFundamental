### C++: 讲一讲 std::move 和 std::forward

std::move执行到右值的无条件转换。就其本身而言，它没有move任何东西。

std::forward只有在它的参数绑定到一个右值上的时候，它才转换它的参数到一个右值。

std::move和std::forward在运行期都没有做任何事情。
