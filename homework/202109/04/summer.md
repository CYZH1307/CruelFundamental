Bloom Filter is used to test whether an element is a member of a set.

The base data structure of a Bloom Filter is a Bit Vector. Each empty cell in the table represents a bit. The number below is its index. To add an element to the Bloom Filter, we simply hash it a few times and set the bits in the bit vector at the index of those hashes to 1.

To test for membership, you simply hash the string with the same functions, then see if those values are set in bit vector. If they are not, you know that the element is not in the set. If they are, you only know if might be, because another element or some combination of other elements could be the same sets.

应用：
1. 网页爬虫URL的去重，避免爬取相同的URL的地址
2. 反垃圾邮件，从数十亿垃圾列表邮件中判断某邮箱是否是垃圾邮箱
3. 缓存击穿，将已经存在的缓存放入布隆过滤器中，当黑客访问不存在的缓存时迅速返回，避免缓存和db挂掉
