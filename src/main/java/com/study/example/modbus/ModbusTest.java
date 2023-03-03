package com.study.example.modbus;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
                ModbusMaster master = getMaster("192.168.0.7");
                int slaveId = 1;//从机地址
                int offset = 0;//寄存器读取开始地址
                int quantity = 23;//读取的寄存器数量
                try {
                    if (!master.isConnected()) {
                        master.connect();// 开启连接,麻烦
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
