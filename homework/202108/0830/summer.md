A thread has two modes: joinable mode, detachable mode

Joinable Thread: pthread_join() is a blocking call, it will block the calling thread until the other thread ends
-- Wait for other threads to finish
Detachable Thread: pthread_detach() will automatically release the resources on exit. (Deamon / background threads)
-- The thread is no longer associated with the actual thread of execution

Case 1: Never call join() or detach() on std::thread object with on associated executing thread
Double calling join()/detach() on a thread object will cause the program to terminate.
-- We should check if the thread is joinable ever time.


Case 2: Never forget to call either join or detach on a std::thread object with associated executing thread
If neither join or detach is called with a std::thread object that has associated executing thread then during object's destruct-or it will terminate the program.
