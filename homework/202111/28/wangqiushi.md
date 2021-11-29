G1：

适合用于较大的堆的垃圾回收器。
软实时，低延时，可设定目标。
JDK9+的默认GC，尽可能少调优，用于替代CMS

G1的内存布局
• 将堆分成若⼲个等⼤的区域
• -XX:G1HeapRegionSize=N 2048 by default

G1工作流程
- Young-only phase
• Initial Mark
• Remark
• Cleanup
- Space-reclaimation phase

Fully young GC
• STW (Evacuation Pause)
• 构建CS (Eden+Survivor)
• 扫描GC Roots
• Update RS
• Process RS
• Object Copy
• Reference Processing
- G1记录每个阶段的时间，⽤于⾃动调优
• 记录Eden/Survivor的数量和GC时间
• 根据暂停⽬标⾃动调整Region的数量
• 暂停⽬标越短，Eden数量越少
• 吞吐量下降
• -XX:+PrintAdaptiveSizePolicy
• -XX:+PrintTenuringDistribution

Mixed GC (Old GC)
• 当堆⽤量达到⼀定程度时触发
• -XX:InitiatingHeapOccupancyPercent=N
• 45 by default
• Old GC是并发(concurrent)进⾏的
• 三⾊标记算法：不暂停应⽤线程的情况下进⾏标记
