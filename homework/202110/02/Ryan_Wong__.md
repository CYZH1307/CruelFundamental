# Redis哨兵 #
哨兵机制是Redis用来监控主从切换的的机制，如果没有该机制，当主节点宕机，可能需要客户去更新新主节点的地址，并指派原有的其他从节点去复制新主节点的内容。  
该机制每隔一段时间（秒级别）去ping每个节点，根据响应判断当前主从节点是否已经故障，若主节点经过两轮下线确认后确定为故障，则该机制主持选出新的主节点、指派从节点的新的复制路径，并上传相关信息给客户端。
