Java Garbage Collection: Oracle’s Hotspot is mostly common.

The general steps:
1. Unreferenced objects are identified and marked as ready for garbage collection
2. Marked objects are deleted/
3. (Optionally) memory can be compacted after the garbage collector deletes objects.

The heap is divided into Younger Generation(Minor Garbage Collection), Old Generation(Major Garbage Collection) and Permanent Generation.

You can use the -Xmn flag to set the size of the Young Generation.
You can use the -Xms and -Xmx flags to set the size of the initial and maximum size of the Heap memory.
You can use the -XX:PermGen and -XX:MaxPermGen flags to set the initial and maximum size of the Permanent Generation.


HotSpot has 4 garbage collectors: Serial, Parallel, CMS(Concurrent Mask Sweep), G1(Garbage First)

Serial: This is designed for small applications running on single-threaded environments.

Parallel: The parallel collector is intended for applications with medium-sized to large-sized data sets that are run on multiprocessor or multithreaded hardware.
Multiple threads are used for minor garbage collection in the Young Generation. A single thread is used for major garbage collection in the Old Generation.

CMS: This is also known as the concurrent low pause collector. Multiple threads are used for minor garbage collection using the same algorithm as Parallel. Major garbage collection is multi-threaded, like Parallel Old GC, but CMS runs concurrently alongside application processes to minimize “stop the world” events.

G1: It is parallel and concurrent like CMS, but it works quite differently under the hood compared to the older garbage collectors. Although G1 is also generational, it does not have separate regions for young and old generations. Instead, each generation is a set of regions, which allows resizing of the young generation in a flexible way.
