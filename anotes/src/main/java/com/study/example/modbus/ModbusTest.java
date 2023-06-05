package com.study.example.modbus;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.modbus4j.msg.WriteRegistersResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.time.LocalDateTime;

public class ModbusTest {
    public static void main(String[] args) {
            try {
//                // 设置主机TCP参数
//                TcpParameters tcpParameters = new TcpParameters();
//                // 设置TCP的ip地址
//                InetAddress adress = InetAddress.getByName("192.168.0.7");
//                // TCP参数设置ip地址
//                // tcpParameters.setHost(InetAddress.getLocalHost());
//                tcpParameters.setHost(adress);
//                // TCP设置长连接
//                tcpParameters.setKeepAlive(true);
//                // TCP设置端口，这里设置是默认端口502
//                tcpParameters.setPort(Modbus.TCP_PORT);
//                // 创建一个主机
//                ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
//                Modbus.setAutoIncrementTransactionId(true);
                LocalDateTime currentDateTime = LocalDateTime.now();
                int year = currentDateTime.getYear();
                int month = currentDateTime.getMonthValue();
                int day = currentDateTime.getDayOfMonth();
                int hour = currentDateTime.getHour();
                int minute = currentDateTime.getMinute();
                int second = currentDateTime.getSecond();
                System.out.println("当前日期时间：" + currentDateTime);
                System.out.println("年份：" + year);
                System.out.println("月份：" + month);
                System.out.println("日期：" + day);
                System.out.println("小时：" + hour);
                System.out.println("分钟：" + minute);
                System.out.println("秒：" + second);

                ModbusMaster master = getMaster("192.168.0.7");
                int slaveId = 1;//从机地址
                int offset = 0;//寄存器读取开始地址
                int quantity = 23;//读取的寄存器数量
                try {
                    if (!master.isConnected()) {
                        master.connect();// 开启连接,麻烦
                    }
                    // 读取对应从机的数据，readInputRegisters读取的写寄存器，功能码04
                    int i = 0;
                    while (true){
                        int[] registerValues = master.readInputRegisters(slaveId, offset, quantity);
                        // 控制台输出
                        for (int value : registerValues) {
                            i++;
                            System.out.println("Address: " + offset++ + ", Value: " + Integer.toHexString(value));
                            if (i > 27 ) break;
                        }
                        break;
                    }
//                    byte[] byteArray = {0x12, 0x34, 0x56, 0x78}; // 要转换的byte数组
//                    int[] intArray = new int[byteArray.length / 4]; // 转换后的int数组
//
//                    for (int i = 0; i < intArray.length; i++) {
//                        intArray[i] = ((byteArray[i*4] & 0xff) << 24) |
//                                ((byteArray[i*4 + 1] & 0xff) << 16) |
//                                ((byteArray[i*4 + 2] & 0xff) << 8) |
//                                (byteArray[i*4 + 3] & 0xff);
//                    }
//                    byte[] byteArray = {0x01, 0x02};
//                    int[] ints = byteArrayToIntArray(byteArray);
//                    master.readWriteMultipleRegisters(1,1,3)
//                    master.readWriteMultipleRegisters()
//                    master.writeMultipleRegisters(1,1,intArray);

//                    master.writeSingleRegister(1,1,1);
//                    int[] registerValue2 = master.readHoldingRegisters(slaveId, offset, quantity);
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
    public static int[] byteArrayToIntArray(byte[] bytes) {
        IntBuffer intBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] intArray = new int[intBuffer.remaining()];
        intBuffer.get(intArray);
        return intArray;
    }

    /**
     * 批量写数据到保持寄存器
     * @param ip 从站IP
     * @param port modbus端口
     * @param slaveId 从站ID
     * @param start 起始地址偏移量
     * @param values 待写数据
     */
    public static void modbusWTCP(String ip, int port, int slaveId, int start, short[] values) {
        ModbusFactory modbusFactory = new ModbusFactory();
        // 设备ModbusTCP的Ip与端口，如果不设定端口则默认为502
        IpParameters params = new IpParameters();
        params.setHost(ip);
        // 设置端口，默认502
        if (502 != port) {
            params.setPort(port);
        }
        com.serotonin.modbus4j.ModbusMaster tcpMaster = null;
        // 参数1：IP和端口信息 参数2：保持连接激活
        tcpMaster = modbusFactory.createTcpMaster(params, true);
        try {
            tcpMaster.init();
            System.out.println("=======初始化成功========");
        } catch (ModbusInitException e) {
            System.out.println("初始化异常");
        }
        try {
            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);
            WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);
            if (response.isException()){
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            }else{
                System.out.println("Success");
            }
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }
    public static ModbusMaster getMaster(String ip){
        // 设置主机TCP参数
        TcpParameters tcpParameters = new TcpParameters();
        // 设置TCP的ip地址
        InetAddress adress = null;
        try {
            adress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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
        return master;
    }
}
