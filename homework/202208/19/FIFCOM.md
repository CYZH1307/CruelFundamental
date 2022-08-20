## 用你熟悉的语言实现哲学家就餐问题

> 在古代，一个富有的慈善家捐赠了一所学院来安排五个著名的哲学家。每个哲学家都有一个房间，他可以在其中从事他自己专业的思考活动；学校里有一个公共的餐厅，这里配备有一个圆形的桌子，桌子周边有五个椅子，每个椅子用坐在上面的哲学家的名字来标记。他们以逆时针的方向来围着桌子坐。每个哲学家的左侧放了一个金叉，桌子中间有一大碗不断添满的意大利面。哲学家将花费大部分时间去思考；但是当他饿了，他就会去餐厅，坐在自己的椅子上，拿起在自己左边的叉子来吃意大利面。但是意大利面条比较难吃到，需要第二个叉子才能把面条送到嘴里。因此，哲学家也要拿起他右边的叉子。当哲学家吃完面条后，他会把两个叉子都放下，离开自己的椅子，并继续思考。当然了，一个叉子一次只能有一个哲学家来使用。如果其他哲学家想要使用你的叉子，那么他仅仅需要等到这个叉子没人用了即可。

```rust
use std::thread;
use std::sync::{Mutex, Arc};

struct Philosopher {
	name: String,
	left: usize,
	right: usize,
}

impl Philosopher {
	fn new(name: &str, left: usize, right: usize) -> Philosopher {
		Philosopher {
			name: name.to_string(),
			left: left,
			right: right,
		}
	}

	fn eat(&self, table: &Table) {
		let _left = table.forks[self.left].lock().unwrap();
		let _right = table.forks[self.right].lock().unwrap();

		println!("{} 正在吃", self.name);

		thread::sleep_ms(1000);

		println!("{} 吃完了", self.name);
	}
}

struct Table {
	forks: Vec<Mutex<()>>,
}

fn main() {
	let table = Arc::new(Table { forks: vec![
		Mutex::new(()),
		Mutex::new(()),
		Mutex::new(()),
		Mutex::new(()),
		Mutex::new(()),
	]});

	let philosophers = vec![
		Philosopher::new("A", 0, 1),
		Philosopher::new("B", 1, 2),
		Philosopher::new("C", 2, 3),
		Philosopher::new("D", 3, 4),
		Philosopher::new("E", 0, 4),
	];

	let handles: Vec<_> = philosophers.into_iter().map(|p| {
		let table = table.clone();

		thread::spawn(move || {
			p.eat(&table);
		})
	}).collect();

	for h in handles {
		h.join().unwrap();
	}
}
```
