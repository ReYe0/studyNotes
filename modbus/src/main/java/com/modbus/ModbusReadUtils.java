package com.modbus;

import com.serotonin.modbus4j.*;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModbusReadUtils {

    /**
     * 工厂。
     */
    static ModbusFactory modbusFactory;

    static {
        if (modbusFactory == null) {
            modbusFactory = new ModbusFactory();
        }
    }

    /**
     * 获取master
     *
     * @return
     * @throws ModbusInitException
     */
    public static ModbusMaster getMaster(String host, Integer port) throws ModbusInitException {
        IpParameters params = new IpParameters();
        params.setHost(host);
        params.setPort(port);
        //
        // modbusFactory.createRtuMaster(wapper); //RTU 协议
        // modbusFactory.createUdpMaster(params);//UDP 协议
        // modbusFactory.createAsciiMaster(wrapper);//ASCII 协议
        // TCP 协议
        ModbusMaster master = modbusFactory.createTcpMaster(params, false);
        master.init();

        return master;
    }

    /**
     * 读取[01 Coil Status 0x]类型 开关数据
     *
     * @param slaveId slaveId
     * @param offset  位置
     * @return 读取值
     * @throws ModbusTransportException 异常
     * @throws ErrorResponseException   异常
     * @throws ModbusInitException      异常
     */
    public static Boolean readCoilStatus(String host, Integer port, int slaveId, int offset)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 01 Coil Status
        BaseLocator<Boolean> loc = BaseLocator.coilStatus(slaveId, offset);
        Boolean value = getMaster(host, port).getValue(loc);
        return value;
    }

    /**
     * 读取[02 Input Status 1x]类型 开关数据
     *
     * @param slaveId
     * @param offset
     * @return
     * @throws ModbusTransportException
     * @throws ErrorResponseException
     * @throws ModbusInitException
     */
    public static Boolean readInputStatus(String host, Integer port, int slaveId, int offset)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 02 Input Status
        BaseLocator<Boolean> loc = BaseLocator.inputStatus(slaveId, offset);
        Boolean value = getMaster(host, port).getValue(loc);
        return value;
    }

    /**
     * 读取[03 Holding Register类型 2x]模拟量数据
     *
     * @param slaveId  slave Id
     * @param offset   位置
     * @param dataType 数据类型,来自com.serotonin.modbus4j.code.DataType
     * @return
     * @throws ModbusTransportException 异常
     * @throws ErrorResponseException   异常
     * @throws ModbusInitException      异常
     */
    public static Number readHoldingRegister(String host, Integer port, int slaveId, int offset, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 03 Holding Register类型数据读取
        BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, offset, dataType);
        Number value = getMaster(host, port).getValue(loc);
        return value;
    }

    /**
     * 读取[04 Input Registers 3x]类型 模拟量数据
     *
     * @param slaveId  slaveId
     * @param offset   位置
     * @param dataType 数据类型,来自com.serotonin.modbus4j.code.DataType
     * @return 返回结果
     * @throws ModbusTransportException 异常
     * @throws ErrorResponseException   异常
     * @throws ModbusInitException      异常
     */
    public static Number readInputRegisters(String host, Integer port, int slaveId, int offset, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 04 Input Registers类型数据读取
        BaseLocator<Number> loc = BaseLocator.inputRegister(slaveId, offset, dataType);
        return getMaster(host, port).getValue(loc);
    }

    /**
     * 批量读取使用方法
     *
     * @throws ModbusTransportException
     * @throws ErrorResponseException
     * @throws ModbusInitException
     */
    public static void batchRead(String host, Integer port) throws ModbusTransportException, ErrorResponseException, ModbusInitException {

        BatchRead<Integer> batch = new BatchRead<Integer>();

        batch.addLocator(0, BaseLocator.holdingRegister(1, 1, DataType.FOUR_BYTE_FLOAT));
        batch.addLocator(1, BaseLocator.inputStatus(1, 0));

        ModbusMaster master = getMaster(host, port);

        batch.setContiguousRequests(false);
        BatchResults<Integer> results = master.send(batch);
        System.out.println(results.getValue(0));
        System.out.println(results.getValue(1));
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
//            ModbusSlaveSet tcpSlave = modbusFactory.createTcpSlave(false);
            // 创建 Modbus TCP 参数
//            IpParameters params = new IpParameters();
//            params.setHost("0.0.0.0");
//            params.setPort(10086);
//            ModbusMaster tcpListener = modbusFactory.createTcpListener(params);
//            System.out.println(tcpListener);

//            ModbusMaster master = getMaster("192.168.0.80", 10123);
//            System.out.println(master);
            Number number = readInputRegisters("192.168.0.80", 10123, 11, 0, DataType.FOUR_BYTE_INT_SIGNED);
            System.out.println(number);
//            // 01测试
//            Boolean v011 = readCoilStatus("localhost", 0, 1, 0);
//            Boolean v012 = readCoilStatus("localhost", 0, 1, 1);
//            Boolean v013 = readCoilStatus("localhost", 0, 1, 6);
//            System.out.println("v011:" + v011);
//            System.out.println("v012:" + v012);
//            System.out.println("v013:" + v013);
//            // 02测试
//            Boolean v021 = readInputStatus("localhost", 0, 1, 0);
//            Boolean v022 = readInputStatus("localhost", 0, 1, 1);
//            Boolean v023 = readInputStatus("localhost", 0, 1, 2);
//            System.out.println("v021:" + v021);
//            System.out.println("v022:" + v022);
//            System.out.println("v023:" + v023);

            // 03测试f
//            for (int i = 0; i < 103; i++) {
//                Number v031 = readHoldingRegister("127.0.0.1", 505, 1, i, DataType.FOUR_BYTE_FLOAT);// 注意,float
//            }
//            Number v031 = readHoldingRegister("127.0.0.1", 505, 1, 0, DataType.FOUR_BYTE_FLOAT);// 注意,float
//            Number v032 = readHoldingRegister("localhost", 0, 1, 3, DataType.FOUR_BYTE_FLOAT);// 同上
            //System.out.println("v031:" + v031);
//            System.out.println("v032:" + v032);

            // 04测试
//            Number v041 = readInputRegisters("localhost", 0, 1, 1, DataType.FOUR_BYTE_FLOAT);//
//            Number v042 = readInputRegisters("localhost", 0, 1, 3, DataType.FOUR_BYTE_FLOAT);//
//            System.out.println("v041:" + v041);
//            System.out.println("v042:" + v042);
            // 批量读取
//            batchRead("localhost", 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}