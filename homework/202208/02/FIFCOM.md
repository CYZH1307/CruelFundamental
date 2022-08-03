## 用你熟悉的语言写一个并行求从0到123456789的数字之和的函数

不会啊(哭

蠢哭了

```rust
use futures::executor::block_on;
use futures::future::join_all;

async fn as_add(start: u64, mut end: u64) ->  u64 {
    let mut tmp_sum = 0;
    if end > 123456789 {
        end = 123456789;
    }
    for i in start..=end {
        tmp_sum += i;
    }
    tmp_sum
}

async fn async_main() -> u64 {
    let futures: Vec<_> = (0..123456789)
        .step_by(1000000)
        .map(|i| as_add(i, i + 999999))
        .collect();
    let results = join_all(futures).await;
    results.iter().fold(0, |sum, i| sum + i)
}

fn main() {
    let x = block_on(async_main());
    println!("{}", x);
}
```