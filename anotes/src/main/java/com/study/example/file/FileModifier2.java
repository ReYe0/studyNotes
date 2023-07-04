package com.study.example.file;

import java.io.*;

public class FileModifier2 {
    public static void main(String[] args) {
        // 文件路径
        String filePath = "D:\\BaiduNetdiskDownload\\中广核湖北天门项目2023.4.19-5.24\\华湖03号机组_20230524081056.sql";
//        String filePath = "D:\\BaiduNetdiskDownload\\中广核湖北天门项目2023.4.19-5.24\\新建文本文档.txt";
        long l = System.currentTimeMillis();
        try {
            // 创建输入流
            FileInputStream inputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // 创建临时文件，用于存储修改后的数据
            File tempFile = File.createTempFile("temp", null);
            PrintWriter printWriter = new PrintWriter(tempFile);

            // 逐行处理文件内容
            String line;
            boolean flag = false;
            String row3 = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("LOCK TABLES `d_")){//第一行
                    String disable_keys = bufferedReader.readLine();
                    if (disable_keys.contains("DISABLE KEYS")){//第二行
                        row3 = bufferedReader.readLine();
                        if (row3.contains("ENABLE KEYS ")){//第三行
                            flag = false;//空表
                            String s = bufferedReader.readLine();//去掉最后一行
                            if (!s.contains("UNLOCK TABLES")){
                                printWriter.println(s);
                            }
                        }else {
                            flag = true;
                        }
                    }
                }else if (!line.contains("/*!40000 ALTER TABLE `d_")) {
                    printWriter.println(line);
                }
                if (flag){
                    printWriter.println(line);
                    printWriter.println(row3);
                    row3 = null;
                    while (flag){
                        String nextRow = bufferedReader.readLine();
                        if (!nextRow.contains("` ENABLE KEYS")) printWriter.println(nextRow);
                        if (nextRow.contains("UNLOCK TABLES")) flag = false;
                    }
                }
                // 修改每一行的内容并写入临时文件
//                String modifiedLine = line.replace("old", "new");
//                printWriter.println(modifiedLine);
            }

            // 关闭流
            bufferedReader.close();
            printWriter.close();
            inputStream.close();
            inputStreamReader.close();

            // 删除原始文件
            File originalFile = new File(filePath);
            originalFile.delete();

            // 重命名临时文件为原始文件名
            tempFile.renameTo(originalFile);

            System.out.println("文件内容已成功修改！");
        } catch (IOException e) {
            System.out.println("处理文件时出现错误：" + e.getMessage());
        }
        System.out.println("消耗时间：" + (System.currentTimeMillis() - l));
    }
}
