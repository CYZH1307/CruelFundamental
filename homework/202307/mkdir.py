import os

num = int(input("请输入这个月有多少天："))
for i in range(1, num+1):
    os.system(f"mkdir {i:02d}")
    os.system(f"echo !*.md > {i:02d}/.gitignore")

print("ok")