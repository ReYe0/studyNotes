package com.study.example.tcp;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        //socket对象初始化
        Socket socket = null;
        //输出流 os对象初始化
        OutputStream os = null;
        ByteArrayOutputStream baos = null;
        DataInputStream dis = null;

        try {

            //1、创建Socket对象，它的第一个参数需要的是服务端的IP，第二个参数是服务端的端口
            InetAddress inet = InetAddress.getByName("10.100.50.89");//127：8234 有人，89:7772, gateway
            socket = new Socket(inet,7772);//inet是服务端ip
            boolean connected = socket.isConnected();
            if (connected){
                System.out.println(1);
            }else{
                System.out.println(0);
            }
//            socket.setSoTimeout(50000);
            //2、获取一个输出流，用于写出要发送的数据
            os = socket.getOutputStream();
//            System.out.println(stringToHex("02 04 03 E8 00 06 F0 4B"));
            //3、写出数据
            String a = "020403E80006F04B";
            os.write(hexStringToBytes(a));
//            System.out.println(hexStringToBytes(a).toString());

            dis = new DataInputStream(socket.getInputStream());
            int available = dis.available();
            System.out.println(available);
//            ByteBuffer.wrap(dis.)
//            byte b = dis.readByte();
//            byte b1 = dis.readByte();
//            byte b2 = dis.readByte();
//            int i = dis.readInt();
//            System.out.println("X 实际角度：" + i / 3600f);
//            int i1 = dis.readInt();
//            System.out.println("Y 实际角度：" + i1 /3600f);
//            System.out.println("实际温度：" + dis.readInt() /10f);

//            byte[] bytes = new byte[1024];
//            dis.read(bytes);
//            String hexString = DatatypeConverter.printHexBinary(bytes);
//            System.out.println(hexString);
//            System.out.println(dis);
//            byte b = dis.readByte();
//            Head head = new Head(dis);
//            System.out.println(head.toString());
//            byte[] startSign_arr = new byte[4];//起始标识，四个字节，16进制
//            dis.read(startSign_arr);
//            System.out.println(byteArrayToInt(startSign_arr));
//            byte[] bytes = new byte[4];
//            dis.read(bytes);
//            System.out.println(byteArrayToInt(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4、释放资源,别忘了哦！！！！
            if(socket!=null){
                try {
                    socket.close();//关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();//关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis!= null){
                try{
                    dis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value += bytes[i] & 0xff << (3 - i) * 8;
        }
        return value;
    }
    public static String stringToHex(String str){
        StringBuilder sb = new StringBuilder();
        for (char i : str.toCharArray()) {
            sb.append(Integer.toHexString((int) i));
        }
        return sb.toString();
    }
    /**
     * 将 16 进制字符串转为字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
