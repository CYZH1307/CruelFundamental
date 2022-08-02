### 用你熟悉的语言写一个并行求从0到123456789的数字之和的函数

```java
import java.util.stream.LongStream;

class Solution {
    public long calSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }
}

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.calSum(123456789));
    }
}
```

并行流内部使用的是fork/join框架