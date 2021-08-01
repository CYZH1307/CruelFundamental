# 针对你熟悉的编程语言和版本，描述锁的实现

## Linux
- TODO-read-source-code
- #include <pthread.h>
- int pthread_mutex_init(pthread_mutex_t* mutex, const pthread_mutexattr_t* attr)
- int pthread_mutex_lock(pthread_mutex_t* mutex)
- int pthread_mutex_unlock(pthread_mutex_t* mutex)
- int pthread_mutex_destroy(pthread_mutex_t* mutex)
- https://pubs.opengroup.org/onlinepubs/7908799/xsh/pthread_mutex_lock.html

## C++11新支持的
- std::mutex::lock
- __gthread_mutex_unlock
- __gthread_active_p
- __gthrw_(__pthread_key_create）// in <pthread.h>
- https://www.cplusplus.com/reference/mutex/mutex/

## ACE的实现
- template<class ACE_LOCK> ACE_INLINE int ACE_Guard<ACE_LOCK>::ACE_Guard(ACE_LOCK &l)
- ACE_Thread_Mutex::acquire()
- ACE_OS::thread_mutex_lock
- ACE_OS::mutex_lock
- ACE_OSCALL_RETURN(pthread_mutex_lock(ACE_mutex_t* mutex)
- 和C++内建锁不同，需要检查 ACE_Guard::locked() 的返回值
- 或者使用替代方案 ACE_GUARD_RETURN / ACE_GUARD_REACTION
