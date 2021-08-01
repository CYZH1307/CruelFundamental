# Redis vs Memcached

## Data type

Redis:

- string
- hash
- list
- set
- sorted set

Memcached

- string

use less overhead memory but need more time to do loading, serialization, storage...

## Scale

redis is single-threaded, can clustering, grows well horizontally
clustering mode use master/slave architecture, for each master, there are two slave for redundancy

memcached is multi-threaded. easy to scaled vertically

## Evict strategy

redis:

- no eviction
- all keys LRU
- volatile LRU
- all keys random
- volatile random
- volatile TTL

memcached: LRU support only

## Persistence

redis: RDB snapshot and AOF (use COW)
RDB file has version limitation

AOF log is better if data loss is not acceptable

memcached: no persistence support