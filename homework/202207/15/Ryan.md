# 什么是单例模式？ C++或者Java如何实现它？（你自己最喜欢的语言即可）

单例是一种一个类只有一个实例的设计模式， 它提供一个访问该实例的全局节点。  
C++的简单单例模式通常使用私有化构造函数来实现

'''c++
class Singleton{
private:
    Singleton(){
        std::cout<<"constructor called!"<<std::endl;
    }
    Singleton(Singleton&)=delete;
    Singleton& operator=(const Singleton&)=delete;
    static Singleton* m_instance_ptr;
public:
    ~Singleton(){
        std::cout<<"destructor called!"<<std::endl;
    }
    static Singleton* get_instance(){
        if(m_instance_ptr==nullptr){
              m_instance_ptr = new Singleton;
        }
        return m_instance_ptr;
    }
    void use() const { std::cout << "in use" << std::endl; }
};

'''
