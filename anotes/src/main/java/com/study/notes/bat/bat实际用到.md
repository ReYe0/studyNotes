# bat实际用到

标签（空格分隔）： bat

---
## 1、脚本启动停止jar包，并生成日志文件
start.bat
```shell
@echo off
%1 mshta vbscript:CreateObject("WScript.Shell").Run("%~s0 ::",0,FALSE)(window.close)&&exit
java -jar 包名.jar >StartupLog.log  2>&1 &
exit
```
其中，StartupLog.log 是日志文件

stop.bat
```shell
@echo off
set port=程序端口号
for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do taskkill /f /pid %%m
```

原文链接：https://blog.csdn.net/weixin_47148475/article/details/126747188

找了个教程：https://blog.csdn.net/albertsh/article/details/52777987?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522168007680116800192258823%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=168007680116800192258823&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-8-52777987-null-null.blog_rank_default&utm_term=.bat%E6%89%B9%E5%A4%84%E7%90%86&spm=1018.2226.3001.4450


## 2、上面的变种

start2.bat
```shell
set JAVA_HOME=./jdk
 
set CLASSPATH=.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;
 
set Path=%JAVA_HOME%\bin;
 
java -jar DMS_DEMO-1.0-SNAPSHOT.jar
```

stop2.bat
```shell
@echo off
# 项目启动后，会占用的端口
set port=9021
for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do (
    echo kill the process %%m who use the port %port%
    # 根据 进程id pid 信息，杀掉进程
    taskkill /f /pid %%m
)

```

