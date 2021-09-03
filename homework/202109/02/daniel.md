
```python
import threading

if __name__ == '__main__':
	
	condition = threading.Condition()
	
	def even():
		with condition:
			for i in range(0, 10, 2):
				print(i)
				condition.wait() # release the lock and block
				condition.notify()
				

	def odd():
		with condition:
			for i in range(1, 10, 2):
				print(i)
				
				condition.notify()
				condition.wait()
			
			
	threading.Thread(target=even).start()
	threading.Thread(target=odd).start()
```

* wait 释放锁, 并block, 等待 notify 唤醒
* notify 不释放锁, 唤醒其他等待线程
* with 退出时, 释放锁