## 用你熟悉的语言实现生产者消费者问题

这里使用rust自带的mpsc库来实现，mpsc即Multi-producer, single-consumer

```rust
use std::sync::mpsc;
use std::thread;

fn main() {
    let mut v = vec![];
	// tx 指transmission, 即生产者
	// rx 指receiving，即消费者
    let (tx, rx) = mpsc::channel();

    for i in 0..=10 {
        let tx = tx.clone(); // 这里是变量遮蔽(shadowing)
		
		// 创建子线程
        let thr = thread::spawn(move || {
            let msg = format!("生产者的第{}条消息", i);
            tx.send(msg).unwrap(); // 发送消息
        });
        v.push(thr);
    }

    for _i in 0..=10 {
        let msg = rx.recv().unwrap();
        println!("消费者收到了: {}", msg);
    }

    for thr in v {
        thr.join().unwrap();
    }
}
```