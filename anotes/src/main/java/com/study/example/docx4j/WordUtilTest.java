package com.study.example.docx4j;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordUtilTest {

    @Test
    public void test1() throws FileNotFoundException, Docx4JException {
        WordUtil.Builder builder = WordUtil.of("test.docx");
        System.out.println();
        builder.addParam("test","testVale");
        builder.addParam("test2","testVale2");
        for (byte b : builder.getByte()) {
            System.out.println(b);
        }
    }

    @Test
    public void test2() throws FileNotFoundException, Docx4JException, JAXBException {
        //模板文件路径

//        String path = this.getClass().getClassLoader().getResource("test.docx").getPath();
        String path = "test.docx";


        //模板中要生成表格的数据

        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            Map<String, String> m = new HashMap<>();

            m.put("name", "姓名"+i);

            m.put("sex", "性别"+i);

            m.put("age", "年龄"+i);

            m.put("bz", "备注"+i);

            m.put("xx", "详细"+i);

            list.add(m);

        }

        list.stream();



        //模板中要插入图片的数据

//        byte[] img = null;
//
//        try (InputStream input = new FileInputStream(this.getClass().getClassLoader().getResource("template/timg.jpg").getPath())){
//
//            img = new byte[input.available()];
//
//            input.read(img);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }



        //要插入的map数据

        Map<String, String> m = new HashMap<>();

        m.put("today", LocalDate.now().toString());

        m.put("active", "游泳");


        //处理好数据后就是超级简单的调用
        byte[] content = WordUtil.of(path)
                .addParam("title", "测试文档标题")
                .addParam("user", "测试人")
                .addParams(m)
                .addTable("name", 2, list)
//                .addImg("img", img)
                .getByte();
//        wordMlPackage.getMainDocumentPart().addObject(table);//将表格添加到word中
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        wordMlPackage.save(new File("Table_Test_Three.docx"));//保存
        System.out.println();

        WordUtil.Builder builder = WordUtil.of(path)
                .addParam("title", "测试文档标题")
                .addParam("user", "测试人")
                .addParams(m)
                .addTable("name", 2, list);
    }
}
