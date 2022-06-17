void fun(int &a)
{
    a+=1;
}

int main()
{
    int b=2;
    fun(b);
    return 0;
}