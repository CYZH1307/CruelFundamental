[ref](https://www.keyfactor.com/blog/what-is-dns-poisoning-and-dns-spoofing/)

DNS负责将域名翻译成由计算机识别的IP地址，从而完成网络的链接访问。  
但如果通过某种技术手段，取得域名的解析控制权，修改域名的解析记录，将该域名对应的IP地址指向非法地址或其他受控制的IP地址。那么当人们对该域名发起访问时，就会跳转到受控的虚假网站。  
这种情况就是DNS劫持。  
![dns](https://www.keyfactor.com/wp-content/uploads/DNS-Spoofing-Attack.png)

The biggest weakness that allows this type of attack to occur is the fact that the entire system for routing web traffic was built more for scale than for security. The current process is built on what’s called the User Datagram Protocol (UDP), a process that does not require senders or recipients to verify they are ready to communicate or verify who they are. This vulnerability allows hackers to fake identity information (which requires no additional verification) and step into the process to start redirecting DNS servers.

While this is absolutely an enormous vulnerability, it is not as simple as it sounds. To pull this off effectively, 
__a hacker must respond to a request within a few milliseconds before the legitimate source kicks in and include in their response detailed information like the port the DNS resolver is using and the request ID number.__

# How to protect?
- 1) Introduce DNS Security Extensions (DNSSEC)
- 2) Always encrypt data
- 3) Enable secure DNS configurations
