# mysqlBug

标签（空格分隔）： mysql

---

## Packet for query is too large错误
```txt
在mysql安装目录中找到my.ini配置文件
在最后添加 max_allowed_packet=10485760
```
数值可以 MB为单位，16M


##  索引失效
eg：假设age是整数类型，但是却使用字符串类型
```shell
SELECT * FROM user WHERE age = '20';
```
MySQL 需要在查询时转换 '20' 为整数类型，可能会导致索引无法使用。 某个电商平台就有这么一个类似的bug，导致下单超时崩盘30分钟。