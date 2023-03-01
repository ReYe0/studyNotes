# modbus

标签（空格分隔）： modbus

## modbus协议

自行百度，反正就是一个报文，每一段代表不同的意思。主要还是看厂商那边指定的规则是什么。


## 引入依赖之jlibmodbus

```pom
<dependency>
    <groupId>com.intelligt.modbus</groupId>
    <artifactId>jlibmodbus</artifactId>
    <version>1.2.9.7</version>
</dependency>
```

### 测试连接

拥有硬件基础后，可以进行测试。

```java
public static void main(String[] args) {
            try {
                // 设置主机TCP参数
                TcpParameters tcpParameters = new TcpParameters();
                // 设置TCP的ip地址
                InetAddress adress = InetAddress.getByName("192.168.0.7");
                // TCP参数设置ip地址
                // tcpParameters.setHost(InetAddress.getLocalHost());
                tcpParameters.setHost(adress);
                // TCP设置长连接
                tcpParameters.setKeepAlive(true);
                // TCP设置端口，这里设置是默认端口502
                tcpParameters.setPort(Modbus.TCP_PORT);
                // 创建一个主机
                ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
                Modbus.setAutoIncrementTransactionId(true);
                int slaveId = 1;//从机地址
                int offset = 0;//寄存器读取开始地址
                int quantity = 23;//读取的寄存器数量
                try {
                    if (!master.isConnected()) {
                        master.connect();// 开启连接
                    }
                    // 读取对应从机的数据，readInputRegisters读取的写寄存器，功能码04
                    int[] registerValues = master.readInputRegisters(slaveId, offset, quantity);
//                    int[] registerValue2 = master.readHoldingRegisters(slaveId, offset, quantity);
                    // 控制台输出
                    for (int value : registerValues) {
                        System.out.println("Address: " + offset++ + ", Value: " + value);
                    }
                } catch (ModbusProtocolException e) {
                    e.printStackTrace();
                } catch (ModbusNumberException e) {
                    e.printStackTrace();
                } catch (ModbusIOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        master.disconnect();
                    } catch (ModbusIOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
```



