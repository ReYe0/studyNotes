# Linux work condition

标签（空格分隔）： Linux

---

# 设置tomcat 开机自启

```shell
crontab -e
# 在其中添加 下面这行代码（startup.sh 自行更好，只有一行时 & 不要） 保存
reboot sudo -u root /home/debian/tomcat9/bin/startup.sh start &
```