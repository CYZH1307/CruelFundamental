2022 03 24 Oyyko 残酷八股

- Linux下怎么快速知道文档总共有多少行: 命令行, C API 或者别的自己用的语言的API

用`wc -l`命令

例如：

```bash
$ echo "1\n2\n3" | wc -l
$ 3
```

如果认为行数的定义就是`\n`的个数加一的话，那么可以用C这样统计

```c
#include <stdio.h>

int main(int argc, char **argv)
{
    FILE *f;
    char c;
    int n = 0;
    f = fopen(argv[1], "r");
    do
    {
        c = fgetc(f);
        n += c == '\n';
    } while (c != EOF);
    printf("%d\n", n + 1);
}
```



