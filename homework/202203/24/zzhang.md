Linux下怎么快速知道文档总共有多少行: 命令行, C API 或者别的自己用的语言的API



命令行：`wc -l xx.txt` 



C++:

count `\n`

```c++
int main() { 
    int number_of_lines = 0;
    std::string line;
    std::ifstream myfile("textexample.txt");

    while (std::getline(myfile, line))
        ++number_of_lines;
    std::cout << "Number of lines in text file: " << number_of_lines;
    return 0;
}
```



```c++
int main() {
    std::ifstream myfile("textexample.txt");

    // new lines will be skipped unless we stop it from happening:    
    myfile.unsetf(std::ios_base::skipws);

    // count the newlines with an algorithm specialized for counting:
    unsigned line_count = std::count(
        std::istream_iterator<char>(myfile),
        std::istream_iterator<char>(), 
        '\n');

    std::cout << "Lines: " << line_count << "\n";
    return 0;
}
```

