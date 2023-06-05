# mysqlBug

标签（空格分隔）： mysql

---

## Packet for query is too large错误
```txt
在mysql安装目录中找到my.ini配置文件
在最后添加 max_allowed_packet=10485760
```
数值可以 MB为单位，16M


