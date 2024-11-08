package com.modbus;

import java.io.OutputStream;
import java.net.Socket;

public class ModbusCustomCommandApp {
    public static void main(String[] args) {
        test();
    }

    public static void test()  {
        String ip = "192.168.0.80"; // 从站的IP地址
        int port = 10123; // Modbus TCP标准端口

        // Modbus指令
        byte[] modbusCommand = new byte[]{
                0x11, // 从站地址（17的十六进制是11）
                0x05, // 写单个线圈的功能码
                0x00, 0x00, // 线圈地址（0000表示第一个线圈）
                (byte) 0xFF, 0x00, // 数据（FF00表示闭合状态）
                (byte) 0x8E, (byte) 0xAA // CRC校验码
        };

        // 创建TCP连接
        try (Socket socket = new Socket(ip, port)) {
            OutputStream os = socket.getOutputStream();

            // 发送Modbus指令
            os.write(modbusCommand);
            os.flush();

            System.out.println("Modbus指令已发送。");

            // 处理响应（可选）
            // 如果需要处理响应，可以读取输入流
            // InputStream is = socket.getInputStream();
            // byte[] response = new byte[8];
            // is.read(response);
            // System.out.println("收到响应: " + Arrays.toString(response));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
