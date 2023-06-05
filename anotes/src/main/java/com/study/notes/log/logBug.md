# logBug

标签（空格分隔）： log

---

## Failed to load class "org.slf4j.impl.StaticLoggerBinder"
加入如下依赖：
```xml
<dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.5</version>
</dependency>
```

## WARN No appenders could be found for logger
添加log4j.properties文件
内容：
```properties
log4j.rootLogger=DEBUG, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

## Class path contains multiple SLF4J bindings.
原因：根据上面的错误提示，存在多个SLF4J bindings绑定，即存在多个slf4j的实现类，按上图所示这两个实现分别是logback-classic-1.2.6和slf4j-log4j12-1.6.1，我们需要的是logback而不是log4j

解决：因此，我们去掉log4j的依赖就行；查找slf4j-log4j12-1.6.1并将其删除