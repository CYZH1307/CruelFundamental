void fun(int *const a)
{
    if (a)
    {
        *a+=1;
    }
    else{
        //do something,may cause error,may demand
    }
    
}

int main()
{
    int b=2;
    fun(&b);
    return 0;
}