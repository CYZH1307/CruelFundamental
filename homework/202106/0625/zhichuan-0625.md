# HTTPS

## Process

1. c->s: hello, let's set up a secure ssl session
2. s->c: hello, here is my certificate
   public key and ca
3. c->s: here is one time encryption key for our session
   verify the ca
   use public key to encrypted a shared key
4. server decrypts session key using its private key and establishes a secure session
   use private key to decrypted the shared key and use it for session


## Encryption

- Symmetric Encryption
- Asymmetric Encryption(time consumed)

## CA

man in the middle attack(client can not ensure the public key belongs to the website)

sign: data [T] (domain) --hash--> hashed data[T'] --private key--> signature[S] --[S] + [T]--> certificate

verify: get [T] and [S] --public key decrypted(browser will instal some trusted certificate)--> [S'] --hash--> [T'] --> [S'] == [T']
