redis 用途

# Login and cookie caching
use token cookies to record user login info & visiting behaviors  
if all data are written to relational db, the load is too high.  

we can use redis for fast storing & updating such info. usually it's 10-100 faster than relational db.

# Web page caching
use redis to save dynamically generate web page contents so that we can avoid regenerating same things for every request.
