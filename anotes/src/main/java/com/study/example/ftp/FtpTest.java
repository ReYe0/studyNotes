package com.study.example.ftp;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Slf4j
public class FtpTest {
    public static void main(String[] args) {
        uploadTest();
    }


    public static void uploadTest(){
        //生成文件并上传
        StringBuffer textStrBf= new StringBuffer("");
        textStrBf.append("Hello World !");
        String fileName = "test.txt";
        InputStream inputStream;

        inputStream = FtpClientUpFileUtil.write(textStrBf.toString());
        boolean a = FtpClientUpFileUtil.uploadFile(fileName, inputStream);
        log.error("上传文件结果："+a);
//        System.out.println("上传文件结果============" + a);

        //上传本地文件至FTP
        String originfilename = "D:\\test\\demo.txt";
        FileInputStream fileinputStream ;
        String fileName2 = "success.txt";
        try {
            fileinputStream = new FileInputStream(originfilename);
            boolean b = FtpClientUpFileUtil.uploadFile(fileName2, fileinputStream );
            log.error("上传本地文件结果:" + b);
//            System.out.println("上传文件结果============" + b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
