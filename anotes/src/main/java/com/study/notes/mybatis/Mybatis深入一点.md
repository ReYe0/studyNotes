# Mybatis深入一点

标签（空格分隔）： mybatis

---

## 日志
```yaml
logging.level.路径=debug，eg：`logging.level.com.zzqa.amc.dao=debug`
```


## @Mapper注解：
作用：在接口类上添加了@Mapper，在编译之后会生成相应的接口实现类
添加位置：接口类上面
```java
@Mapper
public interface UserDAO {
//代码
}
```

如果想要每个接口都要变成实现类，那么需要在每个接口类上加上@Mapper注解，比较麻烦，解决这个问题用@MapperScan

## @MapperScan
作用：指定要变成实现类的接口所在的包，然后包下面的所有接口在编译之后都会生成相应的实现类
添加位置：是在Springboot启动类上面添加，
```java
@SpringBootApplication
@MapperScan("com.位置.dao")
public class SpringbootMybatisDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisDemoApplication.class, args);
    }
}
```

添加@MapperScan(“com.位置.dao”)注解以后，com.乌贼骨.dao包下面的接口类，在编译之后都会生成相应的实现类

## 使用@MapperScan注解多个包
（实际用的时候根据自己的包路径进行修改）
```java
@SpringBootApplication
@MapperScan({"com.h1.demo","com.h2.user"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

## 如果dao接口类没有在Spring Boot主程序可以扫描的包或者子包下面，可以使用如下方式进行配置：
（没验证过，不确定能否使用，或许需要根据自己定义的包名进行修改路径）
```java
@Configuration//将想要的组件添加到容器中
@MapperScan({"com.h1.*.mapper","org.h2.*.mapper"})
public class App {
    // TODO 想要的操作
}
```

## SqlSessionTemplate
SqlSessionTemplate是MyBatis-Spring的核心。这个类负责管理MyBatis的SqlSession,调用MyBatis的SQL方法。SqlSessionTemplate是线程安全的，可以被多个DAO所共享使用。

当调用SQL方法时，包含从映射器getMapper()方法返回的方法，SqlSessionTemplate将会保证使用的SqlSession是和当前Spring的事务相关的。此外，它管理session的生命周期，包含必要的关闭，提交或回滚操作。

qlSessionTemplate实现了SqlSession，这就是说要对MyBatis的SqlSession进行简易替换。

SqlSessionTemplate通常是被用来替代默认的MyBatis实现的DefaultSqlSession，因为它不能参与到Spring的事务中也不能被注入，因为它是线程不安全的。相同应用程序中两个类之间的转换可能会引起数据一致性的问题。
SqlSessionTemplate对象可以使用SqlSessionFactory作为构造方法的参数来创建。
 

