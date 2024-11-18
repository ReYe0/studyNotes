# netty study

标签（空格分隔）： netty

---

## 基本使用 


在使用springboot的情况下，引入依赖

```shell
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-all</artifactId>
</dependency>
```
 
1.实现 discard 协议。
    如 example.netty.discard 中的代码实现。启动它，使用 telnet localhost 8080 连接发送消息。您就可以在服务器看到打印内容。
2.实现 echo 协议。
    如 example.netty.echo 中的代码实现（在discard基础上修改）。然后 telnet localhost 8080 连接成功后，发送什么给服务端您都可以在客户端看见
3.实现 time 协议。
    如 example.netty.time 中的代码实现（在echo基础上修改）。然后 telnet localhost 8080 连接成功后，控制台有打印，连接立即会断开。


