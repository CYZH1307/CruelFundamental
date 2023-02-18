# Linux下怎么快速知道文档总共有多少行: 命令行, C API 或者别的自己用的语言的API

Python: 
```python
with open(file_name, 'r') as f:
  cont = f.read().split('\n')
print(len(cont))
```
