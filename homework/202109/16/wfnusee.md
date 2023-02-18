# Why we use http2 in gRPC

## The goal of network transmission
- Faster Transmission : always make the overhead tiny
- Lower Resource Consumption


## Comparison Between HTTP1.1 and HTTP2

|  |HTTP 2|HTTP 1.1|
|--|--|--|
|Multiplexing|✅|❌|
|Header Compression|HPACK|❌|
|Transfer|Binary|Text|
|Server Push|✅|❌|

## Problem
head-of-line blocking

## Solution
QUIC
