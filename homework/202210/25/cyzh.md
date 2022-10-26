## 2022/10/25

### git中merge和`rebase`区别

- `rebase`把当前的commit放到公共分支的最后面，merge把当前的commit和公共分支合并在一起

- 用merge命令解决完冲突后会产生一个commit，而用`rebase`命令解决完冲突后不会产生额外的commit