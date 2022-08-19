# 用你熟悉的语言实现生产者消费者问题

## 一生产者一消费者

阻塞

OR

单队列（削峰填谷）

```go
package main

import (
	"context"
	"fmt"
	"math/rand"
	"strconv"
	"time"
)

type Task struct {
	Id string
}

func NewProducer(ctx context.Context) <-chan Task {
	ch := make(chan Task, 200)

	go func() {
		defer close(ch)

		id := 1
		for ctx.Err() == nil {
			task := Task{strconv.Itoa(id)}
			id++

			ch <- task

			time.Sleep(time.Duration(rand.Intn(100)) * time.Millisecond)
		}
	}()

	return ch
}

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 1*time.Second) // 1s 后自动停止 producer
	defer cancel()

	tasks := NewProducer(ctx)

	for task := range tasks {
		fmt.Println("Consume task", task.Id)
	}
}

```

## 多生产者一消费者

```go
package main

import (
	"context"
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Task struct {
	Id string
}

func RunProducer(ctx context.Context, wg *sync.WaitGroup, producerId int, ch chan<- Task) {
	defer wg.Done()

	id := 1
	for ctx.Err() == nil {
		task := Task{fmt.Sprintf("%d-%d", producerId, id)}
		id++

		ch <- task

		time.Sleep(time.Duration(rand.Intn(100)) * time.Millisecond)
	}
}

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 1*time.Second) // 1s 后自动停止 producer
	defer cancel()

	tasks := make(chan Task, 200)

	// 启动五个生产者
	wg := &sync.WaitGroup{}
	for i := 1; i <= 5; i++ {
		wg.Add(1)
		go RunProducer(ctx, wg, i, tasks)
	}
	go func() { // 当生产者全部关闭时，自动关闭 tasks（以通知消费者关闭）
		wg.Wait()
		close(tasks)
	}()

	// 启动消费者
	for task := range tasks {
		fmt.Println("Consume task", task.Id)
	}
}

```

## 一生产者多消费者

```go
package main

import (
	"context"
	"fmt"
	"math/rand"
	"strconv"
	"sync"
	"time"
)

type Task struct {
	Id string
}

func NewProducer(ctx context.Context) <-chan Task {
	ch := make(chan Task, 200)

	go func() {
		defer close(ch)

		id := 1
		for ctx.Err() == nil {
			task := Task{strconv.Itoa(id)}
			id++

			ch <- task

			time.Sleep(time.Duration(rand.Intn(100)) * time.Millisecond)
		}
	}()

	return ch
}

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 1*time.Second) // 1s 后自动停止 producer
	defer cancel()

	// 启动生产者
	tasks := NewProducer(ctx)

	// 启动三个消费者
	wgC := &sync.WaitGroup{}
	for i := 1; i <= 3; i++ {
		i := i
		wgC.Add(1)
		go func() {
			defer wgC.Done()

			for task := range tasks {
				fmt.Println(i, "Consume task", task.Id)
			}
		}()
	}

	wgC.Wait()
}

```

## 多生产者多消费者

```go
package main

import (
	"context"
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Task struct {
	Id string
}

func RunProducer(ctx context.Context, wg *sync.WaitGroup, producerId int, ch chan<- Task) {
	defer wg.Done()

	id := 1
	for ctx.Err() == nil {
		task := Task{fmt.Sprintf("%d-%d", producerId, id)}
		id++

		ch <- task

		time.Sleep(time.Duration(rand.Intn(100)) * time.Millisecond)
	}
}

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 1*time.Second) // 1s 后自动停止 producer
	defer cancel()

	tasks := make(chan Task, 200)

	// 启动五个生产者
	wg := &sync.WaitGroup{}
	for i := 1; i <= 5; i++ {
		wg.Add(1)
		go RunProducer(ctx, wg, i, tasks)
	}
	go func() { // 当生产者全部关闭时，自动关闭 tasks（以通知消费者关闭）
		wg.Wait()
		close(tasks)
	}()

	// 启动三个消费者
	wgC := &sync.WaitGroup{}
	for i := 1; i <= 3; i++ {
		i := i
		wgC.Add(1)
		go func() {
			defer wgC.Done()

			for task := range tasks {
				fmt.Println(i, "Consume task", task.Id)
			}
		}()
	}

	wgC.Wait()
}

```
