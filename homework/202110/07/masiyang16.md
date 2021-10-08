#### FaceBook宕机原因

Reference: Understanding How Facebook Disappeared from the Internet https://blog.cloudflare.com/october-2021-facebook-outage/

Externally, we saw the BGP and DNS problems outlined in Facebook post but the problem actually began with a configuration change that affected the entire internal backbone. 
That cascaded into Facebook and other properties disappearing and staff internal to Facebook having difficulty getting service going again.

At 15:58 UTC we noticed that Facebook had stopped announcing the routes to their DNS prefixes. That meant that, at least, Facebook’s DNS servers were unavailable.
Meanwhile, other Facebook IP addresses remained routed but weren’t particularly useful since without DNS Facebook and related services were effectively unavailable.

But at around 15:40 UTC we saw a peak of routing changes from Facebook. Routes were withdrawn, Facebook’s DNS servers went offline, and one minute after the problem occurred

With those withdrawals, Facebook and its sites had effectively disconnected themselves from the Internet.
