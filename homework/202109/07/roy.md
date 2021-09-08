## Object Clear: Garbage Collection
1. How to make sure an object as garbage: analysis of reachability   
Do graph search from GC roots to detect if objects are reachable, if objects cannot be reached from those roots, it should be garbage collected. four type roots: 1. local variables are kept alive on a thread stack; 2. an alive thread; 3. static fields; 4. Java native interface reference
2. Basic garbage collection algorithm:   
minor GC - copying(used objects), assuming high mortality rate, copy faster, no frag: copy one space used objects to another, clear that space; next round, swap the functionalities of these two spaces.   
major GC - mark(unused objects)-compact: 1) scan all objects and mark unused objects. 2) delete them and compact objects left to make the unused memory space continuous. This makes ongoing allocations easier.  
full GC - clear the entire heap.  
3. General garbage collection process: moving objects in heap, “stop-the-world” event   
In the young generation, we do minor GC. we copy newly added and also alive objects in Eden space and used objects in “from” survivor space to “to” survivor space. After that, we should empty Eden space and survivor space. In this process, used objects are also aged, and when ages are met, a specific thread will move them to the old generation.   
In the old generation, we did major GC. we scan the whole generation and mark unused objects, then we delete them and compact objects left to make memory unused continuous.   
In the permanent generation, we do full GC. when class metadata is no longer needed and JVM needs to load other classes, it may be collected.  
Metaspace: The garbage collector now automatically triggers the cleaning of the dead classes once the class metadata usage reaches its maximum metaspace size.  
