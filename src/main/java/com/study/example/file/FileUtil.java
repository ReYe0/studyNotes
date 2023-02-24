package com.study.example.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    /**
     * 将MultipartFile转换为File
     * @param multiFile
     * @return
     */
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public FileUtil() {
    }

    //获取某一列返回数组
    public static List<String> getOneColumn(File path, int column) {   //参数为路径和指定列
        BufferedReader br = null;
        String line = null;
        //该列表用于存储读取的指定列元素
        ArrayList arrayList = new ArrayList();
        try {
            InputStream in=new FileInputStream(path);
            br = new BufferedReader(new InputStreamReader(in, "GBK"));
//            br = new BufferedReader(new FileReader(path));

			/* 不需要标头执行此行
			line = br.readLine();
			*/
            //循环读取每行的指定列元素
            while ((line = br.readLine()) != null) {
                //","分割每行第column列
                //System.out.println(line.split(",")[column]);
                String a = line.split(",")[column];
                if (a != null && a != "") {
                    arrayList.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return arrayList;
    }
}
