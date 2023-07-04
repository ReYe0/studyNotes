package com.study.example.file;

import java.io.*;

public class FileModifier {
    public static void main(String[] args) {
        // 文件路径
        String filePath = "D:\\BaiduNetdiskDownload\\中广核湖北天门项目2023.4.19-5.24\\华湖03号机组_20230524081056.sql";

        try {
            // 创建一个文件对象
            File file = new File(filePath);

            // 创建一个文件读取器
            FileReader fileReader = new FileReader(file);

            // 创建一个缓冲区字符读取器
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 用于存储文件内容的可变字符串
            StringBuilder contentBuilder = new StringBuilder();

            // 逐行读取文件内容并存储到可变字符串中
            String line;
            boolean flag = false;
            String row3 = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("LOCK TABLES")){//第一行
                    String disable_keys = bufferedReader.readLine();
                    if (disable_keys.contains("DISABLE KEYS")){//第二行
                        row3 = bufferedReader.readLine();
                        if (row3.contains("ENABLE KEYS ")){//第三行
                            flag = true;
                        }else if (row3.contains("UNLOCK TABLES")){
                            break;
                        }
                    }
                }
                contentBuilder.append(line).append("\n");
                if (flag){
                    contentBuilder.append(row3).append("\n");
                    row3 = null;
                    while (flag){
                        String nextRow = bufferedReader.readLine();
                        contentBuilder.append(nextRow).append("\n");
                        if (nextRow.contains("UNLOCK TABLES")) flag = false;
                    }
                }
            }

            // 关闭读取器
            bufferedReader.close();

            // 获取文件内容
            String content = contentBuilder.toString();

            // 修改文件内容（假设替换所有"old"为"new"）
            String modifiedContent = content.replace("old", "new");

            // 创建一个文件写入器
            FileWriter fileWriter = new FileWriter(file);

            // 创建一个缓冲区字符写入器
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // 将修改后的内容写入文件
            bufferedWriter.write(modifiedContent);

            // 关闭写入器
            bufferedWriter.close();

            System.out.println("文件内容已成功修改！");
        } catch (IOException e) {
            System.out.println("修改文件内容时出现错误：" + e.getMessage());
        }
    }
}
