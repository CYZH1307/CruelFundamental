The MySQL replication feature allows a server - the master, to send all changes to another server - the slave, and the
slave tries to apply all changes to keep up-to-date with the master. Replication works as follows:

Whenever the master's database is modified, the change is written to a file, the so-called binary log, or binlog. This
is done by the client thread that executed the query that modified the database.

The master has a thread, called the dump thread, that continuously reads the master's binlog and sends it to the slave.

The slave has a thread, called the IO thread, that receives the binlog that the master's dump thread sent, and writes it
to a file: the relay log.

The slave has another thread, called the SQL thread, that continuously reads the relay log and applies the changes to
the slave server.