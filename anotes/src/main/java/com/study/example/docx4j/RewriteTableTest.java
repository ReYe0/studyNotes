package com.study.example.docx4j;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tr;
import org.junit.jupiter.api.Test;

import java.io.File;

public class RewriteTableTest {

    @Test
    public void createTableTest() throws Docx4JException {
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = new ObjectFactory();

//        wordMlPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");
        Tbl table = RewriteTableUtil.createTable("red", "20", JcEnumeration.CENTER);

//        Tr tr = factory.createTr();
//        Tc tc = factory.createTc();
//        tc.getContent().add(wordMlPackage.getMainDocumentPart().createParagraphOfText("我是表格"));
//        tr.getContent().add(tc);
//
//        table.getContent().add(tr);

        wordMlPackage.getMainDocumentPart().addObject(table);//将表格添加到word中
        wordMlPackage.save(new File("Table_Test_One.docx"));//保存
    }

    @Test
    public  void addTableTcTest() throws Docx4JException {
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = new ObjectFactory();

        Tbl table = RewriteTableUtil.createTable("black", "10", JcEnumeration.CENTER);
        Tr tr = factory.createTr();
        RewriteTableUtil.addTableTc(tr,"我是添加的单元格",1000,true,"20","宋体","red");
        RewriteTableUtil.addTableTc(tr,"我是添加的第二个单元格",1000,false,"20","宋体","yellow");

        table.getContent().add(tr);

        wordMlPackage.getMainDocumentPart().addObject(table);//将表格添加到word中
        wordMlPackage.save(new File("Table_Test_Two.docx"));//保存
    }

    @Test
    public void mergeCellsHorizontalTest() throws Docx4JException {
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = new ObjectFactory();

        Tbl table = RewriteTableUtil.createTable("black", "10", JcEnumeration.CENTER);
        Tr tr = factory.createTr();
        RewriteTableUtil.addTableTc(tr,"11",1000,true,"20","宋体","red");
        RewriteTableUtil.addTableTc(tr,"12",1000,false,"20","宋体","yellow");

        table.getContent().add(tr);

//        RewriteTableUtil.mergeCellsHorizontal(table,0,0,1);


        Tr tr1 = factory.createTr();
        RewriteTableUtil.addTableTc(tr1,"21",1000,true,"20","宋体","red");
        RewriteTableUtil.addTableTc(tr1,"22",1000,false,"20","宋体","yellow");


        table.getContent().add(tr1);

        Tr tr2 = factory.createTr();
        RewriteTableUtil.addTableTc(tr2,"31",1000,true,"20","宋体","red");
        RewriteTableUtil.addTableTc(tr2,"32",1000,false,"20","宋体","yellow");
        table.getContent().add(tr2);

        Tr tr3 = factory.createTr();
        RewriteTableUtil.addTableTc(tr3,"31",1000,true,"20","宋体","red");
        RewriteTableUtil.addTableTc(tr3,"32",1000,false,"20","宋体","yellow");
        table.getContent().add(tr3);

//        RewriteTableUtil.mergeCellsHorizontal(table,0,0,1);
        RewriteTableUtil.mergeCellsVertically(table,0,0,1);
//        RewriteTableUtil.mergeCellsVertically(table,1,1,2);
        RewriteTableUtil.mergeCellsHorizontal(table,2,0,1);
        RewriteTableUtil.mergeCellsHorizontal(table,3,0,1);
//        RewriteTableUtil.mergeCellsVertically(table,0,2,3);

        wordMlPackage.getMainDocumentPart().addObject(table);//将表格添加到word中
        wordMlPackage.save(new File("Table_Test_Three.docx"));//保存
    }
}
