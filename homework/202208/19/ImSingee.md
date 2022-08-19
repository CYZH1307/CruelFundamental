# 用你熟悉的语言实现哲学家就餐问题

```go
package main

import (
	"context"
	"fmt"
	"math/rand"
	"sync"
	"sync/atomic"
	"time"
)

type Task struct {
	ctx    context.Context
	cancel context.CancelFunc

	n         int
	resources []uint32

	wg sync.WaitGroup
}

func (task *Task) Do(id int) {
	defer task.wg.Done()

	for task.ctx.Err() == nil {
		func() {
			// 获取资源 ID
			resourceId1, resourceId2 := task.getResourceId(id)
			// 获取资源（并在退出时逆序释放）
			task.lock(resourceId1)
			defer task.unlock(resourceId1)
			task.lock(resourceId2)
			defer task.unlock(resourceId2)

			// 完成任务（这里用随机定时 1-50ms 表示）
			fmt.Printf("%d: Work Start\n", id)
			time.Sleep(time.Duration(rand.Float64() * float64(50*time.Millisecond)))
			fmt.Printf("%d: Work End\n", id)
		}()

		// 每次完成任务后都休息一会（这里用随机定时 1-50ms 表示）
		fmt.Printf("%d: Relax Start\n", id)
		time.Sleep(time.Duration(rand.Float64() * float64(50*time.Millisecond)))
		fmt.Printf("%d: Relax End\n", id)
	}

	fmt.Printf("%d: Done\n", id)
}

func (task *Task) getResourceId(userid int) (resourceId1, resourceId2 int) {
	switch userid {
	case task.n - 1:
		return task.n - 1, 0
	default:
		return userid, userid + 1
	}
}

func (task *Task) Run() {
	for i := 0; i < task.n; i++ {
		task.wg.Add(1)
		go task.Do(i)
	}

	task.wg.Wait()
	fmt.Println("Done")
}

func (task *Task) lock(resourceId int) {
	for atomic.CompareAndSwapUint32(&task.resources[resourceId], 0, 1) {
		return
	}
}

func (task *Task) unlock(resourceId int) {
	atomic.StoreUint32(&task.resources[resourceId], 0)
}

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 1*time.Second)
	defer cancel()

	task := &Task{
		ctx: ctx, cancel: cancel,
		n:         5,
		resources: make([]uint32, 5),
	} // 资源初始化

	task.Run()

}
```
