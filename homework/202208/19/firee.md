### 用你熟悉的语言实现哲学家就餐问题

```go
package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var mu sync.Mutex
var soup [5]int

func eat(i int) {
	for true {
		mu.Lock()
		if soup[i] == 0 && soup[(i+1)%5] == 0 {
			soup[i] = 1
			soup[(i+1)%5] = 1
		} else {
			time.Sleep(100 * time.Millisecond)
			mu.Unlock()
			continue
		}
		mu.Unlock()
		fmt.Printf("%d is eating\n", i)
		s := rand.Intn(1000)
		time.Sleep(time.Millisecond * time.Duration(s))
		soup[i] = 0
		soup[(i+1)%5] = 0
	}
}

func main() {
	for i := 0; i < 5; i++ {
		go eat(i)
	}
	for true {
		time.Sleep(10)
	}
}

```

