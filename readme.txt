对于SVN目录结构说明：

trunk：表示开发时版本存放的目录，即在开发阶段的代码都提交到该目录上。

branches：表示发布的版本存放的目录，即项目上线时发布的稳定版本存放在该目录中。

tags：表示标签存放的目录。

在这需要说明下分三个目录的原因，如果项目分为一期、二期、三期等，那么一期上线时的稳定版本就应该在一期完成时将代码copy到branches上，这样二期开发的代码就对一期的代码没有影响，如新增的模块就不会部署到生产环境上。而branches上的稳定的版本就是发布到生产环境上的代码，如果用户使用的过程中发现有bug，则只要在branches上修改该bug，修改完bug后再编译branches上最新的代码发布到生产环境即可。tags的作用是将在branches上修改的bug的代码合并到trank上时创建个版本标识，以后branches上修改的bug代码再合并到trunk上时就从tags的version到branches最新的version合并到trunk，以保证前期修改的bug代码不会在合并。


分支用于解决什么样的问题？
举例：

在手机游戏开发过程中，经常会遇到多种机型移植的问题。通常开发人员都说以一种机型作为 release 基础版本的目标，然后再此基础上进行相关的适配工作，如，键值修改，屏幕大小的修改单等。

然而同时维护多个版本是异常头疼的事情，因为很少有人能保证在移植之前，基础版本是没有 bug 的，特别是在工期很紧的情况下。这样一来，基础版本中出现了 bug ，就需要手动的“ Ctrl+C/Ctrl+V ”到其他的所有版本，各种版本的管理非常混乱，经常一不小心就会出现这样那样的问题。


而 SVN 的分支 (branch) 虽然不能做到自动将基础版本中的修改复制到其他版本中，却可以对各种版本的管理提供更有效和更规范的支持，避免了很多人为造成的问题。使用 SVN 来管理，可以将基础版本作为主干 (trunk) ，并从项目启动到 alpha 版本的推出，都可以在主干上进行开发。 alpha 版本发布以后，对于其他版本可以分别建立分支，如： branch_moto ， branch_s603 等


如何创建分支？

创建分支非常简单，只需在需要创建分支的工作目录上，使用TortoiseSVN → Branch/Tag命令，在 "To URL" 项指定待创建的分支 url 即可。具体 可查看TortoiseSVN的帮助文档中的“ Braching/Taging ”一节



如何在分支下工作？

假设我们的主干名为 trunk ，分支目录名为 branch 。 branch 实际上是 trunk 目录在 branch 创建时的 copy ，而创建以后， branch 与 trunk 实际就是互不干扰的工作了， branch 上的修改不会影响到 trunk ，反之亦然。


如何合并分支？

事实上，我们并没有解决本文开头所提出的问题，即， trunk 有了修改之后，并不会自动提交到 branch 中（不知道有没有其他的版本管理工具可以做到），这一切都需要手动来实现，而这个过程在 SVN 中称为“合并 (merge) ”。

SVN 合并与原始的“ Ctrl+C/Ctrl+V ”相比，有以下几点好处（假设是将 trunk 合并到 branch 中）：

1 、 trunk 中新增的文件可以自动合并到 branch 中

2 、提示 trunk 与 branch 中的同名文件的冲突内容，便于用于编辑冲突


合并操作步骤

在 TortoiseSVN 中提供便捷的合并功能。在待合并的工作目录上（如： branch ），使用TortoiseSVN → Merge命令，在“ From:(start URL and revision of the range to merge) ”中选择希望合并的目录 ( 如： trunk) ，并指定希望合并的开始 revision 编号，在“ To:(end URL and revision of the range to merge) ”中选择结束 revision 编号。然后点击“ merge ”完成合并操作，剩下的工作就是编辑冲突了，当然运气好的话是不需要这个过程滴。

值得注意的是，“ From: ”和“ To: ”中的 URL 通常是相同的，切记不要与创建分支时的含义混淆。

与合并相关的操作可查看TortoiseSVN的帮助文档中的“ Merging ”一节
