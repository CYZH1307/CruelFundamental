# Lock

lock is to protect data integrity and atomicity in concurrent applications

## Optimistic locking

### CAS(compare and set)

ABA problem, resource cost(keep trying)

### Version number

commit version must bigger than version

## Pessimistic locking

assume that all the users are trying to access the same record 
heavy

## Scenario

less conflict: Optimistic locking
other: Pessimistic locking