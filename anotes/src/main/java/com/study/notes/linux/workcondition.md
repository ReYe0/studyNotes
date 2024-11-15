# Linux work condition

标签（空格分隔）： Linux

---

# 设置tomcat 开机自启

```shell
crontab -e
# 在其中添加 下面这行代码（startup.sh 自行更好，只有一行时 & 不要） 保存
reboot sudo -u root /home/debian/tomcat9/bin/startup.sh start &
```

# 设置 jar 开机自启
编写脚本文件
start.sh
```shell
#!/bin/bash
nohup java -jar XXX.jar
```
然后 输入：crontab -e  添加如下
```shell
@reboot java -jar  xxx/xx.jar
```