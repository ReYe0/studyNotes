package com.study.example.docx4j;

import org.apache.commons.lang3.StringUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TableUtil {
    private static final ObjectFactory factory = new ObjectFactory();//创建一个新的对象工厂，该工厂可用于为包创建架构派生类的新实例：org.docx4j.wml

    /**
     * 创建一个表格
     * @param borderColor 边框颜色
     * @param borderSize 边框粗细
     * @param jcEnumeration 表格位置
     * @return org.docx4j.wml.Tbl
     * @author xuy
     * @date 2023/2/16 10:23
     */
    public static Tbl createTable(String borderColor, String borderSize, JcEnumeration jcEnumeration){
        Tbl tbl = factory.createTbl();//创建实例 Tbl
        //给table添加边框
        TableUtil.addBorders(tbl, borderColor, borderSize);
        if(jcEnumeration!=null) {
            //单元格居中对齐
            Jc jc = new Jc();
            //JcEnumeration.CENTER
            jc.setVal(jcEnumeration);//表格位置
            TblPr tblPr = tbl.getTblPr();//获取表格设置
            tblPr.setJc(jc);//设置表格位置
            tbl.setTblPr(tblPr);//设置表格设置
        }
        return tbl;
    }
    /**
     * 添加TableCell
     *
     * @param tableRow
     * @param content
     */
    public static void  addTableTc(Tr tableRow, String content, Integer width, boolean isBold, String fontSize, String fontColor, String backgroundcolor,STLineSpacingRule  sTLineSpacingRule) {
        Tc tc = factory.createTc();
        P p = factory.createP();
        R r = factory.createR();
        RPr rPr = factory.createRPr();
        Text text = factory.createText();
        //禁止行号(不设置没什么影响)
        BooleanDefaultTrue bCs = rPr.getBCs();
        if (bCs == null) {
            bCs = new BooleanDefaultTrue();
        }
        bCs.setVal(true);
        rPr.setBCs(bCs);

        //设置宽度
        if (width!=-1){
            setCellWidth(tc, width);
        }
        //生成段落添加到单元格中
        text.setValue(content);
        //设置字体颜色，加粗
        Docx4jUtil.setFontColor(rPr, isBold, fontColor);
        //设置字体
        Docx4jUtil.setFontFamily(rPr, "宋体","Times New Roman");
        //设置字体大小
        Docx4jUtil.setFontSize(rPr, fontSize);
        //将样式添加到段落中
        r.getContent().add(rPr);
        r.getContent().add(text);
        p.getContent().add(r);
        tc.getContent().add(p);
        //设置垂直居中
        setTcVAlign(tc, STVerticalJc.CENTER);
        //设置水平居中
        setTcJcAlign(tc, JcEnumeration.CENTER);
        //去除段后格式
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = factory.createPPr();
        }
        Docx4jUtil.setSpacing(pPr);
        p.setPPr(pPr);
        pPr.getSpacing().setLine(BigInteger.valueOf(360));
        pPr.getSpacing().setLineRule(sTLineSpacingRule);
        if (backgroundcolor != null && !"".equals(backgroundcolor)) {
            //设置背景颜色
            CTShd shd = new CTShd();
            shd.setVal(STShd.CLEAR);
            shd.setColor("auto");
            shd.setFill(backgroundcolor);
            tc.getTcPr().setShd(shd);
        }

        tableRow.getContent().add(tc);
    }

    /**
     * 添加单元格
     * @param tableRow 行
     * @param content 内容
     * @param width 宽度
     * @param isBold 是否加粗
     * @param fontSize 文字大小
     * @param fontColor 文字颜色
     * @param backgroundColor 背景颜色
     * @author xuy
     * @date 2023/2/16 10:30
     */
    public static void  addTableTc(Tr tableRow, String content, Integer width, boolean isBold, String fontSize, String fontColor, String backgroundColor) {
        Tc tc = factory.createTc();
        P p = factory.createP();//段落
        R r = factory.createR();//运行块对象，文本容器
        RPr rPr = factory.createRPr();//工厂配置对象
        Text text = factory.createText();
        //禁止行号(不设置没什么影响)
        BooleanDefaultTrue bCs = rPr.getBCs();//获取配置的 boolean 值，暂时不知道具体用意，只知道 new 的时候默认为 true
        if (bCs == null) {
            bCs = new BooleanDefaultTrue();
        }
        bCs.setVal(true);
        rPr.setBCs(bCs);

        //设置宽度
        if (width!=-1){
            setCellWidth(tc, width);
        }
        //生成段落添加到单元格中
        text.setValue(content);
        //设置字体颜色，加粗
        Docx4jUtil.setFontColor(rPr, isBold, fontColor);
        //设置字体
        Docx4jUtil.setFontFamily(rPr, "宋体","Times New Roman");
        //设置字体大小
        Docx4jUtil.setFontSize(rPr, fontSize);
        //将样式添加到段落中
        r.getContent().add(rPr);//设置文字样式
        r.getContent().add(text);//设置文字内容
        p.getContent().add(r);//将文字添加到段落中
        tc.getContent().add(p);//将段落添加到单元格中
        //设置垂直居中
        setTcVAlign(tc, STVerticalJc.CENTER);
        //设置水平居中
        setTcJcAlign(tc, JcEnumeration.CENTER);
        //去除段后格式
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = factory.createPPr();
        }
        Docx4jUtil.setSpacing(pPr);
        p.setPPr(pPr);

        if (backgroundColor != null && !"".equals(backgroundColor)) {
            //设置背景颜色
            CTShd shd = new CTShd();
            shd.setVal(STShd.CLEAR);
            shd.setColor("auto");
            shd.setFill(backgroundColor);
            tc.getTcPr().setShd(shd);
        }

        tableRow.getContent().add(tc);
    }
    /**
     * 添加TableCell
     *
     * @param tableRow
     * @param content
     */
    public static void  addTableTcSpacing(Tr tableRow, String content, Integer width, boolean isBold, String fontSize, String fontColor, String backgroundcolor) {
        Tc tc = factory.createTc();
        P p = factory.createP();
        R r = factory.createR();
        RPr rPr = factory.createRPr();
        Text text = factory.createText();
        //禁止行号(不设置没什么影响)
        BooleanDefaultTrue bCs = rPr.getBCs();
        if (bCs == null) {
            bCs = new BooleanDefaultTrue();
        }
        bCs.setVal(true);
        rPr.setBCs(bCs);

        //设置宽度
        if (width!=-1){
            setCellWidth(tc, width);
        }

        //生成段落添加到单元格中
        text.setValue(content);
        //设置字体颜色，加粗
        Docx4jUtil.setFontColor(rPr, isBold, fontColor);
        //设置字体
        Docx4jUtil.setFontFamily(rPr, "宋体","Times New Roman");
        //设置字体大小
        Docx4jUtil.setFontSize(rPr, fontSize);
        //将样式添加到段落中
        r.getContent().add(rPr);
        r.getContent().add(text);
        p.getContent().add(r);
        tc.getContent().add(p);
        //设置垂直居中
        setTcVAlign(tc, STVerticalJc.CENTER);
        //设置水平居中
        setTcJcAlign(tc, JcEnumeration.CENTER);
        //去除段后格式
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = factory.createPPr();
        }
        Docx4jUtil.setSpacingAuto(pPr);
        p.setPPr(pPr);

        if (backgroundcolor != null && !"".equals(backgroundcolor)) {
            //设置背景颜色
            CTShd shd = new CTShd();
            shd.setVal(STShd.CLEAR);
            shd.setColor("auto");
            shd.setFill(backgroundcolor);
            tc.getTcPr().setShd(shd);
        }

        tableRow.getContent().add(tc);
    }
    public static void addTableTc(Tr tableRow, String content, Integer width, boolean isBold, String fontSize, String fontColor, String backgroundcolor,String cnFont,String enFont,STVerticalJc stVerticalJc,JcEnumeration jcEnumeration) {
        Tc tc = factory.createTc();
        P p = factory.createP();
        R r = factory.createR();
        RPr rPr = factory.createRPr();
        Text text = factory.createText();
        //禁止行号(不设置没什么影响)
        BooleanDefaultTrue bCs = rPr.getBCs();
        if (bCs == null) {
            bCs = new BooleanDefaultTrue();
        }
        bCs.setVal(true);
        rPr.setBCs(bCs);

        //设置宽度
        if (width!=-1){
            setCellWidth(tc, width);
        }
        //生成段落添加到单元格中
        text.setValue(content);
        //设置字体颜色，加粗
        Docx4jUtil.setFontColor(rPr, isBold, fontColor);
        //设置字体
        //"宋体","Arial Narrow"
        Docx4jUtil.setFontFamily(rPr,cnFont,enFont );
        //设置字体大小
        Docx4jUtil.setFontSize(rPr, fontSize);
        //将样式添加到段落中
        r.getContent().add(rPr);

        r.getContent().add(text);
        p.getContent().add(r);
        tc.getContent().add(p);
        //设置垂直居中
        if(stVerticalJc == null){
            setTcVAlign(tc, STVerticalJc.CENTER);
        }else {
            setTcVAlign(tc, stVerticalJc);
        }
        //设置水平居中
        if(jcEnumeration == null){
            setTcJcAlign(tc, JcEnumeration.CENTER);
        }else{
            setTcJcAlign(tc, jcEnumeration);
        }
        //去除段后格式
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = factory.createPPr();
        }
        Docx4jUtil.setSpacing(pPr);
        p.setPPr(pPr);

        if (backgroundcolor != null && !"".equals(backgroundcolor)) {
            //设置背景颜色
            CTShd shd = new CTShd();
            shd.setVal(STShd.CLEAR);
            shd.setColor("auto");
            shd.setFill(backgroundcolor);
            tc.getTcPr().setShd(shd);
        }

        tableRow.getContent().add(tc);
    }

    /**
     * 添加TableCell
     *
     * @param tableRow
     * @param content
     */
    public static void addTableTc(Tr tableRow, String content, Integer width, boolean isBold, String fontSize, String fontColor, String backgroundcolor,int index) {
        Tc tc = factory.createTc();
        P p = factory.createP();
        R r = factory.createR();
        RPr rPr = factory.createRPr();
        Text text = factory.createText();
        //禁止行号(不设置没什么影响)
        BooleanDefaultTrue bCs = rPr.getBCs();
        if (bCs == null) {
            bCs = new BooleanDefaultTrue();
        }
        bCs.setVal(true);
        rPr.setBCs(bCs);

        //设置宽度
        if (width!=-1){
            setCellWidth(tc, width);
        }
        //生成段落添加到单元格中
        text.setValue(content);
        //设置字体颜色，加粗
        Docx4jUtil.setFontColor(rPr, isBold, fontColor);
        //设置字体
        Docx4jUtil.setFontFamily(rPr, "宋体","Times New Roman");
        //设置字体大小
        Docx4jUtil.setFontSize(rPr, fontSize);
        //将样式添加到段落中
        r.getContent().add(rPr);

        r.getContent().add(text);
        p.getContent().add(r);
        tc.getContent().add(p);
        //设置垂直居中
        setTcVAlign(tc, STVerticalJc.CENTER);
        //设置水平居中
        setTcJcAlign(tc, JcEnumeration.CENTER);
        //去除段后格式
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = factory.createPPr();
        }
        Docx4jUtil.setSpacing(pPr);
        p.setPPr(pPr);

        if (backgroundcolor != null && !"".equals(backgroundcolor)) {
            //设置背景颜色
            CTShd shd = new CTShd();
            shd.setVal(STShd.CLEAR);
            shd.setColor("auto");
            shd.setFill(backgroundcolor);
            tc.getTcPr().setShd(shd);
        }

        tableRow.getContent().add(index,tc);
    }

    /**
     * 添加TableCell
     *
     * @param tableRow
     * @param content
     */
    public static void addTableTcSplit(Tr tableRow, String content, Integer width, boolean isBold, String fontSize) {
        Tc tc = factory.createTc();
        P p = factory.createP();
        //设置宽度
        if (width!=-1){
            setCellWidth(tc, width);
        }
        //生成段落添加到单元格中
        String[] split = content.split("/");
        if (split!=null && split.length==2){
            for (int i=0; i<split.length; i++){
                R r = factory.createR();
                Text text = factory.createText();
                RPr rPr = factory.createRPr();
                Docx4jUtil.setFontSize(rPr, fontSize);  //字体大小
                Docx4jUtil.setFontFamily(rPr, "宋体","Times New Roman");  //字体
                if (i==0){
                    Docx4jUtil.setFontColor(rPr, isBold, "#ffc000");  //颜色
                }else if (i==1){
                    Docx4jUtil.setFontColor(rPr, isBold, "#ff0000");  //颜色
                }
                text.setValue(split[i]);
                r.getContent().add(text);
                r.setRPr(rPr);
                p.getContent().add(r);
                if (i!=split.length-1){
                    Text t = factory.createText();
                    R rR = factory.createR();
                    RPr rPrR = factory.createRPr();
                    Docx4jUtil.setFontColor(rPrR, false, "black");
                    rR.setRPr(rPrR);
                    t.setValue("/");
                    rR.getContent().add(t);
                    p.getContent().add(rR);
                }
            }
        }else {
            R r = factory.createR();
            RPr rPr = factory.createRPr();
            Text text = factory.createText();
            //生成段落添加到单元格中
            text.setValue(content);
            //设置字体颜色，加粗
            Docx4jUtil.setFontColor(rPr, isBold, "black");
            //设置字体
            Docx4jUtil.setFontFamily(rPr, "宋体","Times New Roman");
            //设置字体大小
            Docx4jUtil.setFontSize(rPr, fontSize);
            //将样式添加到段落中
            r.getContent().add(rPr);

            r.getContent().add(text);
            p.getContent().add(r);
        }
        tc.getContent().add(p);
        //设置垂直居中
        setTcVAlign(tc, STVerticalJc.CENTER);
        //设置水平居中
        setTcJcAlign(tc, JcEnumeration.CENTER);
        //去除段后格式
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = factory.createPPr();
        }
        Docx4jUtil.setSpacing(pPr);
        p.setPPr(pPr);

        tableRow.getContent().add(tc);
    }

    /**
     * Pagecontent4添加表格最后一行数据说明
     * @param tc
     * @param content
     * @param width
     * @param isBold
     * @param fontSize
     * @param fontColor
     * @param backgroundcolor
     */
    public static void addP2Tc(Tc tc, String content, int width, boolean isBold, String fontSize, String fontColor, String backgroundcolor, boolean flag) {
        P p = factory.createP();
        R r = factory.createR();
        RPr rPr = factory.createRPr();
        Text text = factory.createText();
        //禁止行号(不设置没什么影响)
        BooleanDefaultTrue bCs = rPr.getBCs();
        if (bCs == null) {
            bCs = new BooleanDefaultTrue();
        }
        bCs.setVal(true);
        rPr.setBCs(bCs);

        //设置宽度
        if (width!=-1){
            setCellWidth(tc, width);
        }

        //生成段落添加到单元格中
        text.setValue(content);
        //设置字体颜色，加粗
        Docx4jUtil.setFontColor(rPr, isBold, fontColor);
        //设置字体
        Docx4jUtil.setFontFamily(rPr, "宋体","Times New Roman");
        //设置字体大小
        Docx4jUtil.setFontSize(rPr, fontSize);
        //将样式添加到段落中
        r.getContent().add(rPr);

        r.getContent().add(text);
        p.getContent().add(r);
        tc.getContent().add(p);
        //设置垂直居中
        setTcVAlign(tc, STVerticalJc.CENTER);
        PPr pPr = p.getPPr();
        if (pPr==null){
            pPr = factory.createPPr();
        }
        if (flag){
            //缩进2字符
            PPrBase.Ind ind = pPr.getInd();
            if (ind==null){
                ind = factory.createPPrBaseInd();
            }
            ind.setFirstLineChars(BigInteger.valueOf(200));
            pPr.setInd(ind);
        }
        //设置水平居中
//        setTcJcAlign(tc, JcEnumeration.CENTER);
        //去除段后格式
        Docx4jUtil.setSpacing(pPr);
        if (backgroundcolor != null && !"".equals(backgroundcolor)) {
            Highlight highlight=new Highlight();
            highlight.setVal(backgroundcolor);
            rPr.setHighlight(highlight);
//            Docx4jUtil.setParagraphShdStyle(pPr, STShd.SOLID, backgroundcolor);
        }
        p.setPPr(pPr);


//        if (backgroundcolor != null && !"".equals(backgroundcolor)) {
//            //设置背景颜色
//            CTShd shd = new CTShd();
//            shd.setVal(STShd.CLEAR);
//            shd.setColor("auto");
//            shd.setFill(backgroundcolor);
//            tc.getTcPr().setShd(shd);
//        }

    }

    /**
     * 添加有序号的tc
     * @param tableRow
     * @param ps
     * @param width
     * @param isBold
     * @param fontSize
     * @param fontColor
     * @param backgroundcolor
     */
    public static void addTableTcNumbering(Tr tableRow, P[] ps, int width, boolean isBold, String fontSize, String fontColor, String backgroundcolor) {
        Tc tc = factory.createTc();

        if (ps!=null){
            for (P p : ps) {
                //设置宽度
                setCellWidth(tc, width);
                //设置垂直居中
                setTcVAlign(tc, STVerticalJc.CENTER);
                //设置水平居中
//                setTcJcAlign(tc, JcEnumeration.CENTER);
                tc.getContent().add(p);

                if (backgroundcolor != null && !"".equals(backgroundcolor)) {
                    //设置背景颜色
                    CTShd shd = new CTShd();
                    shd.setVal(STShd.CLEAR);
                    shd.setColor("auto");
                    shd.setFill(backgroundcolor);
                    tc.getTcPr().setShd(shd);
                }
            }
        }
        tableRow.getContent().add(tc);
    }

    /**
     * 添加表格边框样式
     * @param table 要修改的表格
     * @param borderColor 要修改的颜色
     * @param borderSize 要修改的粗细大小
     * @author xuy
     * @date 2023/2/16 10:25
     */
    public static void addBorders(Tbl table,String borderColor, String borderSize) {
        table.setTblPr(new TblPr());//设置 tblPr 属性的值
        CTBorder border = new CTBorder();//边框线对象吧
        border.setColor(borderColor);
        border.setSz(new BigInteger(borderSize));//边框大小
        border.setSpace(new BigInteger("0"));//间隔
        border.setVal(STBorder.SINGLE);//边框的类型，单线双线啥的

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }

    /**
     * @Description: 跨行合并
     */
    public static void mergeCellsVertically(Tbl tbl, int col, int fromRow, int toRow) {
        if (col < 0 || fromRow < 0 || toRow < 0) {
            return;
        }
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            Tc tc = getTc(tbl, rowIndex, col);
            if (tc == null) {
                break;
            }
            TcPr tcPr = getTcPr(tc);
            TcPrInner.VMerge vMerge = tcPr.getVMerge();
            if (vMerge == null) {
                vMerge = new TcPrInner.VMerge();
                tcPr.setVMerge(vMerge);
            }
            if (rowIndex == fromRow) {
                vMerge.setVal("restart");
            } else {
                vMerge.setVal("continue");
            }
        }
    }

    /**
     * @Description: 跨列合并
     */
    public static void mergeCellsHorizontal(Tbl tbl, int row, int fromCell, int toCell) {
        if (row < 0 || fromCell < 0 || toCell < 0) {
            return;
        }
        List<Tr> trList = getTblAllTr(tbl);
        if (row > trList.size()) {
            return;
        }
        Tr tr = trList.get(row);
        List<Tc> tcList = getTrAllCell(tr);
        for (int cellIndex = fromCell, len = Math.min(tcList.size() - 1, toCell); cellIndex <= len; cellIndex++) {
            Tc tc = tcList.get(cellIndex);
            TcPr tcPr = getTcPr(tc);
            TcPrInner.HMerge hMerge = tcPr.getHMerge();
            if (hMerge == null) {
                hMerge = new TcPrInner.HMerge();
                tcPr.setHMerge(hMerge);
            }
            if (cellIndex == fromCell) {
                hMerge.setVal("restart");
            } else {
                hMerge.setVal("continue");
            }
        }
    }

    private static TblPr getTblPr(Tbl tbl) {
        TblPr tblPr = tbl.getTblPr();
        if (tblPr == null) {
            tblPr = new TblPr();
            tbl.setTblPr(tblPr);
        }
        return tblPr;
    }
    /**
     * @Description: 设置表格总宽度
     */
    public static void setTableWidth(Tbl tbl, String width) {
        if (StringUtils.isNotBlank(width)) {
            TblPr tblPr = getTblPr(tbl);
            TblWidth tblW = tblPr.getTblW();
            if (tblW == null) {
                tblW = new TblWidth();
                tblPr.setTblW(tblW);
            }
            tblW.setW(new BigInteger(width));
            tblW.setType(TblWidth.TYPE_DXA);
        }
    }

    /**
     * 本方法创建一个单元格属性集对象和一个表格宽度对象. 将给定的宽度设置到宽度对象然后将其添加到
     * 属性集对象. 最后将属性集对象设置到单元格中.
     *
     * width:单位为缇，1440缇为2.54cm
     */
    /**
     * 本方法创建一个单元格属性集对象和一个表格宽度对象. 将给定的宽度设置到宽度对象然后将其添加到
     * 属性集对象. 最后将属性集对象设置到单元格中.
     * @param tableCell 要操作的单元格
     * @param width 单位为缇，1440缇为2.54cm
     * @author xuy
     * @date 2023/2/16 10:42
     */
    public static void setCellWidth(Tc tableCell, int width) {
        TcPr tableCellProperties = new TcPr();//单元格配置对象
        TblWidth tableWidth = new TblWidth();
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }
    private static TcPr getTcPr(Tc tc) {
        TcPr tcPr = tc.getTcPr();
        if (tcPr == null) {
            tcPr = new TcPr();
            tc.setTcPr(tcPr);
        }
        return tcPr;
    }


    /**
     * 设置单元格垂直居中
     * @param tc 需要修改的单元格
     * @param vAlignType 对齐方式
     * @author xuy
     * @date 2023/2/16 13:26
     */
    private static void setTcVAlign(Tc tc, STVerticalJc vAlignType) {
        if (vAlignType != null) {
            TcPr tcPr = getTcPr(tc);//获取单元格现有配置
            CTVerticalJc vAlign = new CTVerticalJc();//单元格对齐对象
            vAlign.setVal(vAlignType);
            tcPr.setVAlign(vAlign);
        }
    }

    /**
     * 设置单元格水平居中
     * @param tc 需要修改的单元格
     * @param jcType 对齐方式
     * @author xuy
     * @date 2023/2/16 13:29
     */
    private static void setTcJcAlign(Tc tc, JcEnumeration jcType) {
        if (jcType != null) {
            List<P> pList = getTcAllP(tc);
            for (P p : pList) {
                setParaJcAlign(p, jcType);
            }
        }
    }

    private static List<P> getTcAllP(Tc tc) {
        List<Object> objList = getAllElementFromObject(tc, P.class);
        List<P> pList = new ArrayList<P>();
        if (objList == null) {
            return pList;
        }
        for (Object obj : objList) {
            if (obj instanceof P) {
                P p = (P) obj;
                pList.add(p);
            }
        }
        return pList;
    }

    /**
     * @Description: 得到指定类型的元素
     */
    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
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

    /**
     * @Description: 设置段落水平对齐方式
     */
    private static void setParaJcAlign(P paragraph, JcEnumeration hAlign) {
        if (hAlign != null) {
            PPr pprop = paragraph.getPPr();
            if (pprop == null) {
                pprop = new PPr();
                paragraph.setPPr(pprop);
            }
            Jc align = new Jc();
            align.setVal(hAlign);
            pprop.setJc(align);
        }
    }


    /**
     * @Description: 得到表格所有的行
     */
    private static List<Tr> getTblAllTr(Tbl tbl) {
        List<Object> objList = getAllElementFromObject(tbl, Tr.class);
        List<Tr> trList = new ArrayList<Tr>();
        if (objList == null) {
            return trList;
        }
        for (Object obj : objList) {
            if (obj instanceof Tr) {
                Tr tr = (Tr) obj;
                trList.add(tr);
            }
        }
        return trList;

    }

    /**
     * @Description: 得到指定位置的单元格
     */
    private static Tc getTc(Tbl tbl, int row, int cell) {
        if (row < 0 || cell < 0) {
            return null;
        }
        List<Tr> trList = getTblAllTr(tbl);
        if (row >= trList.size()) {
            return null;
        }
        List<Tc> tcList = getTrAllCell(trList.get(row));
        if (cell >= tcList.size()) {
            return null;
        }
        return tcList.get(cell);
    }

    /**
     * @Description: 得到所有表格
     */
    private static List<Tbl> getAllTbl(WordprocessingMLPackage wordMLPackage) {
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
     * @Description: 获取所有的单元格
     */
    private static List<Tc> getTrAllCell(Tr tr) {
        List<Object> objList = getAllElementFromObject(tr, Tc.class);
        List<Tc> tcList = new ArrayList<Tc>();
        if (objList == null) {
            return tcList;
        }
        for (Object tcObj : objList) {
            if (tcObj instanceof Tc) {
                Tc objTc = (Tc) tcObj;
                tcList.add(objTc);
            }
        }
        return tcList;
    }
}
