![img](oyyko.assets/v2-73db63a5abb3cac70f913155a854cf29_1440w.webp)



使用git merge 命令将 `master` 分支合并到 `feature`分支中：

```
复制git checkout feature
复制git merge master
```

缩写为一句代码就是：

```
复制git merge feature master
```

![git-merge](oyyko.assets/S5YmfCK7wW1JxTB.png)

由上图可知，git merge 会在 `feature` 分支中新增一个新的 **merge commit**，然后将两个分支的历史联系在一起

- 使用 merge 是很好的方式，因为它是一种**非破坏性的**操作，对现有分支不会以任何方式被更改。
- 另一方面，这也意味着 `feature` 分支每次需要合并上游更改时，它都将产生一个额外的合并提交。
- 如果master 提交非常活跃，这可能会严重污染你的 `feature` 分支历史记录。不过这个问题可以使用高级选项 `git log` 来缓解

使用git rebase 命令将 `master` 分支合并到 `feature`分支中：

```
复制git checkout feature
复制git rebase master
```

缩写为一句代码就是：

```
复制git rebase feature master
```

![git-rebase](oyyko.assets/F1qIGKTNDW6aRul.png)

- rebase 会将整个 `feature` 分支移动到 `master` 分支的**顶端**，从而有效地整合了所有 master 分支上的提交。
- 但是，与 merge 提交方式不同，rebase 通过为原始分支中的每个提交创建全新的 commits 来重写项目历史记录,特点是仍然会在`feature`分支上形成线性提交
- rebase 的主要好处是可以获得更清晰的项目历史。首先，它消除了 git merge 所需的不必要的合并提交；其次，正如你在上图中所看到的，rebase 会产生完美线性的项目历史记录，你可以在 feature分支上没有任何分叉的情况下一直追寻到项目的初始提交。

**git rebase原理再深入：**

将`master`分支代码合并到`feature`上：

![rebase](oyyko.assets/kdAPQeMRN7DhyrY.png)

- 这边需要强调一个概念：**reapply(重放)**，使用rebase并不是简单地好像你用**ctrl-x/ctrl-v**进行剪切复制一样
- rebase会依次地将你所要操作的分支的所有提交应用到目标分支上

合并过程如下图：

![git-rebase-1](oyyko.assets/SogmKiIswFJG3V6.png)

从上图可以看出，在对特征分支进行rebase之后，其等效于创建了新的提交。并且老的提交也没有被销毁，只是简单地不能再被访问或者使用。

> 实际上在执行rebase的时候，有两个隐含的注意点：

1.在重放之前的提交的时候，**Git会创建新的提交**，也就是说即使你重放的提交与之前的一模一样Git也会将之**当做新的独立的提交**进行处理。

2.**Git rebase并不会删除老的提交**，也就是说你在对某个分支执行了rebase操作之后，老的提交仍然会存放在.git文件夹的objects目录下。



- **git merge**优点是分支代码合并后不破坏原分支的代码提交记录，缺点就是会产生额外的提交记录并进行两条分支的合并，
- **git rebase** 优点是无须新增提交记录到目标分支，rebase后可以将对象分支的提交历史续上目标分支上，形成线性提交历史记录，进行review的时候更加直观
- git merge 如果有多人进行开发并进行分支合并，会形成复杂的合并分支图，如下图：

![git-merge](oyyko.assets/6dW3fTI27B1Xbro.png)

> 问题：既然rebase如此有用，那么可以使用rebase完全取代merge吗？ 答案：不能！

git rebase的黄金原则：

- **不能在一个共享的分支上进行Git rebase操作。**

**总结：**

- 融合代码到公共分支的时使用`git merge`,而不用`git rebase`
- 融合代码到个人分支的时候使用`git rebase`，可以不污染分支的提交记录，形成简洁的线性提交历史记录。

# 5.rebase的黄金原则

> 问题：为什么不能再一个共享的分支上进行Git rebase操作呢？

所谓共享的分支，即是指那些存在于远端并且允许团队中的其他人进行Pull操作的分支，比如我们Git工作的master分支就是最常见的公共分支。

假设现在Bob和Anna在同一个项目组中工作，项目所属的仓库和分支大概是下图这样：

![rebase-1](oyyko.assets/CeRwsKUWxoOmbnV.png)

现在Bob为了图一时方便打破了原则，正巧这时Anna在特征分支上进行了新的提交，此时的结构图大概是这样的：

![rebase-2](oyyko.assets/xWeQj5HPCcyTIAd.png)

当Bob推送自己的分支到远端的时候，现在的分支情况如下：

![rebase-3](oyyko.assets/Eb3Ch4UeVBWaxjH.png)

然后呢，当Anna也进行推送的时候，她会得到如下的提醒，Git提醒Anna她本地的版本与远程分支并不一致，需要向远端服务器拉取代码进行同步：

![rebase-4](oyyko.assets/IcByegf5CYwOL63.png)

在Anna提交之前，分支中的Commit序列是如下这样的：

```
复制A--B--C--D'   origin/feature // GitHub
复制
复制A--B--D--E    feature        // Anna
```

在进行Pull操作之后，Git会进行自动地合并操作，结果大概是这样的：

![rebase-5](oyyko.assets/nSuZJRONGkIsHxa.png)

这个第M个提交即代表着合并的提交，也就是Anna本地的分支与Github上的特征分支最终合并的点，现在Anna解决了所有的合并冲突并且可以Push她的代码，在Bob进行Pull之后，每个人的Git Commit结构为：

![rebase-6](oyyko.assets/1gymbXko7IhKWfF.png)

看到上面这个混乱的流线图，相信你对于Rebase和所谓的黄金准则也有了更形象深入的理解。

> 假设下还有一哥们Emma，第三个开发人员，在他进行了本地Commit并且Push到远端之后，仓库变为了：

![rebase-7](oyyko.assets/3jsGSy7Qei4g8a2.png)

- 这还只是仅有几个人，一个特征分支的项目因为误用rebase产生的后果。如果你团队中的每个人都对公共分支进行rebase操作，那么后果就是乱成一片。
- 另外，相信你也注意到，在远端的仓库中存有大量的重复的Commit信息，这会大大浪费我们的存储空间。
- 因此，**不能在一个共享的分支上进行Git rebase操作,避免出现项目分支代码提交记录错乱和浪费存储空间的现象。**

# 6.使用rebase合并多次提交记录

> rebase和merge不同的作用还有一个就是合并分支多次提交记录。

在分支开发的过程中，我们常常会出现为了调试程序而多次提交代码记录，但是这些记录的共同目的都是为了解决某一个需求，所以，是否可以将这些记录合并起来为一个新的记录会更方便进行代码的review呢？

**git rebase提供了合并记录的作用**

下面是一个合并的案例过程：

1.尝试合并分支的最近 4 次提交纪录

```
复制git rebase -i HEAD~4
```

2.这时候，会自动进入 vi 编辑模式：

![rebase-x](oyyko.assets/qiQa7GHzO2wUhIp.png)

进入编辑模式，第一列为操作指令，第二列为commit号，第三列为commit信息。

- pick：保留该commit；
- reword：保留该commit但是修改commit信息；
- edit：保留该commit但是要修改commit内容；
- squash：将该commit和前一个commit合并；
- fixup：将该commit和前一个commit合并，并不保留该commit的commit信息；
- exec：执行shell命令；
- drop：删除该commit。

按照如上命令来修改你的提交记录：

```
复制p 799770a add article
复制s 72530e4 add article
复制s 53284b1 add article
复制s 9f6e388 add article
```

成功合并了四条记录为一条：

![success](oyyko.assets/3YI6kjyh7ltDcmC.png)

如果保存的时候，你碰到了这个错误：

```
复制error: cannot 'squash' without a previous commit
```

说明你在合并记录的时候顺序错误了，压缩顺序应该是从下往上，而不是从上往下，否则就会触发上面的错误。也就是以新记录为准。

例如上面的例子写成了这样就是出现错误：

```
复制s 799770a add article
复制s 72530e4 add article
复制s 53284b1 add article
复制p 9f6e388 add article
```

中途出现异常退出了 vi窗口，执行下面命令可以返回编辑窗口：

```
复制git rebase --edit-todo
```

继续编辑合并记录的操作，修改完保存一下：

```
复制git rebase --continue
```

# 7.git pull –rebase的应用

## 7.1 场景介绍

同事都基于git flow工作流的话都会从`develop`拉出分支进行并行开发，这里的分支可能是多到数十个，然后彼此在进行自己的逻辑编写，时间可能需要几天或者几周。

在这期间你可能需要时不时的需要pull下远程develop分支上的同事的提交。这是个好的习惯，这样下去就可以避免你在一个无用的代码上进行长期的开发，回头来看这些代码不是新的代码。甚至是会面临很多冲突需要解决，而这个时候你可能还需要对冲突的部分代码进行测试和问题解决，你在有些时候pull代码的时候会有这样的一个提示：

![pull](oyyko.assets/lwtOEmdH9BI5fja.png)

通常习惯性的你可能会，”esc ：wq“，直接默认commit注释。然后你的commit log就多了一笔很不好看的log。

![log](oyyko.assets/noC7k4KzTswPUqm.png)

## 7.2 如何移除多余的merge commit

> 很简单，只要你在pull时候需要习惯性的加上—rebase参数，这样可以避免很多问题。

`git pull --rebase`可以免去分支中出现大量的merge commit，基本原理就像上面rebase一样，合并过程会直接融合到目标分支的顶端，形成线性历史提交。

## 7.3 分析过程

1.正常情况下的分支commit路线并且当前develop分支上有三个commit。

2.现在我们两个项目开始启动，需要分别拉出两个分支独立开发，提交过程如图：

![pull](oyyko.assets/yLIcez1GkDOJ6aF.png)

3.我们分别checkout –b 出来两个分支，独立开发互不干扰。正常情况下，如果这两个分支的改动都没有冲突的时候，一切都很顺利的。

4.我在develop_newfeature_authorcheck里修改了点东西，push到develop。然后checkout 到develop_newfeature_apiwrapper。

5.当我再git pull，这将会把develop_newfeature_authorcheck分支的修改直接拉下来于本地代码merge，且产生一个commit，也就是merge commit。

![pull](oyyko.assets/BgW9QrZYuCFxMOV.png)

6.使用`git pull –rebase`产生的提交结果就完全不一样,rebase并不会产生一个commit提交，而是会将你的E commit附加到D commit的结尾处。在看commit log时，不会多出你所不知道的commit出来。其实此处的F commmit是无意义的，它只是一个merge commit。

![pull](oyyko.assets/6Ghz8mYrNgCEH9L.png)

> 很明显，git pull –rebase 会使commit看起来很自然,因为代码都有一个前后依赖，看起来更加的直观。

### 注意：

在公共的分支上，例如`master`仍然要遵守rebase黄金原则，不用使用`git pull --rabase`进行代码的拉取，更改代码的历史提交记录。



