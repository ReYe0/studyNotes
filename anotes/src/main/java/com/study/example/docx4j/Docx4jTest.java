package com.study.example.docx4j;

import lombok.extern.slf4j.Slf4j;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SuppressWarnings("all")
public class Docx4jTest {
    //用一些文本创建文档
    @Test
    public void test1() throws Docx4JException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();//创建一个空白word
        wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");//添加一个文本段落
        wordMLPackage.save(new File("HelloWord1.docx"));
    }
    //添加带样式的文本
    @Test
    public void test2() throws Docx4JException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello Word!");
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle","This is a subtitle!");
        wordMLPackage.save(new File("HelloWord2.docx"));
    }
    //添加表格
    @Test
    public void test3() throws Docx4JException {
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = Context.getWmlObjectFactory();//工厂
        Tbl table = factory.createTbl();//创建表格
        Tr tableRow = factory.createTr();//创建一行

        Tc tableCell_1 = factory.createTc();//创建单元格
        tableCell_1.getContent().add(wordMlPackage.getMainDocumentPart().createParagraphOfText("Field 1"));//设置单元格内容
        tableRow.getContent().add(tableCell_1);//添加单元格到row中

        Tc tableCell_2 = factory.createTc();
        tableCell_1.getContent().add(wordMlPackage.getMainDocumentPart().createParagraphOfText("Field 2"));
        tableRow.getContent().add(tableCell_2);


        table.getContent().add(tableRow);//将row添加到表格中

        wordMlPackage.getMainDocumentPart().addObject(table);//将表格添加到word中
        wordMlPackage.save(new File("HelloWord4.docx"));//保存
    }


    //给表格添加边框
    @Test
    public void test4() throws Docx4JException {
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = Context.getWmlObjectFactory();//工厂
        Tbl table = factory.createTbl();
        Tr tableRow = factory.createTr();
        Tc tableCell = factory.createTc();//创建单元格
        tableCell.getContent().add(wordMlPackage.getMainDocumentPart().createParagraphOfText("Field 1"));//设置单元格内容
        tableRow.getContent().add(tableCell);//添加单元格到row中
        table.getContent().add(tableRow);//将row添加到表格中

        addBorders(table);

        wordMlPackage.getMainDocumentPart().addObject(table);//将表格添加到word中
        wordMlPackage.save(new File("HelloWord5.docx"));//保存
    }
    //加边框
    void addBorders(Tbl table) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");//默认黑色
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }

    //给表格添加样式
    @Test
    public void test6() throws Docx4JException{
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = Context.getWmlObjectFactory();//工厂
        Tbl tab = factory.createTbl();
        Tr row = factory.createTr();
        Tc tc = factory.createTc();
        //第一个单元格
        tc.getContent().add(wordMlPackage.getMainDocumentPart().createParagraphOfText("Normal text"));
        row.getContent().add(tc);
        //第二个单元格
        Tc tc2 = factory.createTc();
        P paragraph = factory.createP();//创建段落
        Text text = factory.createText();//文本对象
        text.setValue("Bold text");

        R run = factory.createR();//创建运行块对象,它是一块或多块拥有共同属性的文本的容器
        run.getContent().add(text);//将文本对象添加到其中.

        paragraph.getContent().add(run);//运行块R添加到段落内容中.

        RPr runProperties = factory.createRPr();//运行属性

        BooleanDefaultTrue b = new BooleanDefaultTrue();//默认为true
        b.setVal(true);

        runProperties.setB(b);//将运行块属性添加为粗体属性

        HpsMeasure size = new HpsMeasure();//字体大小属性
        size.setVal(new BigInteger("40"));//这个属性规定是半个点(half-point)大小, 因此字体大小需要是你想在Word中显示大小的两倍,

        runProperties.setSz(size);//猜测是整体大小
        runProperties.setSzCs(size);//猜测为单元格大小

        run.setRPr(runProperties);//将样式添加进去

        tc2.getContent().add(paragraph);
        row.getContent().add(tc2);


        tab.getContent().add(row);
        addBorders(tab);

        wordMlPackage.getMainDocumentPart().addObject(tab);
        wordMlPackage.save(new File("helloWord6.docx"));
    }

    //纵向合并单元格
    @Test
    public void test7() throws Docx4JException{
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = Context.getWmlObjectFactory();//工厂
        Tbl tab = factory.createTbl();
        addBorders(tab);

        //创建一行, 并向其中添加合并列, 然后添加再两个普通的单元格. 随后将该行添加到表格
        Tr row = factory.createTr();
        Tc tc = factory.createTc();
        TcPr tableCellProperties = new TcPr();
        TcPrInner.VMerge merge = new TcPrInner.VMerge();//合并单元格对象
        merge.setVal("2");//合并数量
        tableCellProperties.setVMerge(merge);//配置属性

        tc.setTcPr(tableCellProperties);
        tc.getContent().add(wordMlPackage.getMainDocumentPart().createParagraphOfText("7777"));
        row.getContent().add(tc);



        wordMlPackage.getMainDocumentPart().addObject(tab);
        wordMlPackage.save(new File("helloWord7.docx"));


    }

    //封装工具类使用
    @Test
    public void test8() throws Docx4JException{
        WordprocessingMLPackage wordMlPackage = WordprocessingMLPackage.createPackage();
        ObjectFactory factory = new ObjectFactory();
        Tbl tbl = TableUtil.createTable("red", "10", JcEnumeration.CENTER);
        Tr tr = factory.createTr();
        TableUtil.addTableTc(tr,"测试",160,true,"20","green","yellow");
        TableUtil.addTableTc(tr,"测试2",160,true,"20","green","yellow");


        tbl.getContent().add(tr);
        wordMlPackage.getMainDocumentPart().addObject(tbl);//将表格添加到word中
        wordMlPackage.save(new File("HelloWord8.docx"));//保存
    }
    //获取已有的word修改其表格
    @Test
    public void test9(){
        WordprocessingMLPackage wordMlPackage = null;
        try {
            wordMlPackage = WordprocessingMLPackage.load(new File("helloWord6.docx"));
        } catch (Docx4JException e) {
            log.error("文件加载失败");
            e.printStackTrace();
        }
        MainDocumentPart mainDocPart = wordMlPackage.getMainDocumentPart();
        List<Object> children = ((ContentAccessor) mainDocPart).getContent();
        JAXBElement jaxbElement = (JAXBElement) children.get(0);
        Tbl tbl = (Tbl) jaxbElement.getValue();
        List<Object> trList = tbl.getContent();
        Tr tr = (Tr) trList.get(0);

        JAXBElement jax_tc = (JAXBElement) tr.getContent().get(0);
        Tc tc = (Tc) jax_tc.getValue();
        P p = (P) tc.getContent().get(0);//段落
        R r = (R) p.getContent().get(0);//行
        JAXBElement jax = (JAXBElement) r.getContent().get(0);
        Text text = (Text) jax.getValue();
        String value = text.getValue();
        log.error("获取到表格中第一列第一行的值为：" + value);
        text.setValue("我成功啦");
        try {
            wordMlPackage.save(new File("helloWord6.docx"));
        } catch (Docx4JException e) {
            log.error("保存文件失败");
            e.printStackTrace();
        }
    }



    /**
     * @Description:得到所有表格
     */
    public List<Tbl> getAllTbl(WordprocessingMLPackage wordMLPackage) {
        MainDocumentPart mainDocPart = wordMLPackage.getMainDocumentPart();
        List<Object> objList = getAllElementFromObject(mainDocPart, Tbl.class);
        if (objList == null) {
            return null;
        }
        List<Tbl> tblList = new ArrayList<Tbl>();
        for (Object obj : objList) {
            if (obj instanceof Tbl) {
                Tbl tbl = (Tbl) obj;
                tblList.add(tbl);
            }
        }
        return tblList;
    }
    /**
     * 得到指定类型的元素
     * @param obj
     * @param toSearch
     * @return
     */
    public static List<Object> getAllElementFromObject(Object obj,Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }
































}
