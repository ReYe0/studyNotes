# Windows 端口占用

标签（空格分隔）： Windows

---
```sh
# 查找端口号，监听的程序id
netstat -aon|findstr "9011"
# 通过程序id，找到程序
tasklist|findstr "24548"
# 通过程序id，杀死这个程序
taskkill -pid 24548 -f
```





