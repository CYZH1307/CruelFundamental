# 0621 - Type URL

- user types URL in browser address bar
- browser will check DNS cache in browser, OS, router or ISP
- a recursive DNS query will be executed if hostname is not found in DNS cache
- browser will create a TCP connection with 3-way handshare
- client: SYNC, server: ACK, client: SYNC
- sends request to server with IP and port
- web server forwards request to application server
- application will query info from database if needed
- application server assembles response
- responses contains status code, compression type, cache policy, cookie and so on
- browser displays HTML contents
- QED
