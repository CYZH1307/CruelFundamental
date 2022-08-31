```python3
@timer
def do_something():
  pass

def timer(f):
  def inner():
    t1 = time.now()
    x = f()
    t2 = time.now()
    print("Time:", t2-t1)
    return x
  return inner
```
