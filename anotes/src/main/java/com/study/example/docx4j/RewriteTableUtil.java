package com.study.example.docx4j;

import org.apache.commons.lang3.StringUtils;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格操作工具类
 * @author xuy
 * @date 2023/2/17 10:16
 */
public class RewriteTableUtil {
    private static final ObjectFactory factory = new ObjectFactory();//创建一个新的对象工厂，该工厂可用于为包创建架构派生类的新实例：org.docx4j.wml

    /**
     * 创建一个表格,当表格中没有添加 tr tc 时，不会显示，像一个占位符
     * @param borderColor 边框颜色
     * @param borderSize 边框粗细
     * @param jcEnumeration 表格位置的枚举
     * @return org.docx4j.wml.Tbl
     * @author xuy
     * @date 2023/2/16 10:23
     */
    public static Tbl createTable(String borderColor, String borderSize, JcEnumeration jcEnumeration){
        Tbl tbl = factory.createTbl();//创建实例 Tbl
        //给table添加边框
        addBorders(tbl, borderColor, borderSize);
        if(jcEnumeration!=null) {
            //单元格居中对齐
            Jc jc = new Jc();//表格的位置的对象
            jc.setVal(jcEnumeration);//表格位置
            TblPr tblPr = tbl.getTblPr();//获取表格设置
            if (tblPr == null) tblPr = new TblPr();
            tblPr.setJc(jc);//设置表格位置
            tbl.setTblPr(tblPr);//设置表格设置
        }
        return tbl;
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

//        table.setTblPr(new TblPr());//设置 tblPr 属性的值
//        table.getTblPr().setTblBorders(borders);
        TblPr tblPr = table.getTblPr();
        if (tblPr == null) tblPr = new TblPr();
        tblPr.setTblBorders(borders);
        table.setTblPr(tblPr);
    }

    /**
     * 添加单元格
     * @param tableRow 行
     * @param content 内容
     * @param width 单元格宽度
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
            setCellWidth(tc, width);//没有设置的时候
        }
        //生成段落添加到单元格中
        text.setValue(content);
        //设置字体颜色，加粗
        setFontColor(rPr, isBold, fontColor);
        //设置字体
        setFontFamily(rPr, "宋体","Times New Roman");
        //设置字体大小
        setFontSize(rPr, fontSize);
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
            CTShd shd = new CTShd();//单元格背景对象
            shd.setVal(STShd.CLEAR);//单元格模式，eg：田字格等
            shd.setColor("auto");
            shd.setFill(backgroundColor);
            TcPr tcPr = tc.getTcPr();
            if( tcPr == null) tcPr = new TcPr();
            tcPr.setShd(shd);
            tc.setTcPr(tcPr);
        }

        tableRow.getContent().add(tc);
    }

    /**
     * 设置字体颜色，加粗
     * @param rPr 工厂配置
     * @param isBold 是否加粗
     * @param color 颜色
     * @author xuy
     * @date 2023/2/16 10:46
     */
    public static void setFontColor(RPr rPr, boolean isBold, String color) {
        if (StringUtils.isNotBlank(color)) {
            Color c = new Color();
            c.setVal(color);
            rPr.setColor(c);
        }
        if (isBold) {
            //设置加粗
            BooleanDefaultTrue booleanDefaultTrue = factory.createBooleanDefaultTrue();
            booleanDefaultTrue.setVal(Boolean.TRUE);
            rPr.setB(booleanDefaultTrue);//设置粗体
        }
    }

    /**
     * 设置字体
     * @param runProperties 工厂配置
     * @param cnFontFamily 中文字体
     * @param enFontFamily 英文字体
     * @author xuy
     * @date 2023/2/16 10:48
     */
    public static void setFontFamily(RPr runProperties, String cnFontFamily,String enFontFamily) {
        if (StringUtils.isNotBlank(cnFontFamily) || StringUtils.isNotBlank(enFontFamily)) {
            RFonts rf = runProperties.getRFonts();//从工厂配置中获取字体对象的配置
            if (rf == null) {
                rf = new RFonts();//没有创建字体配置对象
            }
            if (cnFontFamily != null) {
                rf.setEastAsia(cnFontFamily);
            }
            if (enFontFamily != null) {
                rf.setAscii(enFontFamily);
            }
            runProperties.setRFonts(rf);
        }
    }

    /**
     * 设置字体大小
     * @param rPr 工厂配置对象
     * @param size size*2 为真正的字体
     * @author xuy
     * @date 2023/2/16 13:18
     */
    public static void setFontSize(RPr rPr, String size) {
        if (StringUtils.isNotBlank(size)) {
            HpsMeasure fontSize = new HpsMeasure();//某个大小的对象
            fontSize.setVal(new BigInteger(size));
            rPr.setSzCs(fontSize);//猜测单元格大小配置
            rPr.setSz(fontSize);//猜测表格配置
        }
    }
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
        TblWidth tableWidth = new TblWidth();//表格宽度对象
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }

    /**
     * 合并单元格，跨列合并
     * @param tbl 要操作的表格
     * @param row 要操作的行，索引从0开始
     * @param fromCell 起始列，索引从0开始
     * @param toCell 终止列，索引从0开始
     * @return void
     * @author xuy
     * @date 2023/2/16 15:24
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
//            TcPr tcPr = getTcPr(tc);
            TcPr tcPr = tc.getTcPr();
            TcPrInner.HMerge hMerge = tcPr.getHMerge();
            if (hMerge == null) {
                hMerge = new TcPrInner.HMerge();
                tcPr.setHMerge(hMerge);
            }
            if (cellIndex == fromCell) {
                hMerge.setVal("restart");//感觉这个是起始标志
            } else {
                hMerge.setVal("continue");//这个是后续要合并的标志
            }
        }
    }

    /**
     * 合并单元格，跨行合并
     * @param tbl 要操作的表格
     * @param col 要操作的列，索引从0开始
     * @param fromRow 起始行，索引从0开始
     * @param toRow 终止行，索引从0开始
     * @return void
     * @author xuy
     * @date 2023/2/16 16:24
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
//            TcPr tcPr = getTcPr(tc);
            TcPr tcPr = tc.getTcPr();
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
     * 获取表格所有 tr
     * @param tbl 表格
     * @return java.util.List<org.docx4j.wml.Tr>
     * @author xuy
     * @date 2023/2/16 15:50
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
     * 获取指定元素的类型列表
     * @param obj
     * @param toSearch
     * @return java.util.List<java.lang.Object>
     * @author xuy
     * @date 2023/2/16 15:48
     */
    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {//内容访问器，应该是所有docx4j的父类，暂定 table 系列，tr 继承了，tc 没有继承
            List<?> children = ((ContentAccessor) obj).getContent();//获取表格内容，也就是 tr tc
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }

    /**
     * 获取一行内的所有单元格
     * @param tr
     * @return java.util.List<org.docx4j.wml.Tc>
     * @author xuy
     * @date 2023/2/16 15:52
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
//    /**
//     * 获取指定位置单元格的配置
//     * @param tc
//     * @return org.docx4j.wml.TcPr
//     * @author xuy
//     * @date 2023/2/16 15:33
//     */
//    private static TcPr getTcPr(Tc tc) {
//        TcPr tcPr = tc.getTcPr();
//        if (tcPr == null) {
//            tcPr = new TcPr();
//            tc.setTcPr(tcPr);
//        }
//        return tcPr;
//    }
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
     * 设置单元格垂直居中
     * @param tc 需要修改的单元格
     * @param vAlignType 对齐方式
     * @author xuy
     * @date 2023/2/16 13:26
     */
    private static void setTcVAlign(Tc tc, STVerticalJc vAlignType) {
        if (vAlignType != null) {
//            TcPr tcPr = getTcPr(tc);//获取单元格现有配置
            TcPr tcPr = tc.getTcPr();
            CTVerticalJc vAlign = new CTVerticalJc();//单元格对齐对象
            vAlign.setVal(vAlignType);
            tcPr.setVAlign(vAlign);
            tc.setTcPr(tcPr);
        }
    }

    /**
     * 设置单元格水平居中,有点想不通 为啥并没有官方文档 气死
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
}
