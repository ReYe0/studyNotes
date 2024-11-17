# springboot work condition

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
```shell
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
```
### 解决二：项目需要连接数据库
配置yml文件，eg：
```shell
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
```xml
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
```shell
@RunWith(SpringRunner.class)
@SpringBootTest(classes = '启动类'.class)
```

### mybatis debug log startup
```shell
#在properties 文件中添加
logging.level.com.example.datatransport.dao=debug
```

### @Autowired 和 @Resources 的区别
```shell
@Autowired
来源：@Autowired 是Spring框架提供的注解。
工作方式：默认情况下，@Autowired 按照类型（byType）来装配。如果存在多个相同类型的bean，那么Spring会抛出异常，除非其中一个被标记为首选（使用@Primary注解）或通过@Qualifier指定具体的bean名称。
使用场景：适用于需要根据类型匹配bean的情况，当有多个相同类型的bean时，可以结合@Qualifier使用以精确指定要装配的bean。
@Resource
来源：@Resource 是Java标准的一部分，由JSR-250规范定义，因此它不仅可以在Spring环境中使用，也可以在其他支持该规范的容器中使用。
工作方式：默认情况下，@Resource 按照名称（byName）来装配。如果指定了名称，则会按照该名称查找bean；如果没有指定名称，那么会使用字段名作为默认名称来查找bean。如果找不到相应名称的bean，那么会退回到按类型装配。
使用场景：适用于需要根据名称匹配bean的情况，或者希望代码更加独立于特定框架（如Spring），因为它是一个Java标准注解。
总结
如果你的项目严格遵循Spring框架，且主要关注类型安全，那么@Autowired可能是一个更好的选择。
如果你需要跨不同Java容器的兼容性，或者更倾向于基于名称的装配，那么@Resource可能更适合你。
在实际开发中，选择哪个注解取决于项目的具体需求和个人偏好。不过，通常推荐在一个项目中保持一致性，避免混合使用不同的注解风格，以减少潜在的混淆和维护成本。
```