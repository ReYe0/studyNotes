package com.study.example.tcp;

import cn.hutool.core.util.StrUtil;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class TCPTest {

    /**
     * 将byte数组数据转换成float
     *
     * @param arr
     * @return
     */
    public static float bytes2Float(byte[] arr) {
        int accum = 0;
        accum = accum | (arr[0] & 0xff) << 0;
        accum = accum | (arr[1] & 0xff) << 8;
        accum = accum | (arr[2] & 0xff) << 16;
        accum = accum | (arr[3] & 0xff) << 24;
        return Float.intBitsToFloat(accum);
    }

    /**
     * byte数组到int的转换(小端)
     *
     * @param bytes
     * @return
     */
    public static int bytes2IntLittle(byte[] bytes) {
        int int1 = bytes[0] & 0xff;
        int int2 = (bytes[1] & 0xff) << 8;
        int int3 = (bytes[2] & 0xff) << 16;
        int int4 = (bytes[3] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    /**
     * 构建目录
     *
     * @param outputDir
     * @param subDir
     */
    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || subDir.trim().equals(""))) {//子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.mkdirs();
        }
    }

    /**
     * 解压.tar.gz文件
     *
     * @param sourceFile 需解压文件
     * @param outputDir  输出目录
     * @throws IOException
     */
    public static void unTarGz(String sourceFile, String outputDir) throws IOException {
        TarInputStream tarIn = null;
        File file = new File(sourceFile);
        try {
            tarIn = new TarInputStream(new GZIPInputStream(
                    new BufferedInputStream(new FileInputStream(file))),
                    1024 * 2);

            createDirectory(outputDir, null);//创建输出目录

            TarEntry entry = null;
            while ((entry = tarIn.getNextEntry()) != null) {

                if (entry.isDirectory()) {//是目录
                    entry.getName();
                    createDirectory(outputDir, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(outputDir + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/", null);//创建输出目录
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(tmpFile);
                        int length = 0;

                        byte[] b = new byte[2048];

                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }

                    } catch (IOException ex) {
                        throw ex;
                    } finally {

                        if (out != null)
                            out.close();
                    }
                }
            }
        } catch (IOException ex) {
            throw new IOException("解压归档文件出现异常", ex);
        } finally {
            try {
                if (tarIn != null) {
                    tarIn.close();
                }
            } catch (IOException ex) {
                throw new IOException("关闭tarFile出现异常", ex);
            }
        }
    }

    @Test
    public void test3() {
        test2();
        test();
    }

    @Test
    public void test() {
        //创建Socket对象，连接服务器
        try {
            while (true) {
                Socket socket = new Socket("127.0.0.1", 7001);
//            Socket socket=new Socket("192.168.3.240",7090);
                boolean connected = socket.isConnected();
                if (connected) {
                    System.out.println("连接成功");
                } else {
                    System.out.println("连接失败");
                }
                //通过客户端的套接字对象Socket方法，获取字节输出流，将数据写向服务器
//            OutputStream out=socket.getOutputStream();
//            out.write("cmd=:SATA:PVJ?".getBytes());
                //读取服务器发回的数据，使用socket套接字对象中的字节输入流

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int receiveBufferSize = socket.getReceiveBufferSize();
                System.out.println(receiveBufferSize);
//                InputStream in=socket.getInputStream();
                byte[] data = new byte[1024];
                int len = dis.read(data);
                if (len == -1) {
                    System.out.println("len=" + len);
                } else {
                    System.out.println(new String(data, 0, len));
                }
                socket.close();
                dis.close();
//                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Test
    public void test33() {
        try {
            ServerSocket server = new ServerSocket(7090);
            //调用服务器套接字对象中的方法accept()获取客户端套接字对象
            Socket socket = server.accept();
            //通过客户端套接字对象，socket获取字节输入流,读取的是客户端发送来的数据
            while (true) {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                System.out.println(dis);
            }
        } catch (Exception e) {

        }
    }

    @Test
    public void test2() {
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream dis = null;
        try {
            int b = 0;
            System.out.println(System.currentTimeMillis());
            server = new ServerSocket(7002);
            //调用服务器套接字对象中的方法accept()获取客户端套接字对象

            HashMap<String, Object> map = new HashMap<>();
            InetAddress inetAddress = server.getInetAddress();
            boolean reuseAddress = server.getReuseAddress();
            server.setSoTimeout(3000);
            socket = server.accept();
            InetAddress localAddress = socket.getLocalAddress();
            String hostAddress = localAddress.getHostAddress();
            String remoteSocketAddress = String.valueOf(socket.getRemoteSocketAddress());
            map.put(String.valueOf(b), remoteSocketAddress);
            String[] split = remoteSocketAddress.split(":");
            String replace = split[0].replace("/", "");
            //通过客户端套接字对象，socket获取字节输入流,读取的是客户端发送来的数据
            dis = new DataInputStream(socket.getInputStream());

            while (true) {

                byte[] startSign_arr = new byte[4];//起始标识，四个字节，16进制
                dis.read(startSign_arr);
                String startSign = bytesToHexString(startSign_arr);


                byte dataVer_val = dis.readByte();//数据版本，一个字节，16进制
                String hex = Integer.toHexString(0xFF & dataVer_val);

                byte verify_val = dis.readByte();//校验，一个字节
                String verify = Integer.toHexString(0xFF & verify_val);

                byte[] deviceNum_arr = new byte[4];//设备序列号，四个字节
                dis.read(deviceNum_arr);
                int deviceNum = bytes2IntLittle(deviceNum_arr);

                byte[] deviceTime_arr = new byte[4];//设备时间，四个字节
                dis.read(deviceTime_arr);
                int i13 = bytes2IntLittle(deviceTime_arr);
                String s3 = bytesToHexString(deviceTime_arr);


                byte[] microsecond_arr = new byte[4];//微秒，四个字节
                dis.read(microsecond_arr);
                int microsecond = bytes2IntLittle(microsecond_arr);
                System.out.println(microsecond);


                byte[] counter_arr = new byte[4];//计数器，四个字节
                dis.read(counter_arr);
                int i3 = bytes2IntLittle(counter_arr);
                int i1 = bytes2IntBig(counter_arr);


                byte[] deviceStatus_arr = new byte[4];//设备状态，四个字节，16进制
                dis.read(deviceStatus_arr);
                int i = bytes2IntLittle(deviceStatus_arr);
//            String s1 = "Ox" + Integer.toHexString(i);
//            int s2 = Integer.parseInt(s1);
                int c = 0x00000001;
                boolean aa = i == c;
                System.out.println(aa);
                String deviceStatus = bytesToHex(deviceStatus_arr);

                dis.read(new byte[4]);//设备状态2


                float LaserTemperature = dis.readFloat();//激光器温度，4byte float


                byte ch_num_val = dis.readByte();//系统有效通道数，1byte
                int ch_num = Byte.toUnsignedInt(ch_num_val);


                //方式一 pass
                int a = 0;
                for (int j = 0; j < 32; j++) {
                    byte sensor = dis.readByte();//传感器数量，一个通道一个字节
                    a += sensor;
                }
                byte[] bytes = new byte[32];
                dis.read(bytes);
                ByteBuffer byteBuffer = ByteBuffer.allocate(32 * 4);
                byteBuffer.put(bytes);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                ArrayList<Float> list = new ArrayList<>();
                for (int m = 0; m < 24; m++) {//12个通道每个通道两个数据
                    float aFloat = byteBuffer.getFloat(m * 4);
                    list.add(aFloat);
                }
                dis.read(new byte[4]);
                byte[] bytes1 = new byte[4];
                dis.read(bytes1);
                String s = bytesToHexString(bytes1);
                System.out.println(s);
                //服务器向客户端回数据，字节输出流，通过客户端套接字对象获取字节输出流

                System.out.println(System.currentTimeMillis());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void test4() {
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream dis = null;
        try {
            int b = 0;
            System.out.println(System.currentTimeMillis());
            server = new ServerSocket(7002);
            //调用服务器套接字对象中的方法accept()获取客户端套接字对象

            HashMap<String, Object> map = new HashMap<>();
            InetAddress inetAddress = server.getInetAddress();
            boolean reuseAddress = server.getReuseAddress();
            server.setSoTimeout(3000);
            socket = server.accept();
            InetAddress localAddress = socket.getLocalAddress();
            String hostAddress = localAddress.getHostAddress();
            String remoteSocketAddress = String.valueOf(socket.getRemoteSocketAddress());
            map.put(String.valueOf(b), remoteSocketAddress);
            String[] split = remoteSocketAddress.split(":");
            String replace = split[0].replace("/", "");
            //通过客户端套接字对象，socket获取字节输入流,读取的是客户端发送来的数据

            dis = new DataInputStream(socket.getInputStream());
            int a = 0;
            while (true && a < 6000) {

                byte[] startSign_arr = new byte[4];//起始标识，四个字节，16进制
                dis.read(startSign_arr);
                String startSign = bytesToHexString(startSign_arr);
                System.out.println(startSign);
                while (!"ffffffff".equals(startSign)){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a++;
                    System.out.println(startSign);
                    startSign_arr = new byte[4];
                    dis.read(startSign_arr);
                    startSign = bytesToHexString(startSign_arr);
                }


                byte dataVer_val = dis.readByte();//数据版本，一个字节，16进制
                String hex = Integer.toHexString(0xFF & dataVer_val);

                byte verify_val = dis.readByte();//校验，一个字节
                String verify = Integer.toHexString(0xFF & verify_val);

                byte[] deviceNum_arr = new byte[4];//设备序列号，四个字节
                dis.read(deviceNum_arr);
                int deviceNum = bytes2IntLittle(deviceNum_arr);

                byte[] deviceTime_arr = new byte[4];//设备时间，四个字节
                dis.read(deviceTime_arr);
                int i13 = bytes2IntLittle(deviceTime_arr);
                String s3 = bytesToHexString(deviceTime_arr);


                byte[] microsecond_arr = new byte[4];//微秒，四个字节
                dis.read(microsecond_arr);
                int microsecond = bytes2IntLittle(microsecond_arr);
                System.out.println(microsecond);


                byte[] counter_arr = new byte[4];//计数器，四个字节
                dis.read(counter_arr);
                int i3 = bytes2IntLittle(counter_arr);
                int i1 = bytes2IntBig(counter_arr);


                byte[] deviceStatus_arr = new byte[4];//设备状态，四个字节，16进制
                dis.read(deviceStatus_arr);
                int i = bytes2IntLittle(deviceStatus_arr);
//            String s1 = "Ox" + Integer.toHexString(i);
//            int s2 = Integer.parseInt(s1);
                int c = 0x00000001;
                boolean aa = i == c;
                System.out.println(aa);
                String deviceStatus = bytesToHex(deviceStatus_arr);

                dis.read(new byte[4]);//设备状态2


                float LaserTemperature = dis.readFloat();//激光器温度，4byte float


                byte ch_num_val = dis.readByte();//系统有效通道数，1byte
                int ch_num = Byte.toUnsignedInt(ch_num_val);


                List<Integer> sensorList = new ArrayList<>();
                for (int j = 0; j < ch_num; j++) {
                    //传感器数量，一个通道一个字节
                    sensorList.add((int) dis.readByte());
                }
                if (ch_num < 32) dis.read(new byte[32-ch_num]);
                for (int j = 0; j < sensorList.size(); j++) {
                    byte[] bytes = new byte[4 * sensorList.get(j)];
                    dis.read(bytes);
                }
//                dis.read(new byte[4]);
                byte[] bytes1 = new byte[4];
                dis.read(bytes1);
                String s = bytesToHexString(bytes1);
                System.out.println(s);
                //服务器向客户端回数据，字节输出流，通过客户端套接字对象获取字节输出流

                System.out.println(System.currentTimeMillis());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test5(){
        while (true){
            test4();
        }
    }
    @Test
    public void test6() {
        ServerSocket server = null;
        Socket socket = null;
        DataInputStream dis = null;
        try {
            int b = 0;
            System.out.println(System.currentTimeMillis());
            server = new ServerSocket();
            server.setSoTimeout(1000 * 60);
            if (!server.isBound()){
                server.bind(new InetSocketAddress("10.100.50.56",7002));
            }
//            server = new ServerSocket(7002);
            //调用服务器套接字对象中的方法accept()获取客户端套接字对象

            HashMap<String, Object> map = new HashMap<>();
            InetAddress inetAddress = server.getInetAddress();
            boolean reuseAddress = server.getReuseAddress();
            server.setSoTimeout(3000);
            socket = server.accept();
            InetAddress localAddress = socket.getLocalAddress();
            String hostAddress = localAddress.getHostAddress();
            String remoteSocketAddress = String.valueOf(socket.getRemoteSocketAddress());
            map.put(String.valueOf(b), remoteSocketAddress);
            String[] split = remoteSocketAddress.split(":");
            String replace = split[0].replace("/", "");
            //通过客户端套接字对象，socket获取字节输入流,读取的是客户端发送来的数据

            dis = new DataInputStream(socket.getInputStream());
            int a = 0;
            while (true && a < 6000) {

                byte[] startSign_arr = new byte[4];//起始标识，四个字节，16进制
                dis.read(startSign_arr);
                String startSign = bytesToHexString(startSign_arr);
                System.out.println(startSign);
                while (!"ffffffff".equals(startSign)){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a++;
                    System.out.println(startSign);
                    startSign_arr = new byte[4];
                    dis.read(startSign_arr);
                    startSign = bytesToHexString(startSign_arr);
                }


                byte dataVer_val = dis.readByte();//数据版本，一个字节，16进制
                String hex = Integer.toHexString(0xFF & dataVer_val);

                byte verify_val = dis.readByte();//校验，一个字节
                String verify = Integer.toHexString(0xFF & verify_val);

                byte[] deviceNum_arr = new byte[4];//设备序列号，四个字节
                dis.read(deviceNum_arr);
                int deviceNum = bytes2IntLittle(deviceNum_arr);

                byte[] deviceTime_arr = new byte[4];//设备时间，四个字节
                dis.read(deviceTime_arr);
                int i13 = bytes2IntLittle(deviceTime_arr);
                String s3 = bytesToHexString(deviceTime_arr);


                byte[] microsecond_arr = new byte[4];//微秒，四个字节
                dis.read(microsecond_arr);
                int microsecond = bytes2IntLittle(microsecond_arr);
                System.out.println(microsecond);


                byte[] counter_arr = new byte[4];//计数器，四个字节
                dis.read(counter_arr);
                int i3 = bytes2IntLittle(counter_arr);
                int i1 = bytes2IntBig(counter_arr);


                byte[] deviceStatus_arr = new byte[4];//设备状态，四个字节，16进制
                dis.read(deviceStatus_arr);
                int i = bytes2IntLittle(deviceStatus_arr);
//            String s1 = "Ox" + Integer.toHexString(i);
//            int s2 = Integer.parseInt(s1);
                int c = 0x00000001;
                boolean aa = i == c;
                System.out.println(aa);
                String deviceStatus = bytesToHex(deviceStatus_arr);

                dis.read(new byte[4]);//设备状态2


                float LaserTemperature = dis.readFloat();//激光器温度，4byte float


                byte ch_num_val = dis.readByte();//系统有效通道数，1byte
                int ch_num = Byte.toUnsignedInt(ch_num_val);


                List<Integer> sensorList = new ArrayList<>();
                for (int j = 0; j < ch_num; j++) {
                    //传感器数量，一个通道一个字节
                    sensorList.add((int) dis.readByte());
                }
                if (ch_num < 32) dis.read(new byte[32-ch_num]);
                for (int j = 0; j < sensorList.size(); j++) {
                    byte[] bytes = new byte[4 * sensorList.get(j)];
                    dis.read(bytes);
                }
//                dis.read(new byte[4]);
                byte[] bytes1 = new byte[4];
                dis.read(bytes1);
                String s = bytesToHexString(bytes1);
                System.out.println(s);
                //服务器向客户端回数据，字节输出流，通过客户端套接字对象获取字节输出流

                System.out.println(System.currentTimeMillis());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value += bytes[i] & 0xff << (3 - i) * 8;
        }
        return value;
    }

    /**
     * 把byte转化成2进制字符串
     *
     * @param b
     * @return
     */
    public String getBinaryStrFromByte(byte b) {
        String result = "";
        byte a = b;
        ;
        for (int i = 0; i < 8; i++) {
            byte c = a;
            a = (byte) (a >> 1);//每移一位如同将10进制数除以2并去掉余数。
            a = (byte) (a << 1);
            if (a == c) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            a = (byte) (a >> 1);
        }
        return result;
    }

    /**
     * byte数组到int的转换(大端)
     *
     * @return
     */
    public int bytes2IntBig(byte[] bytes) {
        int int1 = bytes[3] & 0xff;
        int int2 = (bytes[2] & 0xff) << 8;
        int int3 = (bytes[1] & 0xff) << 16;
        int int4 = (bytes[0] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    @Test
    public void test11() {
//        String str = "sdfdfK";
//        System.out.println(str.replace("/","//"));
        String file = "E:\\资料\\雷电\\光纤\\saData\\saData-1026\\Data\\sa-10032-230112-140819.dat";
        File startFile = new File(file);
        FileInputStream in = null;
        try {
            in = new FileInputStream(startFile);
            DataInputStream dis = new DataInputStream(in);
            ServerSocket server = null;
            Socket socket = null;
            System.out.println(System.currentTimeMillis());
//            server = new ServerSocket(7002);
            //调用服务器套接字对象中的方法accept()获取客户端套接字对象

            HashMap<String, Object> map = new HashMap<>();
            byte[] bytes2 = new byte[4481];
            dis.read(bytes2);
            byte[] startSign_arr = new byte[4];//起始标识，四个字节，16进制
            dis.read(startSign_arr);
            String startSign = bytesToHexString(startSign_arr);


            byte dataVer_val = dis.readByte();//数据版本，一个字节，16进制
            String hex = Integer.toHexString(0xFF & dataVer_val);

            byte verify_val = dis.readByte();//校验，一个字节
            String verify = Integer.toHexString(0xFF & verify_val);

            byte[] deviceNum_arr = new byte[4];//设备序列号，四个字节
            dis.read(deviceNum_arr);
            int deviceNum = bytes2IntLittle(deviceNum_arr);
            int i = bytes2IntBig(deviceNum_arr);

            byte[] deviceTime_arr = new byte[4];//设备时间，四个字节
            dis.read(deviceTime_arr);
            int i13 = bytes2IntLittle(deviceTime_arr);
            String s3 = bytesToHexString(deviceTime_arr);


            byte[] microsecond_arr = new byte[4];//微秒，四个字节
            dis.read(microsecond_arr);
            int microsecond = bytes2IntLittle(microsecond_arr);
            System.out.println(microsecond);
//            String microsecond = bytesToHexString(microsecond_arr);


            byte[] counter_arr = new byte[4];//计数器，四个字节
            dis.read(counter_arr);
            int i3 = bytes2IntLittle(counter_arr);
//                System.out.println(i3);
            int i1 = bytes2IntBig(counter_arr);


            byte[] deviceStatus_arr = new byte[4];//设备状态，四个字节，16进制
            dis.read(deviceStatus_arr);
            String deviceStatus = bytesToHexString(deviceStatus_arr);

            dis.read(new byte[4]);//设备状态2


            float LaserTemperature = dis.readFloat();//激光器温度，4byte float


            byte ch_num_val = dis.readByte();//系统有效通道数，1byte
            int ch_num = Byte.toUnsignedInt(ch_num_val);


            //方式一 pass
            int a = 0;
            for (int j = 0; j < 32; j++) {
                byte sensor = dis.readByte();//传感器数量，一个通道一个字节
                a += sensor;
            }
            byte[] bytes = new byte[32 * 4];
            dis.read(bytes);
            ByteBuffer byteBuffer = ByteBuffer.allocate(32 * 4);
            byteBuffer.put(bytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            ArrayList<Float> list = new ArrayList<>();
            for (int m = 0; m < 24; m++) {//12个通道每个通道两个数据
                float aFloat = byteBuffer.getFloat(m * 4);
                list.add(aFloat);
            }
            byte[] bytes1 = new byte[4];
            dis.read(bytes1);
            String s = bytesToHexString(bytes1);


            dis.read(new byte[15]);
//            while (true){
            byte[] bytes12 = new byte[4];
            dis.read(bytes12);
            String s2 = bytesToHexString(bytes12);
            System.out.println(s2);

            byte dataVer_val1 = dis.readByte();//数据版本，一个字节，16进制
            String hex1 = Integer.toHexString(0xFF & dataVer_val1);

            byte verify_val1 = dis.readByte();//校验，一个字节
            String verify1 = Integer.toHexString(0xFF & verify_val1);

            byte[] deviceNum_arr1 = new byte[4];//设备序列号，四个字节
            dis.read(deviceNum_arr1);
            int deviceNum_arr12 = bytes2IntLittle(deviceNum_arr1);
//            }

            System.out.println(deviceNum_arr12);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* @Title: readFileContent
     * @Description: 读取文件内容
     * @param filePath
     * @return
     */
    @Test
    public void readFileContent() {
        List<File> fileList = getAllFile("E:\\POF");
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return new Long(o2.lastModified()).compareTo(o1.lastModified());
            }
        });
        File file = fileList.get(0);
        try {
            unTarGz(String.valueOf(file), "E:\\POF\\DAT");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> datList = getAllFile("E:\\POF\\DAT");
        Collections.sort(datList, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return new Long(o2.lastModified()).compareTo(o1.lastModified());
            }
        });
//        File dat = datList.get(0);
        File dat = new File("E:\\POF\\10.100.50.168\\DAT\\sa-10032-230411-154223.dat");
        FileInputStream in = null;
        try {
            in = new FileInputStream(dat);
            DataInputStream dis = new DataInputStream(in);
            System.out.println(System.currentTimeMillis());
            //调用服务器套接字对象中的方法accept()获取客户端套接字对象
            byte[] bytes2 = new byte[4481];
            dis.read(bytes2);
            int flag = 0;
            int read = 0;
            while (read != -1) {
                ++flag;
                byte[] startSign_arr = new byte[4];//起始标识，四个字节，16进制
                read = dis.read(startSign_arr);
                if (read == -1) {
                    System.out.println(flag);
                    continue;
                }
                String startSign = bytesToHexString(startSign_arr);

                byte dataVer_val = dis.readByte();//数据版本，一个字节，16进制
                String hex = Integer.toHexString(0xFF & dataVer_val);

                byte verify_val = dis.readByte();//校验，一个字节
                String verify = Integer.toHexString(0xFF & verify_val);

                byte[] deviceNum_arr = new byte[4];//设备序列号，四个字节
                dis.read(deviceNum_arr);
                int deviceNum = bytes2IntLittle(deviceNum_arr);

                byte[] deviceTime_arr = new byte[4];//设备时间，四个字节
                dis.read(deviceTime_arr);
                int i13 = bytes2IntLittle(deviceTime_arr);
                String s3 = bytesToHexString(deviceTime_arr);


                byte[] microsecond_arr = new byte[4];//微秒，四个字节
                dis.read(microsecond_arr);
                int microsecond = bytes2IntLittle(microsecond_arr);


                byte[] counter_arr = new byte[4];//计数器，四个字节
                dis.read(counter_arr);
                int i3 = bytes2IntLittle(counter_arr);
                System.out.println(i3);
                String s1 = bytesToHexString(counter_arr);

                byte[] deviceStatus_arr = new byte[4];//设备状态，四个字节，16进制
                dis.read(deviceStatus_arr);
                String deviceStatus = bytesToHexString(deviceStatus_arr);

                dis.read(new byte[4]);//设备状态2


                float LaserTemperature = dis.readFloat();//激光器温度，4byte float


                byte ch_num_val = dis.readByte();//系统有效通道数，1byte
                int ch_num = Byte.toUnsignedInt(ch_num_val);

//                int a = 0;
                for (int j = 0; j < 32; j++) {
                    byte sensor = dis.readByte();//传感器数量，一个通道一个字节
//                    a += sensor;
                }
                byte[] bytes = new byte[32];
                dis.read(bytes);
                ByteBuffer byteBuffer = ByteBuffer.allocate(32 * 4);
                byteBuffer.put(bytes);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                ArrayList<Float> list = new ArrayList<>();
                for (int m = 0; m < 24; m++) {//12个通道每个通道两个数据
                    float aFloat = byteBuffer.getFloat(m * 4);
                    list.add(aFloat);
                }

//                int a = 0;
//                while (true){
//                    byte[] bytes1 = new byte[4];
//                    System.out.println(++a);
//                    dis.read(bytes1);
//                    String s = bytesToHexString(bytes1);//结尾标志
//                    System.out.println(s);
//                }


                dis.read(new byte[4]);//中间隔离4个字节
                byte[] bytes1 = new byte[4];
                dis.read(bytes1);
                String s = bytesToHexString(bytes1);//结尾标志
//                System.out.println(s);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFilePath 文件夹路径
     * @return
     */
    public List<File> getAllFile(String dirFilePath) {
        if (StrUtil.isBlank(dirFilePath))
            return null;
        return getAllFile(new File(dirFilePath));
    }

    public File getLastModifiedFile(File directory) {
        File[] files = directory.listFiles();
        if (files.length == 0) return null;
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return new Long(o2.lastModified()).compareTo(o1.lastModified());
            }
        });
        return files[0];
    }

    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFile 文件夹
     * @return
     */
    public List<File> getAllFile(File dirFile) {
        // 如果文件夹不存在或着不是文件夹，则返回 null
        if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile())
            return null;

        File[] childrenFiles = dirFile.listFiles();
        if (Objects.isNull(childrenFiles) || childrenFiles.length == 0)
            return null;

        List<File> files = new ArrayList<>();
        for (File childFile : childrenFiles) {
            // 如果是文件，直接添加到结果集合
            if (childFile.isFile()) {
                files.add(childFile);
            }
            //以下几行代码取消注释后可以将所有子文件夹里的文件也获取到列表里
//            else {
//                // 如果是文件夹，则将其内部文件添加进结果集合
//                List<File> cFiles = getAllFile(childFile);
//                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
//                files.addAll(cFiles);
//            }
        }
        return files;
    }

    @Test
    public void test11231() {

        int index = 0;
        ArrayList<String> strings = new ArrayList<>();
        while (strings.size() <= 60000) {
            strings.add("1");
        }
        System.out.println(strings);
    }

}
