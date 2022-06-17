void fun(int *const a)
{
    *a+=1;
}

int main()
{
    int b=2;
    fun(&b);
    return 0;
}