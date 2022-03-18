### C++: 如何实现heap-only class，如何实现stack-only class

```c++
class NonCopyableClass{
  public:

  // state the compiler generated default constructor
  NonCopyableClass()= default;

  // disallow copying
  NonCopyableClass& operator = (const NonCopyableClass&) = delete;
  NonCopyableClass (const NonCopyableClass&) = delete;
  
  // disallow moving
  NonCopyableClass& operator = (NonCopyableClass&&) = default;
  NonCopyableClass (NonCopyableClass&&) = default;
};

class TypeOnStack {
  public:
    void * operator new(std::size_t)= delete;
  	// we assign delete to the operator new. 
  	// This means that an object of this class cannot occupy space on the heap.
};

class TypeOnHeap{
  public:
    ~TypeOnHeap()= delete;
  	// not allowed to make objects on the stack
};

int main() {
  NonCopyableClass nonCopyableClass;

  TypeOnStack typeOnStack;

  TypeOnHeap * typeOnHeap = new TypeOnHeap;
}
```



### Python: Python中有日志吗，怎么使用

Reference: [docs_python](https://docs.python.org/zh-cn/3/howto/logging.html)

- 对于命令行或程序的应用，结果显示在控制台。 => `print()`
- 在对程序的普通操作发生时提交事件报告(比如：状态监控和错误调查) => `logging.info()` 
- 提出一个警告信息基于一个特殊的运行时事件 => `warnings.warn()` 位于代码库中，该事件是可以避免的，需要修改客户端应用以消除告警；`logging.warning()` 不需要修改客户端应用，但是该事件还是需要引起关注
- 对一个特殊的运行时事件报告错误 => 引发异常
- 报告错误而不引发异常(如在长时间运行中的服务端进程的错误处理) => `logging.error()`, `logging.exception()`, `logging.critical()`



```python
import logging
logging.warning('Watch out!')  # will print a message to the console
logging.info('I told you so')  # will not print anything
```

写入到文件：

```python
import logging
logging.basicConfig(filename='example.log', encoding='utf-8', level=logging.DEBUG) # 由于我们设置的阈值是 DEBUG，所有信息都将被打印。
logging.debug('This message should go to the log file')
logging.info('So should this')
logging.warning('And this, too')
logging.error('And non-ASCII stuff, too, like Øresund and Malmö')
```

