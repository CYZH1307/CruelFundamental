# 2 thread log

## go

```go
package main

import (
    "fmt"
    "sync"
)

var len = 10

func odd(ch chan int, group *sync.WaitGroup) {
    defer group.Done()
    for i := 1; i <= len; i++ {
        ch <- i
        if i%2 == 1 {
            fmt.Println("odd-thread:", i)
        }
    }
}

func even(ch chan int, group *sync.WaitGroup) {
    defer group.Done()
    for i := 1; i <= len; i++ {
        <- ch
        if i%2 == 0 {
            fmt.Println("even-thread:", i)
        }
    }
}

func main() {
    msg := make(chan int)
    var s sync.WaitGroup
    s.Add(2)
    go odd(msg, &s)
    go even(msg, &s)
    s.Wait()
}
```

## python

ref: https://leetcode.com/problems/print-zero-even-odd/discuss/337499/5-Python-solutions-using-threading-(Barrier-Condition-Event-Lock-Semaphore)-with-explanation