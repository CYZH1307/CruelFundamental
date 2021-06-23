Please describe what happens when you click on a URL in your browser?


首先是根据URL分成域名和URI，域名被路由到DNS解析程序（ISP提供），再由DNS解析程序访问DNS跟域名服务器根据域名取回目标IP。
之后用目标IP和本机的路由表中的掩码做与运算，确定下一跳IP，用下一跳IP通过ARP协议换取下一跳mac地址，然后用mac地址寻找下一跳的主机，开始传输数据包，
通过反复的寻找下个路由节点，数据包最终被传输到了负载均衡服务器（lvs），通过负载均衡服务器（lvs）将请求分配给nginx上，先进行三次握手，然后nginx拆分URL，
拆分成URI，根据uri将请求发到对应的一个服务器节点上，进行请求，请求结束后会将web页面和后端数据一同返回给用户浏览器，浏览器对web页面进行渲染。
