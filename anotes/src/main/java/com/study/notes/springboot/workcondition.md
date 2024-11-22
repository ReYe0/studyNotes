# springboot work condition

标签（空格分隔）： springboot

---

## pringboot的yml文件没有小叶子

进入project structure ——》

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

### logback 的使用

1.导入依赖：

```shell
 <!-- Logback Core -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
            <version>1.2.11</version> <!-- 选择合适的版本 -->
        </dependency>

        <!-- Logback Classic (用于替代 SLF4J 的实现) -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.11</version>
        </dependency>
        <dependency>
            <groupId>org.rxtx</groupId>
            <artifactId>rxtx</artifactId>
            <version>2.1.7</version>
        </dependency>
```

2.配置xml文件:logback.xml 放置在 resources 下

```xml

<configuration>


    <!-- 控制台日志输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %highlight(%-5level) %white(-) %-15(%yellow([%10.20thread]))
                %-55(%cyan(%.32logger{30}:%L)) %highlight(- %msg%n)
            </pattern>
        </encoder>
    </appender>

    <!-- 普通应用日志文件输出 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 按日期滚动日志文件 -->
            <fileNamePattern>logs/app.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory> <!-- 保留30天的日志 -->
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %highlight(%-5level) %white(-) %-15(%yellow([%10.20thread]))
                %-55(%cyan(%.32logger{30}:%L)) %highlight(- %msg%n)
            </pattern>
        </encoder>
    </appender>


    <!-- 定义全局的日志级别 -->
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </root>

</configuration>

```

3.调用
```shell
// 注意依赖引入错误会没有效果
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
private static final Logger log = LoggerFactory.getLogger(XXX.class);
```

### logback-spring 这个只需要 引入lombok的依赖，@Slf4j的注解就能使用
1.引入 lombok 依赖
2.创建logback-spring.xml文件，输入一下内容
```xml
<!-- 级别从高到低 OFF 、 FATAL 、 ERROR 、 WARN 、 INFO 、 DEBUG 、 TRACE 、 ALL -->
<!-- 日志输出规则 根据当前ROOT 级别，日志输出时，级别高于root默认的级别时 会输出 -->
<!-- 以下 每个配置的 filter 是过滤掉输出文件里面，会出现高级别文件，依然出现低级别的日志信息，通过filter 过滤只记录本级别的日志 -->
<!-- scan 当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。 -->
<!-- scanPeriod 设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug 当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <!-- 动态日志级别 -->
    <!--  <jmxConfigurator/>-->
    <!-- 定义日志文件 输出位置 -->

    <springProperty scope="context" name="logPath" source="logging.file.path" defaultValue="./log"/>
    <property name="log.path" value="${logPath}/log/"/>
    <!-- 日志最大的历史 30天 -->
    <property name="maxHistory" value="10"/>
    <!-- 设置日志输出格式 -->
    <property name="CONSOLE_LOG_PATTERN"
              value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{traceId}] %highlight(${LOG_LEVEL_PATTERN:-%5p}) %magenta(${PID:-})  [%yellow(%thread)] [%cyan(%logger{50} - %method:%line)] - %highlight(%msg%n)"/>
    <property name="LOG_PATTERN"
              value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{traceId}] ${LOG_LEVEL_PATTERN:-%5p} ${PID:-}  [%thread] [%logger{50} - %method:%line] - %msg%n"/>

    <!-- ConsoleAppender 控制台输出日志 -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>


    <!-- INFO级别日志 appender -->
    <appender name="INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--记录的日志文件的路径及文件名-->
        <file>${log.path}/info.log</file>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <!--日志记录器的滚动策略，按日期，按大小记录-->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/%d{yyyy-MM,aux}/info.%d{yyyy-MM-dd}.%i.log.zip</fileNamePattern>
            <maxFileSize>100MB</maxFileSize>
            <totalSizeCap>1GB</totalSizeCap>
            <maxHistory>10</maxHistory>
            <cleanHistoryOnStart>true</cleanHistoryOnStart>
        </rollingPolicy>
        <encoder>
            <pattern>${LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!--
      <logger>用来设置某一个包或者具体的某一个类的日志打印级别、
      以及指定<appender>。<logger>仅有一个name属性，
      一个可选的level和一个可选的addtivity属性。
      name:用来指定受此logger约束的某一个包或者具体的某一个类。
      level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
            还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。
            如果未设置此属性，那么当前logger将会继承上级的级别。
      addtivity:是否向上级logger传递打印信息。默认是true。
      <logger name="org.springframework.web" level="info"/>
      <logger name="org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor" level="INFO"/>
    -->

    <!--
        使用mybatis的时候，sql语句是debug下才会打印，而这里我们只配置了info，所以想要查看sql语句的话，有以下两种操作：
        第一种把<root level="info">改成<root level="DEBUG">这样就会打印sql，不过这样日志那边会出现很多其他消息
        第二种就是单独给dao下目录配置debug模式，代码如下，这样配置sql语句会打印，其他还是正常info级别：
        【logging.level.org.mybatis=debug logging.level.dao=debug】
     -->

    <!--
        root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        不能设置为INHERITED或者同义词NULL。默认是DEBUG
        可以包含零个或多个元素，标识这个appender将会添加到这个logger。
    -->
    <!-- 4  最终的策略：
                     基本策略(root级) + 根据profile在启动时, logger标签中定制化package日志级别(优先级高于上面的root级)-->
    <!-- root级别 DEBUG -->
    <root>
        <!-- 打印debug级别日志及以上级别日志 -->
        <level value="Info"/>
        <!-- 控制台输出 -->
        <appender-ref ref="console"/>
        <!-- 文件输出 -->
        <appender-ref ref="INFO"/>
        <!--    <appender-ref ref="DEBUG"/>-->
        <!--    <appender-ref ref="TRACE"/>-->
    </root>
    <!--  &lt;!&ndash;不同业务打印到指定文件&ndash;&gt;-->
    <!--  <logger name="byte" additivity="false" level="INFO">-->
    <!--    <appender-ref ref="DELETE_INFO"/>-->
    <!--  </logger>-->
    <!--  &lt;!&ndash;  &lt;!&ndash;不同业务打印到指定文件&ndash;&gt;&ndash;&gt;-->
    <!--  &lt;!&ndash; 生产环境, 指定某包日志为warn级 &ndash;&gt;-->
    <!--  <logger name="org.springframework.jdbc.core.JdbcTemplate" level="info"/>-->
    <!-- 特定某个类打印info日志, 比如application启动成功后的提示语 -->
</configuration>
```

### springboot 启动后 立即执行
1.必须的spring托管，没有则添加@Component，然后 implements CommandLineRunner 接口 重写 run 方法既可

2.在 Spring Boot 应用程序中，ApplicationRunner 是一个接口，用于在应用程序启动完成后执行一些初始化任务。它提供了一个 run 方法，该方法在 SpringApplication.run 方法执行完毕后被调用。ApplicationRunner 接口的主要作用是在应用程序启动时执行一些自定义的逻辑。

上述两个的区别为：
ApplicationRunner：提供了 ApplicationArguments 对象，可以更方便地访问命令行参数。
CommandLineRunner：提供了 String[] 数组，包含所有的命令行参数。


## 注解 @ConditionalOnProperty

作用 @ConditionalOnProperty 注解的主要作用是：

* 根据配置属性的存在与否：如果配置属性存在，则加载相应的 Bean 或配置类。
* 根据配置属性的值：如果配置属性的值符合预期，则加载相应的 Bean 或配置类。

eg:@ConditionalOnProperty(name = "cs2290.version", havingValue = "1")