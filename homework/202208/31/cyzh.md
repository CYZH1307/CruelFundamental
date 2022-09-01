## 2022/08/31

### 什么是装饰器模式？用你喜欢的语言实现一个例子。 

- 装饰器模式就是给一个对象加一些新功能，要求装饰对象和被装饰对象实现同一个接口
- 语义和代理模式很像



```go
package main

import (
    "fmt"
    "time"
)

func myFunc() {
    fmt.Println("Hello World")
    time.Sleep(1 * time.Second)
}

func coolFunc(a func()) {
    fmt.Printf("Starting function execution: %s\n", time.Now())
    a()
    fmt.Printf("End of function execution: %s\n", time.Now())
}

func main() {
    fmt.Printf("Type: %T\n", myFunc)
    coolFunc(myFunc)
}
```

