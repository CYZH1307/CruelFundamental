# 什么是LRU缓存？你还了解其他缓存策略吗？

LRU stands for Least Recently Used, i.e., everytime the cache is going to evict an item, it will always discard the least recently used one. This cache is effective because of the temporal locality. Processor tends to access or re-use the instruction and data within a small period of time.

Another cache replacement algorithm that I know is CLOCK. It'a an approximation of LRU but much easier to implement. All items will be associated with a reference bit. When it is accessed, the reference bit will be set to 1. All items will be organized as a circle. And we want to keep a clock hand to track the current position of this clock. If the reference bit of the item is 0, then just evict it, else set it to 0.