# GET和POST的区别

    1.传递参数：GET通过url，多个参数以&连接，POST通过request body
    2.缓存请求：GET会默认被缓存，POST默认不缓存。
    3.收藏为书签/历史记录：GET支持保存书签与保存参数在历史记录中，POST不支持。
    4.安全性：POST比GET安全，因为GET的参数直接放在url上。但要彻底杜绝明文攻击，POST也不保险，必须采用https。
    5.编码方式与数据类型：GET只能接受url编码方式以及ASCII字符，POST无这方面限制。

# GET的方法参数有大小限制吗？
    协议本身没有，但客户端（浏览器）和服务端会限制。如IE11限制url大小为2kb，chrome为2Mb
