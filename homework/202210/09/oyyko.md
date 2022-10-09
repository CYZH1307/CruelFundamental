**缓存穿透：**查询一个必然不存在的数据。比如文章表，查询一个不存在的id，每次都会访问DB，如果有人恶意破坏，很可能直接对DB造成影响。

解决办法：对所有可能查询的参数以hash形式存储，在控制层先进行校验，不符合则丢弃。

### 缓存穿透

缓存穿透是指用户查询数据，在数据库没有，自然在缓存中也不会有。这样就导致用户查询的时候，在缓存中找不到，每次都要去数据库中查询。

解决思路：

1，如果查询[数据库](http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=17&is_app=0&jk=9d81f77002e20c6d&k=%CA%FD%BE%DD%BF%E2&k0=%CA%FD%BE%DD%BF%E2&kdi0=0&luki=7&mcpm=0&n=10&p=baidu&q=smileking_cpr&rb=0&rs=1&seller_id=1&sid=6d0ce20270f7819d&ssp2=1&stid=9&t=tpclicked3_hc&td=1682280&tu=u1682280&u=http%3A%2F%2Fwww.th7.cn%2Fdb%2Fnosql%2F201510%2F136276.shtml&urlid=0)也为空，直接设置一个默认值存放到缓存，这样第二次到缓冲中获取就有值了，而不会继续访问数据库，这种办法最简单粗暴。

2，根据缓存数据Key的规则。例如我们公司是做机顶盒的，缓存数据以Mac为Key，Mac是有规则，如果不符合规则就过滤掉，这样可以过滤一部分查询。在做缓存规划的时候，Key有一定规则的话，可以采取这种办法。这种办法只能缓解一部分的压力，过滤和系统无关的查询，但是无法根治。

3，采用布隆[过滤器](http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=17&is_app=0&jk=9d81f77002e20c6d&k=%B9%FD%C2%CB%C6%F7&k0=%B9%FD%C2%CB%C6%F7&kdi0=0&luki=6&mcpm=0&n=10&p=baidu&q=smileking_cpr&rb=0&rs=1&seller_id=1&sid=6d0ce20270f7819d&ssp2=1&stid=9&t=tpclicked3_hc&td=1682280&tu=u1682280&u=http%3A%2F%2Fwww.th7.cn%2Fdb%2Fnosql%2F201510%2F136276.shtml&urlid=0)，将所有可能存在的数据哈希到一个足够大的BitSet中，不存在的数据将会被拦截掉，从而避免了对[底层](http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=17&is_app=0&jk=9d81f77002e20c6d&k=%B5%D7%B2%E3&k0=%B5%D7%B2%E3&kdi0=0&luki=2&mcpm=0&n=10&p=baidu&q=smileking_cpr&rb=0&rs=1&seller_id=1&sid=6d0ce20270f7819d&ssp2=1&stid=9&t=tpclicked3_hc&td=1682280&tu=u1682280&u=http%3A%2F%2Fwww.th7.cn%2Fdb%2Fnosql%2F201510%2F136276.shtml&urlid=0)存储系统的查询压力。关于布隆[过滤器](http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=17&is_app=0&jk=9d81f77002e20c6d&k=%B9%FD%C2%CB%C6%F7&k0=%B9%FD%C2%CB%C6%F7&kdi0=0&luki=6&mcpm=0&n=10&p=baidu&q=smileking_cpr&rb=0&rs=1&seller_id=1&sid=6d0ce20270f7819d&ssp2=1&stid=9&t=tpclicked3_hc&td=1682280&tu=u1682280&u=http%3A%2F%2Fwww.th7.cn%2Fdb%2Fnosql%2F201510%2F136276.shtml&urlid=0)，详情查看：基于BitSet的布隆过滤器(Bloom Filter) 

大并发的缓存穿透会导致缓存雪崩。



