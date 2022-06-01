import os

num = int(input())
for i in range(1, num+1):
    os.system(f"mkdir {i:02d}")
    # print(f"mkdir {i:02d}")
