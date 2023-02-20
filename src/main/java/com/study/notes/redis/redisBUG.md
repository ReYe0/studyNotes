# redisBUG

标签（空格分隔）： redis

---

## Windows环境

1、Could not connect to Redis at 127.0.0.1:6379: 由于目标计算机积极拒绝，无法连接。
``


2、报错如下
```shell
D:\Redis-x64-5.0.14.1>redis-server.exe redis.windows.conf
[7948] 29 Jan 10:06:50.399 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
[7948] 29 Jan 10:06:50.399 # Redis version=5.0.14.1, bits=64, commit=ec77f72d, modified=0, pid=7948, just started
[7948] 29 Jan 10:06:50.400 # Configuration loaded
[7948] 29 Jan 10:06:50.402 # Could not create server TCP listening socket 127.0.0.1:6379: bind: 操作成功完成。
```
解决：
```shell
redis-cli.exe      # 进入客户端
shutdown  # 关闭该redis服务
exit       # 退出
redis-server.exe redis.windows.conf  # 重新启动reids 服务
```
