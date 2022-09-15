# 如何避免 DNS 劫持

#### 本地 DNS 劫持

杀毒，检查DNS配置是否正确

#### MitM DNS 劫持

MitM 即中间人攻击，最基本的可以使用 DNSSEC 来防止劫持。但 DNSSEC 仅对查询结果签名，不对查询结果加密，因此中间人仍可窃听到查询结果，并可能阻断该次查询

可以使用 [DoH (DNS over HTTPS)](https://zh.wikipedia.org/zh-hans/DNS_over_HTTPS) 或者 [DoT (DNS over TLS)](https://en.wikipedia.org/wiki/DNS_over_TLS) 来加密查询结果。

DoH 的端口为443，TCP协议，即可以将DNS Query隐藏在众多HTTPS流量中；

DoT 的端口为853，UDP协议，由于 DoT 具有专用端口，因此即使请求和响应本身都已加密，具有网络可见性的任何人都发现来回的 DoT 流量。

相比之下，DoH 由于是封装在HTTPS中的，比 UDP + TLS 要慢。但 DoH 的隐私性更强。

此外，[DNSCrypt](https://zh.wikipedia.org/zh-hans/DNSCrypt) 也可以防范 MitM DNS 劫持

#### 由运营商操纵的 DNS 劫持

有的 ISP 的 DNS 会对特定网站返回错误的IP地址。

> 攻击者借由其在网络拓扑中的特殊位置，发送比真实的DNS回应更早到达攻击目标的伪造DNS回应。 一部连上了互联网的电脑一般都会使用互联网服务提供商提供的递归DNS服务器，这个服务器通常都会将部分客户曾经请求过的域名暂存起来。缓存污染攻击就是针对这一特性，影响服务器的用户或下游服务。

可以使用类似 Smart DNS 的软件来尽可能避免这种情况。即不要信任单个 DNS，从多个 DNS 源获取结果