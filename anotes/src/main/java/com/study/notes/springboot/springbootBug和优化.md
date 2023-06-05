# springbootBug和优化

标签（空格分隔）： springboot

---
## pringboot的yml文件没有小叶子
进入project structure  ——》

## Failed to configure a DataSource: ‘url‘ attribute is not specified and no embedded datasource
报错信息如下
```shell
***************************
APPLICATION FAILED TO START
***************************
 
Description:
 
Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
 
Reason: Failed to determine a suitable driver class
 
 
Action:
 
Consider the following:
    If you want an embedded database (H2, HSQL or Derby), please put it on the classpath.
    If you have database settings to be loaded from a particular profile you may need to activate it (no profiles are currently active).
```
### 解决一：项目不需要连数据库
启动类加上如下注解
```java
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
```
### 解决二：项目需要连接数据库
配置yml文件，eg：
```ymal
#在application.properties/或者application.yml文件中没有添加数据库配置信息.
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
```

### 解决三：mysql版本问题
mysql8以下的去除cj，更高版本的加上cj，eg：`driver-class-name: com.mysql.cj.jdbc.Driver`


### 解决四：项目没有加载到yml或者properties文件
特别是自己的pom打包是jar的项目，请查看自己的pom.xml文件中的packaging
```pom
<packaging>jar</packaging>
```
如果pom中指定使用jar，系统不会自动读取到yml或者properties文件的，需要我们手动配置pom.xml。
```pom
<!--build放在</dependencies>标签的后面，主要加入的是resources标签 -->
<!--resources标签可以告诉系统启动的时候能够读取到这些后缀的文件 -->
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.yml</include>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.yml</include>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>lib</directory>
                <includes>
                    <include>**/*.jar</include>
                </includes>
            </resource>
        </resources>
    </build>
```

### 解决五：项目使用了springcloud+nacos系列
需要配置启用了那个配置文件


### test类dao层注册失败
测试类加上如下代码
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = '启动类'.class)
```