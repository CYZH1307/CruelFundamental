# grpc http2

## http2 vs http1.1

|                          | HTTP 2     | HTTP 1.1   |
|--------------------------|------------|------------|
| Transfer                 | Binary     | Text       |
| Headers                  | Compressed | Plain text |
| multiplexing             | Yes        | No         |
| requests per connections | Multiple   | 1          |
| server push              | Yes        | No         |

## header compress

header static table:

1: authority
2: method
....

动态添加header

## multiplexing

http 1.1 只能串行

引入流，和帧，流有id，这样帧可以穿插在一起发送


## binary

+---------------+
 |Pad Length? (8)|
 +---------------+-----------------------------------------------+
 |                            Data (*)                         ...
 +---------------------------------------------------------------+
 |                           Padding (*)                       ...
 +---------------------------------------------------------------+