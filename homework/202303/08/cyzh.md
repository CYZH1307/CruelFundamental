## 03月08日
### QUIC的流量控制和拥塞控制
#### Stream级别流量控制
> Stream可以认为就是一条HTTP请求，每个Stream都有独立的滑动窗口，每个Stream单独做流量控制，单个Stream阻塞不会阻塞其他的Stream

#### Connection级别流量控制
> 限制所有Stream加起来的总字节数，防止超过接受方的缓冲区

#### QUIC对于拥塞控制的改进
QUIC默认使用TCP的Cubic拥塞控制算法，但是也支持其他的拥塞控制算法。可以针对不同的应用设置不同的拥塞控制算法
