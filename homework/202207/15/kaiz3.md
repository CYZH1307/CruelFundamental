保证一个类仅有一个实例，并提供一个访问它的全局访问点。

```
Singleton *Singleton::instance = nullptr;
Singleton* getInstance()
{
    if (instance == NULL)
    {
    lock();
        if (instance == NULL)
        {
               instance = new Singleton();
        }
        unlock();
    }

    return instance;
}
```
