# 简述session与cookie

# session #
session是特定的客户端和特定的服务端在有效时间内建立的会话，服务端在校验权限后会为这个会话产生唯一的session_id并共享给客户端，服务端保存session实例。  
客户端通信时带上这个id，若其代表的session没过期，则允许其获取服务端资源。session实例会占用服务端空间。

# cookie #
cookie是在http连接中，保存在客户端浏览器的连接信息，如位置、用户首选项等。在有效时间内可以让用户不必重新输入信息即可访问特定http站点。

# 区别 #
session保存在服务端，cookie在客户端浏览器。
session安全性更高，但占用服务器性能。

