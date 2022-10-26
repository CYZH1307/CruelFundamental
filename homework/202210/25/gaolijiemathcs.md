### git中merge和rebase区别

merge和rebase都是用来合并分支的

- 采用merge和rebase后，git log的区别，merge命令不会保留merge的分支的commit，rebase会保留所有的commit：
  - rebase会把你当前分支的 commit 放到公共分支的最后面,所以叫变基。就好像你从公共分支又重新拉出来这个分支一样。
  - merge 会把公共分支和你当前的commit 合并在一起，形成一个新的 commit 提交
- 处理冲突的方式：
  - （一股脑）使用`merge`命令合并分支，解决完冲突，执行`git add .`和`git commit -m'fix conflict'`。这个时候会产生一个commit。
  - （交互式）使用rebase命令合并分支，解决完冲突，执行git add .和git rebase --continue，不会产生额外的commit。这样的好处是，‘干净’，分支上不会有无意义的解决分支的commit；坏处，如果合并的分支中存在多个commit，需要重复处理多次冲突。

ref:https://blog.csdn.net/muzidigbig/article/details/122519949