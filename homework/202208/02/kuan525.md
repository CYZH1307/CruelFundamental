```go
package main

import (
	"fmt"
	"sync"
)

var ans int64 = 0
var mutex sync.Mutex

func get_sum(a, b int64, done func()) {
	defer done()
	var tmp int64 = 0
	for i := a; i < b; i++ {
		tmp += i
	}
	mutex.Lock()
	ans += tmp
	mutex.Unlock()
}

func main() {
	var wg sync.WaitGroup
	wg.Add(13)
	// 123456789
	for i := 0; i < 12; i++ {
		go get_sum(int64(i*10000000), int64((i+1)*10000000), wg.Done)
	}
	go get_sum(int64(12*10000000), int64(123456789+1), wg.Done)

	wg.Wait()
	fmt.Println(ans)

	return
}

```