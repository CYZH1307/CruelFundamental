If a specific user is continually making lots of requests within a short period of time, we can put a constraint on his
operation rate. For example, a next request is only available when several seconds or minutes have passed before his
last request.

If there are a large deal of incoming requests from a specific IP address, we can apply the above approach to that IP
address.

And also, if one or several of our interfaces are being maliciously frequently invoked, we can consider lowering our
rate of returning responses the invoked interfaces.