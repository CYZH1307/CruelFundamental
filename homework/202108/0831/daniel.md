## 场景

当 client 期望根据不同的场景切换同一功能的不同用法, 比如地图软件里的路线规划:

用户可以选择步行, 公交, 自驾 等不同方式, 但是他们都是属于路线规划的功能.

## 普通实现

在 路线规划的实现当中, 我们根据 client 的不同选择进行不同的实现. 这样不好在于:

- 不符合 open close 原则
- 多团队开发, 会有confliction

## strategy behavior

- strategy interface: 接口类
- concrete algorithms: 不同的算法实现
- context: 提供给client 的接口

```python
class Strategy(ABC):
    @abstractmethod
    def do_algorithm(self, data: List):
        pass

class MySort(Strategy):
    def do_algorithm(self, data: List) -> List:
        return sorted(data)

class MyReverse(Strategy):
    def do_algorithm(self, data: List) -> List:
        return reversed(sorted(data))

class Context():
    def __init__(self, strategy: Strategy) -> None:
        self._strategy = strategy

    @property
    def strategy(self) -> Strategy:
        return self._strategy

    @strategy.setter
    def strategy(self, strategy: Strategy) -> None:
        self._strategy = strategy

    def do_some_business_logic(self) -> None:
        result = self._strategy.do_algorithm(["a", "b", "c", "d", "e"])
        print(",".join(result))
```