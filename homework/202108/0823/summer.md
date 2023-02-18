Dirty Read: a dirty read is the situation when a transaction reads a data that has not yet been committed.
Non Repeatable Read: This happens when a transaction reads same row twice, and get a different values each time.
Phantom Read: When two queries are executed but the rows retrieved by the two are different.


Four Isolation Levels:

1. Read Uncommitted: lowest level, prevents nothing.
select statements are performed in a nonlocking fashion, but a possible ealier version of a row might be used. 

2. Read Committed: the isolation level guarantees that any data is committed at the moment it is read, prevents from dirty reads.
It holds a read or write lock on the current row, prevents others from reading, updating or deleting it.
Each consistent read, even within the same transaction, sets and reads its own fresh snapshot.

Because gap locking is disabled, phantom row problems may occur, as other sessions can insert new rows into gaps.

3. Repeatable Read: most restrictive isolation level, prevents from dirty reads and non-repeatable reads.
The transaction holds read locks on all rows it references and write locks on all rows it inserts, udpates and deletes. 

4. Serializable: The highest level, prevents all
The concurrent executing transactions appears to be serially executing.
