HTTP vs HTTPS

[ref from cloudflare](https://www.cloudflare.com/zh-cn/learning/ssl/why-is-http-not-secure/)

# HTTPS is HTTP with encryption  
HTTPS uses TLS(SSL) to encrypt normal HTTP requests and responses.   
As a result, HTTPS is far more secure than HTTP.   
A website that uses HTTP has http:// in its URL, while a website that uses HTTPS has https://.

# Why HTTP is not secure?
- HTTP is textual-based, all info is not encrypted
- other people can monitor the network connection and capture HTTP packet
- if sensitive info is submitted via HTTP, other people can see it

# What's HTTPS?
- HTTPS uses TLS (or SSL) to encrypt HTTP requests and responses
- instead of the text, an attacker would see a bunch of seemingly random characters.

before
> GET /hello.txt HTTP/1.1  
User-Agent: curl/7.63.0 libcurl/7.63.0 OpenSSL/1.1.l zlib/1.2.11  
Host: www.example.com  
Accept-Language: en  

after
> t8Fw6T8UV81pQfyhDkhebbz7+oiwldr1j2gHBB3L3RFTRsQCpaSnSBZ78Vme+DpDVJPvZdZUZHpzb

## What's TLS
TLS uses a technology called __public key encryption__: there are two keys, a public key and a private key, and the public key is shared with client devices via the server's __SSL certificate__. When a client opens a connection with a server, the two devices use the public and private key to agree on new keys, called __session keys__, to encrypt further communications between them.  

All HTTP requests and responses are then encrypted with these session keys, so that anyone who intercepts communications can only see a random string of characters, not the plaintext.

---

server authentication is not discussed here
