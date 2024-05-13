package com.study.example.juc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Demo {
    public static int a = 20;
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < a; i++) {
            Thread.sleep(1000 * 1);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Socket socket = null;
                    String message = null;
                    InputStream inputStream = null;
                    try {
                        socket = new Socket("10.100.50.61", 5017);
                        System.out.println(Thread.currentThread().getName() +  "客户端已连接");
                        inputStream = socket.getInputStream();
                        byte[] data = new byte[1024];
                        inputStream.read(data);
                        System.out.println(Thread.currentThread().getName() + "收到服务器消息：" +data.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println(Thread.currentThread().getName() + "关闭");
                        if (socket != null){
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (inputStream != null){
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();

        }
    }
}
