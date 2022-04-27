# TCC分布事务

​	tcc由try，confirm，cancel三个阶段组成

- try阶段：尝试执行，完成所有业务检查（一致性），预留必须业务资源（准隔离性）
- Confirm阶段：如果所有分支的Try都成功了，则走到Confiem阶段。Confirm真正执行业务，不做任何业务检查，只是用Try阶段预留的业务资源
- Cancel阶段：如果所有分支的try有一个失败了，则走到Cancel阶段。Cancel释放Try阶段预留的业务资源
