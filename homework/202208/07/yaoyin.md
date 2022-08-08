# 请简述RSA算法的加解密流程

## Public key and private generation

1. Find 2 sufficient big enough primes, $p$ and $q$, whereas $p \neq q$. Let $n = p \times q$.

1. $\varphi(n) = (p-1)(q-1)$
1. Choose a random integer $e \in (1, \varphi(n)) $ and $e$ is coprime with $\varphi(n)$.
1. Calculate the inverse $d$ of $e$ module $\varphi(n)$, i.e., $ed = 1 \  mod \ \varphi(n)$
1. Now we have public key $(e, n)$ and private key $(d, n)$.

## Encryption

Original Message: $M$.

Encrypted Message: $C = M^e \mod n $

## Decryption

$M = C^d \ mod \ n $: