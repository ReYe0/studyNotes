﻿# work condition

标签（空格分隔）： Windows

---
## windows 端口占用
```sh
# 查找端口号，监听的程序id
netstat -aon|findstr "9011"
# 通过程序id，找到程序
tasklist|findstr "24548"
# 通过程序id，杀死这个程序
taskkill -pid 24548 -f
```

## windows 启动 jar包
```shell
@echo off
setlocal

REM 改变当前工作目录到 JAR 文件所在目录, /d 参数允许同时更改驱动器和目录
cd /d D:\transferData\

REM 指定Java应用程序和参数
set "JAVA_PATH=D:\transferData\jdk1.8.0_351\jre\bin\java"
set "JAR_FILE=datatransport-0.0.1-SNAPSHOT.jar"

REM 启动Java应用程序
"%JAVA_PATH%" -jar "%JAR_FILE%" --server.port=10086

PAUSE
```
## windows 停止 jar包
1.根据端口号停止服务
```shell
@echo off
REM 确保在批处理脚本中对环境变量所做的更改只在该脚本内部有效，不会影响到批处理脚本外部的环境变量
setlocal

REM 设置要查找和停止的端口号
set PORT=10086

REM 查找并终止监听指定端口的Java进程
for /f "tokens=5" %%i in ('netstat -ano ^| findstr :%PORT%') do (
    taskkill /F /PID %%i
)

echo Attempted to stop Java process listening on port %PORT%.

endlocal
PAUSE
```
2.停止脚本，去除重复的pid
```shell
@echo off
setlocal

REM 设置要查找和停止的端口号
set PORT=10086

REM 初始化变量用于存储唯一PID
set UNIQUE_PIDS=

REM 查找并终止监听指定端口的Java进程，同时去重
for /f "tokens=5" %%i in ('netstat -ano ^| findstr :%PORT%') do (
    set NEW_PID=%%i
    set found=
    for /f "tokens=*" %%j in ('set pid_%%i 2^>nul') do set found=true
    if not defined found (
        echo Process ID listening on port %PORT% is %%i
        taskkill /F /PID %%i
        if errorlevel 1 (
            echo Failed to stop the process with PID %%i.
        ) else (
            echo Successfully stopped the Java process with PID %%i.
            set pid_%%i=1
        )
    )
)

if "%UNIQUE_PIDS%"=="" (
    echo No process found listening on port %PORT%.
) else (
    echo All matching processes have been processed.
)

endlocal
PAUSE
```
3.根据文件位置和名称 停止
```shell
@echo off
setlocal

REM 设置JDK路径和JAR文件路径
set "jdkPath=D:\transferData\jdk1.8.0_351"
set "jarFileName=datatransport-0.0.1-SNAPSHOT.jar"

REM 保存当前的JAVA_HOME和PATH
set "original_JAVA_HOME=%JAVA_HOME%"
set "original_PATH=%PATH%"

REM 设置新的JAVA_HOME环境变量
set "JAVA_HOME=%jdkPath%"

REM 添加JDK的bin目录到PATH环境变量
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM 打印当前设置的JAVA_HOME和PATH
echo JAVA_HOME is set to: %JAVA_HOME%
echo PATH is set to: %PATH%

REM 检查jps是否存在
where jps >nul 2>&1
if errorlevel 1 (
    echo Error: jps not found in PATH.
    goto end
)

REM 获取正在运行的Java进程列表，并查找包含指定JAR文件名的进程
echo Searching for Java processes...
for /f "tokens=1,2 delims= " %%p in ('jps -l') do (
    if "%%q"=="%jarFileName%" (
        echo Found process with PID %%p running %jarFileName%
        echo Killing process with PID %%p
        taskkill /PID %%p /F
        if errorlevel 1 (
            echo Failed to kill process with PID %%p
        ) else (
            echo Successfully killed process with PID %%p
        )
    ) else (
        REM 检查命令行参数是否包含JAR文件路径
        for /f "tokens=*" %%a in ('wmic process where processid^=%%p get commandline ^| findstr /i "%jarFileName%"') do (
            if not "%%a"=="" (
                echo Found process with PID %%p running %jarFileName%
                echo Killing process with PID %%p
                taskkill /PID %%p /F
                if errorlevel 1 (
                    echo Failed to kill process with PID %%p
                ) else (
                    echo Successfully killed process with PID %%p
                )
            )
        )
    )
)

REM 如果没有找到任何进程
if '%errorlevel%'=='0' (
    echo No matching Java processes found.
)

:end
REM 恢复原来的JAVA_HOME和PATH
set "JAVA_HOME=%original_JAVA_HOME%"
set "PATH=%original_PATH%"

echo Restored original JAVA_HOME and PATH.

endlocal
pause
```
