package com.study.example.modbus;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.*;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ModbusWriteUtils {

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
     * 获取tcpMaster
     *
     * @return
     * @throws ModbusInitException
     */
    public static ModbusMaster getMaster(String host, Integer port) {
        IpParameters params = new IpParameters();
        params.setHost(host);
        params.setPort(port);

        ModbusMaster tcpMaster = modbusFactory.createTcpMaster(params, false);
        try {
            tcpMaster.init();
        } catch (ModbusInitException e) {
            log.error("sdfsdfsdfsdfsdf");
        }

        return tcpMaster;
    }

    /**
     * 写 [01 Coil Status(0x)]写一个 function ID = 5
     *
     * @param slaveId     slave的ID
     * @param writeOffset 位置
     * @param writeValue  值
     * @return 是否写入成功
     * @throws ModbusTransportException
     * @throws ModbusInitException
     */
    public static boolean writeCoil(String host, Integer port, int slaveId, int writeOffset, boolean writeValue)
            throws ModbusTransportException, ModbusInitException {
        // 获取master
        ModbusMaster tcpMaster = getMaster(host, port);
        // 创建请求
        WriteCoilRequest request = new WriteCoilRequest(slaveId, writeOffset, writeValue);
        // 发送请求并获取响应对象
        WriteCoilResponse response = (WriteCoilResponse) tcpMaster.send(request);
        if (response.isException()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 写[01 Coil Status(0x)] 写多个 function ID = 15
     *
     * @param slaveId     slaveId
     * @param startOffset 开始位置
     * @param bdata       写入的数据
     * @return 是否写入成功
     * @throws ModbusTransportException
     * @throws ModbusInitException
     */
    public static boolean writeCoils(String host, Integer port, int slaveId, int startOffset, boolean[] bdata)
            throws ModbusTransportException, ModbusInitException {
        // 获取master
        ModbusMaster tcpMaster = getMaster(host, port);
        // 创建请求
        WriteCoilsRequest request = new WriteCoilsRequest(slaveId, startOffset, bdata);
        // 发送请求并获取响应对象
        WriteCoilsResponse response = (WriteCoilsResponse) tcpMaster.send(request);
        if (response.isException()) {
            return false;
        } else {
            return true;
        }

    }

    /***
     * 写[03 Holding Register(4x)] 写一个 function ID = 6
     *
     * @param slaveId
     * @param writeOffset
     * @param writeValue
     * @return
     * @throws ModbusTransportException
     * @throws ModbusInitException
     */
    public static boolean writeRegister(String host, Integer port, int slaveId, int writeOffset, short writeValue)
            throws ModbusTransportException, ModbusInitException {
        // 获取master
        ModbusMaster tcpMaster = getMaster(host, port);
        // 创建请求对象
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, writeOffset, writeValue);
        WriteRegisterResponse response = (WriteRegisterResponse) tcpMaster.send(request);
        if (response.isException()) {
            log.error("错误信息 {} {} {}", response.getExceptionMessage(), response.getWriteOffset(), response.getWriteValue());
            return false;
        } else {
            return true;
        }

    }

    /**
     * 写入[03 Holding Register(4x)]写多个 function ID=16
     *
     * @param slaveId     modbus的slaveID
     * @param startOffset 起始位置偏移量值
     * @param sdata       写入的数据
     * @return 返回是否写入成功
     * @throws ModbusTransportException
     * @throws ModbusInitException
     */
    public static boolean writeRegisters(String host, Integer port, int slaveId, int startOffset, short[] sdata)
            throws ModbusTransportException, ModbusInitException {
        // 获取master
        ModbusMaster tcpMaster = getMaster(host, port);
        log.info("host {}  port {} ", host, port);
        // 创建请求对象
        WriteRegistersRequest request = new WriteRegistersRequest(slaveId, startOffset, sdata);
        // 发送请求并获取响应对象
        ModbusResponse response = tcpMaster.send(request);
        if (response.isException()) {
            log.error("发送失败 {}", response.getExceptionMessage());
            return false;
        } else {
            return true;
        }
    }

    /**
     * 写入数字类型的模拟量（如:写入Float类型的模拟量、Double类型模拟量、整数类型Short、Integer、Long）
     *
     * @param slaveId
     * @param offset
     * @param value    写入值,Number的子类,例如写入Float浮点类型,Double双精度类型,以及整型short,int,long
     * @param dataType ,com.serotonin.modbus4j.code.DataType
     * @throws ModbusTransportException
     * @throws ErrorResponseException
     * @throws ModbusInitException
     */
    public static void writeHoldingRegister(String host, Integer port, int slaveId, int offset, Number value, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 获取master
        ModbusMaster tcpMaster = getMaster(host, port);
        // 类型
        log.info("Modbus 数据写入中ip {} port{} slaveId {} offset {} value {} dataType {}", host, port, slaveId, offset, value, dataType);
        BaseLocator<Number> locator = BaseLocator.holdingRegister(slaveId, offset, dataType);
//        log.info(JSONUtils.toJSONString(locator));
        tcpMaster.setValue(locator, value);
    }

    public static void main(String[] args) {
        try {
            //@formatter:off
            // 测试01
//			boolean t01 = writeCoil(1, 0, true);
//			System.out.println("T01:" + t01);

            // 测试02
//			boolean t02 = writeCoils(1, 0, new boolean[] { true, false, true });
//			System.out.println("T02:" + t02);

            // 测试03
//			short v = -3;
//			boolean t03 = writeRegister("192.168.0.7", 502, 1,0,v);
//			System.out.println("T03:" + t03);
            // 测试04
//			boolean t04 = writeRegisters(1, 0, new short[] { -3, 3, 9 });
//			System.out.println("t04:" + t04);
            //写模拟量
//            for (int i = 0; i < 103; i++) {
//                int i1 = new Random().nextInt(103 - 0) + 0;
//                writeHoldingRegister("127.0.0.1", 504, 1, 0, i, DataType.TWO_BYTE_INT_SIGNED);
//            }



//            writeHoldingRegister("192.168.0.7",502,1,0,2304,DataType.TWO_BYTE_INT_UNSIGNED);
//            int a = 0x2305;
//            int b = 0x2906;
//            int v = 0x1005;
//            LocalDateTime currentDateTime = LocalDateTime.now();
//            int year = currentDateTime.getYear();
//            int month = currentDateTime.getMonthValue();
//            int day = currentDateTime.getDayOfMonth();
//            int hour = currentDateTime.getHour();
//            int minute = currentDateTime.getMinute();
//            int second = currentDateTime.getSecond();
//            System.out.println("当前日期时间：" + currentDateTime);
//            System.out.println("年份：" + year);
//            System.out.println("月份：" + month);
//            System.out.println("日期：" + day);
//            System.out.println("小时：" + hour);
//            System.out.println("分钟：" + minute);
//            System.out.println("秒：" + second);
//            short[] aa  = {Short.parseShort(String.valueOf(a)), Short.parseShort(String.valueOf(b)),Short.parseShort(String.valueOf(v))};
//            writeRegisters("192.168.0.7",502,1,0,aa);


            ModbusMaster master = getMaster("192.168.0.2", 502);
//            master.getValue(1,23,0,DataType.TWO_BYTE_INT_SIGNED);
            //@formatter:on
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}