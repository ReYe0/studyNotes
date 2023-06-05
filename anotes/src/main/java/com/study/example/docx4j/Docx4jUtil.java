package com.study.example.docx4j;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.properties.table.tr.TrHeight;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class Docx4jUtil {
    private static final ObjectFactory objectFactory = new ObjectFactory();

    /**
     * 插入下一页
     *
     */
    public static void addNextPage(WordprocessingMLPackage wpMLPackage) {
        Br br = new Br();
        br.setType(STBrType.PAGE);
        P p = objectFactory.createP();
        p.getContent().add(br);
//        wpMLPackage.getMainDocumentPart().addObject(p);
        wpMLPackage.getMainDocumentPart().getJaxbElement().getBody().getContent().add(p);
    }

    /**
     * 插入下一节
     *
     * @param wpMLPackage
     */
    public static void addNextSection(WordprocessingMLPackage wpMLPackage) {
        P para = objectFactory.createP();
        SectPr sectPr = objectFactory.createSectPr();
        PPr pPr = objectFactory.createPPr();
        SectPr.Type sectPrType = objectFactory.createSectPrType();

        sectPrType.setVal("nextPage");
        sectPr.setType(sectPrType);
        pPr.setSectPr(sectPr);
        para.setPPr(pPr);
        wpMLPackage.getMainDocumentPart().addObject(para);
    }

    /**
     * 添加换行符
     *
     * @param wpMLPackage
     * @param brNum       换行数量
     */
    public static void addBr(WordprocessingMLPackage wpMLPackage, int brNum) {
        P para = objectFactory.createP();
        R run = objectFactory.createR();
        Br br = new Br();
        //循环插入
        for (int i = 0; i < brNum; i++) {
            run.getContent().add(br);
        }
        para.getContent().add(run);
        wpMLPackage.getMainDocumentPart().addObject(para);
    }

    /**
     * 表格添加文字
     *
     * @param wpMLPackage
     * @param content
     */
    public static P addTableText(WordprocessingMLPackage wpMLPackage, String content) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = objectFactory.createPPr();
        }
        //缩进2字符
        PPrBase.Ind ind = pPr.getInd();
        if (ind == null) {
            ind = objectFactory.createPPrBaseInd();
        }
        ind.setFirstLineChars(BigInteger.valueOf(200));
        pPr.setInd(ind);
        //设置行距1.5倍
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing == null) {
            spacing = objectFactory.createPPrBaseSpacing();
        }
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(360));
        //段前段后
//        spacing.setBeforeLines(BigInteger.valueOf(50));
//        spacing.setAfterLines(BigInteger.valueOf(50));

        Text text = objectFactory.createText();
        text.setValue(content);
        r.getContent().add(text);

        RPr rPr = objectFactory.createRPr();
        setFontFamily(rPr, "宋体", "Arial Narrow");
        r.setRPr(rPr);
        pPr.setSpacing(spacing);
        p.getContent().add(r);
        p.setPPr(pPr);
        return p;
    }

    /**
     * 添加正文段落
     *
     * @param wpMLPackage
     * @param content
     */
    public static void addParagraph(WordprocessingMLPackage wpMLPackage, String content) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = objectFactory.createPPr();
        }
        //缩进2字符
        PPrBase.Ind ind = pPr.getInd();
        if (ind == null) {
            ind = objectFactory.createPPrBaseInd();
        }
        ind.setFirstLineChars(BigInteger.valueOf(200));
        pPr.setInd(ind);
        //设置行距1.5倍
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing == null) {
            spacing = objectFactory.createPPrBaseSpacing();
        }
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(360));
        //段前段后
//        spacing.setBeforeLines(BigInteger.valueOf(50));
//        spacing.setAfterLines(BigInteger.valueOf(50));

        Text text = objectFactory.createText();
        text.setValue(content);
        r.getContent().add(text);

        RPr rPr = objectFactory.createRPr();
        setFontFamily(rPr, "宋体", "Times New Roman");
        r.setRPr(rPr);
        pPr.setSpacing(spacing);
        p.getContent().add(r);
        p.setPPr(pPr);
        wpMLPackage.getMainDocumentPart().addObject(p);
    }
    /**
     * 添加正文段落
     *
     * @param content
     */
    public static P addParagraph(String content) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = objectFactory.createPPr();
        }
        //缩进2字符
        PPrBase.Ind ind = pPr.getInd();
        if (ind == null) {
            ind = objectFactory.createPPrBaseInd();
        }
        ind.setFirstLineChars(BigInteger.valueOf(200));
        pPr.setInd(ind);
        //设置行距1.5倍
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing == null) {
            spacing = objectFactory.createPPrBaseSpacing();
        }
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(360));
        //段前段后
//        spacing.setBeforeLines(BigInteger.valueOf(50));
//        spacing.setAfterLines(BigInteger.valueOf(50));

        Text text = objectFactory.createText();
        text.setValue(content);
        r.getContent().add(text);

        RPr rPr = objectFactory.createRPr();
        setFontFamily(rPr, "宋体", "Times New Roman");
        r.setRPr(rPr);
        pPr.setSpacing(spacing);
        p.getContent().add(r);
        p.setPPr(pPr);
        return p;
    }

    /**
     * 添加正文段落
     *
     * @param content
     */
    public static P addParagraph(String content, String cnFont, String enFont) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = p.getPPr();
        if (pPr == null) {
            pPr = objectFactory.createPPr();
        }
        //缩进2字符
        PPrBase.Ind ind = pPr.getInd();
        if (ind == null) {
            ind = objectFactory.createPPrBaseInd();
        }
        ind.setFirstLineChars(BigInteger.valueOf(200));
        pPr.setInd(ind);
        //设置行距1.5倍
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing == null) {
            spacing = objectFactory.createPPrBaseSpacing();
        }
        spacing.setLineRule(STLineSpacingRule.EXACT);
        spacing.setLine(BigInteger.valueOf(400));
        //段前段后
//        spacing.setBeforeLines(BigInteger.valueOf(50));
//        spacing.setAfterLines(BigInteger.valueOf(50));

        Text text = objectFactory.createText();
        text.setValue(content);
        r.getContent().add(text);

        RPr rPr = objectFactory.createRPr();
        setFontFamily(rPr, cnFont, enFont);
        r.setRPr(rPr);
        pPr.setSpacing(spacing);
        p.getContent().add(r);
        p.setPPr(pPr);
        return p;
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
     * 设置字体
     *
     * @param rPr
     * @param font
     */
    public static void setFont(RPr rPr, String font) {
        if (StringUtils.isNotBlank(font)) {
            RFonts rFonts = new RFonts();
            rFonts.setEastAsia(font);
            rPr.setRFonts(rFonts);
        }
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
            BooleanDefaultTrue booleanDefaultTrue = objectFactory.createBooleanDefaultTrue();
            booleanDefaultTrue.setVal(Boolean.TRUE);
            rPr.setB(booleanDefaultTrue);//设置粗体
        }
    }


    /**
     * 添加图片到文档中
     *
     * @param wpMLPackage
     * @param bytes
     * @throws Exception
     */
    public static void addImageToPackage(WordprocessingMLPackage wpMLPackage, byte[] bytes) {
        BinaryPartAbstractImage imagePart = null;
        Inline imageInline = null;
        if (bytes.length < 1) {
            return;
        }
        try {
            imagePart = BinaryPartAbstractImage.createImagePart(wpMLPackage, bytes);

            int docPrId = 1;
            int cNvPrId = 2;
            imageInline = imagePart.createImageInline("Filename hint", "Alternative", docPrId, cNvPrId, false);
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getClassName(), e);
        }
        P p = addInlineImageToParagraph(imageInline);

        wpMLPackage.getMainDocumentPart().addObject(p);
    }

    public static P createImageP(WordprocessingMLPackage wpMLPackage, byte[] bytes) {
        BinaryPartAbstractImage imagePart = null;
        Inline imageInline = null;
        if (bytes.length < 1) {
            return null;
        }
        try {
            imagePart = BinaryPartAbstractImage.createImagePart(wpMLPackage, bytes);

            int docPrId = 1;
            int cNvPrId = 2;
            imageInline = imagePart.createImageInline("Filename hint", "Alternative", docPrId, cNvPrId, false, 6500);
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getClassName(), e);
        }
        P p = addInlineImageToParagraph(imageInline);
        return p;
    }

    public static P createImageP(WordprocessingMLPackage wpMLPackage, byte[] bytes, long cx, long cy) {
        BinaryPartAbstractImage imagePart = null;
        Inline imageInline = null;
        if (bytes.length < 1) {
            return null;
        }
        try {
            imagePart = BinaryPartAbstractImage.createImagePart(wpMLPackage, bytes);

            int docPrId = 1;
            int cNvPrId = 2;
            //lcm = 360000emu
            imageInline = imagePart.createImageInline("Filename hint", "Alternative", docPrId, cNvPrId, cx, cy, false);
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getClassName(), e);
        }
        P p = addInlineImageToParagraph(imageInline);
        return p;
    }


    //设置新的newCx newCy
    public static Map<String, Long> dealCxy(Long cx, Long cy, Long newCx, Long newCy) {
        Map<String, Long> map = new HashMap<>();
        Long setCx;
        Long setCy;
        if (newCx > cx) {
            if (newCy <= cy) {
                setCx = cx;
                setCy = newCy / (newCx / cx);
            } else {
                if ((newCx / cx) > (newCy / cy)) {
                    setCx = cx;
                    setCy = newCy / (newCx / cx);
                } else {
                    setCy = cy;
                    setCx = newCx / (newCy / cy);
                }
            }
        } else {   // newCx < cx
            if (newCy > cy) {
                setCx = cx;
                setCy = newCy * (cx / newCx);
            } else {
                if ((cx / newCx) > (cy / newCy)) {
                    setCx = cx;
                    setCy = newCy * (cx / newCx);
                } else {
                    setCy = cy;
                    setCx = newCy * (cy / newCy);
                }
            }
        }
        map.put("setCx", setCx);
        map.put("setCy", setCy);
        return map;

        /*Long cx = imageInline.getExtent().getCx();
        Long cy = imageInline.getExtent().getCy();
        Map<String, Long> map = Docx4jUtil.dealCxy(cx, cy, 5900l, 5900l/cx/cy);
        imageInline.getExtent().setCx(map.get("setCx"));
        imageInline.getExtent().setCy(map.get("setCy"));*/
    }
    //文字换行


    /**
     * 添加内连对象到Paragraph中
     *
     * @param inline
     * @return
     */
    public static P addInlineImageToParagraph(Inline inline) {
        //添加内联对象到一个段落中
//        ObjectFactory objectFactory = new ObjectFactory();
        P p = objectFactory.createP();
        R run = objectFactory.createR();
        p.getContent().add(run);
        PPr pPr = objectFactory.createPPr();
        Jc jc = pPr.getJc();
        if (jc == null) {
            jc = new Jc();
        }
        //设置居中
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);
        p.setPPr(pPr);
        Drawing drawing = objectFactory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return p;
    }

    /**
     * 转换图片为byte数组
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] convertImageToByteArray(File file) {
        InputStream is = null;
        if (file == null) {
            return null;
        }
        long length = file.length();
        byte[] bytes = new byte[(int) length];
        try {
            if (!file.exists()) {
                return bytes;
            }
            is = new FileInputStream(file);

            if (length > Integer.MAX_VALUE) {
                log.warn(Thread.currentThread().getStackTrace()[1].getClassName(), "File too large!!");
            }

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            //确认所有的字节都被读取
            if (offset < bytes.length) {
                log.warn(Thread.currentThread().getStackTrace()[1].getClassName(), "Could not completely read file" + file.getName());
            }
            is.close();
        } catch (Exception e) {
            log.error(Thread.currentThread().getStackTrace()[1].getClassName(), e);
        }

        return bytes;
    }

    /**
     * 添加图片标题
     *
     * @param wpMLPackage
     * @param title
     */
    public static void addTableTitle(WordprocessingMLPackage wpMLPackage, String title) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = objectFactory.createPPr();
        Jc jc = pPr.getJc();
        if (jc == null) {
            jc = new Jc();
        }
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);

        Text text = objectFactory.createText();
        text.setValue(title);
        r.getContent().add(text);

        p.getContent().add(r);
        p.setPPr(pPr);
        wpMLPackage.getMainDocumentPart().addObject(p);
    }

    public static P addTableTitle(String title, String cnFont, String enFont) {
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = objectFactory.createPPr();
        Jc jc = pPr.getJc();
        if (jc == null) {
            jc = new Jc();
        }
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);

        Text text = objectFactory.createText();
        text.setValue(title);
        r.getContent().add(text);

        if (cnFont != null && enFont != null) {
            RPr rPr = objectFactory.createRPr();
            setFontFamily(rPr, cnFont, enFont);
            r.setRPr(rPr);
        }

        p.getContent().add(r);
        p.setPPr(pPr);
        return p;
    }

    public static P addTableTitle(String title) {
        return addTableTitle(title, null, null);
    }

    /**
     * 设置目录
     *
     * @return
     */
    public static void setTocStyle(WordprocessingMLPackage wpMLPackage, String styleId, String colorStr) {
        StyleDefinitionsPart sdp = wpMLPackage.getMainDocumentPart().getStyleDefinitionsPart();
        Style style = sdp.getStyleById(styleId);

        if (style != null) {
            PPr pPr = style.getPPr();
            if (pPr == null) {
                pPr = objectFactory.createPPr();
            }
            setSpacing(pPr);
            ParaRPr rPr = pPr.getRPr();
            if (rPr == null) {
                rPr = objectFactory.createParaRPr();
            }
            RFonts rf = rPr.getRFonts();
            if (rf == null) {
                rf = new RFonts();
            }
            //字体
            rf.setEastAsia("宋体");
            rf.setAscii("Times New Roman");
            //颜色
            Color color = new Color();
            color.setVal(colorStr);
            //设置加粗
            BooleanDefaultTrue booleanDefaultTrue = objectFactory.createBooleanDefaultTrue();
            booleanDefaultTrue.setVal(Boolean.TRUE);
            rPr.setB(booleanDefaultTrue);
            //设置行距1.5倍
            PPrBase.Spacing spacing = pPr.getSpacing();
            if (spacing == null) {
                spacing = objectFactory.createPPrBaseSpacing();
            }
            spacing.setLineRule(STLineSpacingRule.AUTO);
            spacing.setLine(BigInteger.valueOf(800));


            rPr.setColor(color);
            rPr.setRFonts(rf);
            pPr.setRPr(rPr);
            style.setPPr(pPr);
        }
    }

    /**
     * 设置标题格式
     *
     * @param wpMLPackage
     * @param styleId
     * @param colorStr
     */
    public static void setTitleStyle(WordprocessingMLPackage wpMLPackage, String styleId, String colorStr) {
        StyleDefinitionsPart sdp = wpMLPackage.getMainDocumentPart().getStyleDefinitionsPart();
        Style style = sdp.getStyleById(styleId);
        if (style != null) {
            RPr rPr = style.getRPr();
            if (rPr == null) {
                rPr = objectFactory.createRPr();
            }
            setFontColor(rPr, true, colorStr);
            PPr pPr = style.getPPr();
            if (pPr == null) {
                pPr = objectFactory.createPPr();
            }
            style.setPPr(pPr);
            style.setRPr(rPr);
        }
    }

    /**
     * 运达大标题格式
     *
     * @param wpMLPackage
     * @param styleId
     * @param colorStr
     */
    public static void setYunDaTitleStyle(WordprocessingMLPackage wpMLPackage, String styleId, String colorStr, String size) {
        StyleDefinitionsPart sdp = wpMLPackage.getMainDocumentPart().getStyleDefinitionsPart();
        Style style = sdp.getStyleById(styleId);
        if (style != null) {
            RPr rPr = style.getRPr();
            if (rPr == null) {
                rPr = objectFactory.createRPr();
            }
            setFontColor(rPr, true, colorStr);
            setFontFamily(rPr, "宋体", "Times New Roman");
            setFontSize(rPr, size);
            PPr pPr = style.getPPr();
            if (pPr == null) {
                pPr = objectFactory.createPPr();
            }
            //字体(不起作用)
//            ParaRPr pRpr = pPr.getRPr();
//            if (pRpr==null){
//                pRpr = objectFactory.createParaRPr();
//            }

//            RFonts rFonts = new RFonts();
//            rFonts.setAscii("Times New Roman");
//            rFonts.setEastAsia("宋体");
//            pRpr.setRFonts(rFonts);
//            pPr.setRPr(pRpr);
            style.setPPr(pPr);
            style.setRPr(rPr);
        }
    }

    /**
     * 设置目录标题
     *
     * @param wpMLPackage
     * @param styleId
     * @param colorStr
     */
    public static void setTocHeadingStyle(WordprocessingMLPackage wpMLPackage, String styleId, String colorStr) {
        StyleDefinitionsPart sdp = wpMLPackage.getMainDocumentPart().getStyleDefinitionsPart();
        Style style = sdp.getStyleById(styleId);
        if (style != null) {
            RPr rPr = style.getRPr();
            if (rPr == null) {
                rPr = objectFactory.createRPr();
            }
            setFontColor(rPr, true, "black");
            setFontFamily(rPr, "宋体", "Times New Roman");

            PPr pPr = style.getPPr();
            if (pPr == null) {
                pPr = objectFactory.createPPr();
            }
            //设置居中
            Jc jc = pPr.getJc();
            if (jc == null) {
                jc = new Jc();
            }
            jc.setVal(JcEnumeration.CENTER);
            pPr.setJc(jc);
            //字体
            ParaRPr pRpr = pPr.getRPr();
            if (pRpr == null) {
                pRpr = objectFactory.createParaRPr();
            }
            RFonts rFonts = new RFonts();
            rFonts.setAscii("Times New Roman");
            rFonts.setEastAsia("宋体");
            pRpr.setRFonts(rFonts);
            pPr.setRPr(pRpr);
            style.setPPr(pPr);
            style.setRPr(rPr);
        }
    }

    /**
     * 清除段落后空行
     *
     * @param pPr
     */
    public static void setSpacing(PPr pPr) {
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing == null) {
            spacing = new PPrBase.Spacing();
        }
        spacing.setAfter(new BigInteger("0"));
        pPr.setSpacing(spacing);
    }

    /**
     * 1.5倍行距
     *
     * @param pPr
     */
    public static void setSpacingAuto(PPr pPr) {
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing == null) {
            spacing = new PPrBase.Spacing();
        }
        //设置行距1.5倍
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(360));
        pPr.setSpacing(spacing);
    }

    /**
     * @Description: 设置段落底纹(对整段文字起作用)
     */
    public static void setParagraphShdStyle(PPr ppr, STShd shdType, String shdColor) {
        CTShd ctShd = ppr.getShd();
        if (ctShd == null) {
            ctShd = new CTShd();
        }
        if (StringUtils.isNotBlank(shdColor)) {
            ctShd.setColor(shdColor);
        }
        if (shdType != null) {
            ctShd.setVal(shdType);
        }
        ppr.setShd(ctShd);
    }

    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy年M月d日");
        return sdf.format(date);
    }

    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    public static String getDate1(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy年M月");
        return sdf.format(date);
    }

    /**
     * 使用给定的文件创建一个内联图片.
     * 跟前面例子中一样, 我们将文件转换成字节数组, 并用它创建一个内联图片.
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static Inline createInlineImage(WordprocessingMLPackage wpMLPackage, File file) throws Exception {
        byte[] bytes = convertImageToByteArray(file);

        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wpMLPackage, bytes);

        int docPrId = 1;
        int cNvPrId = 2;

        return imagePart.createImageInline("Filename hint", "Alternative text", docPrId, cNvPrId, false);
    }




    /**
     * 创建列表
     *
     * @param factory 工厂类
     * @param type    列表类型 0是删除项目编号，1是（数字编号）有序列表，2是无序列表
     * @return
     */
    public static PPrBase.NumPr createList(ObjectFactory factory, Integer type) {
        // Create and add <w:numPr>
        PPrBase.NumPr numPr = factory.createPPrBaseNumPr();
        // The <w:ilvl> element
        PPrBase.NumPr.Ilvl ilvlElement = factory.createPPrBaseNumPrIlvl();
        numPr.setIlvl(ilvlElement);
        ilvlElement.setVal(BigInteger.valueOf(0));

        // 查阅了相关英文文档，初步认定：0是删除项目编号，1是（数字编号）有序列表，2是无序列表
        PPrBase.NumPr.NumId numIdElement = factory.createPPrBaseNumPrNumId();
        numPr.setNumId(numIdElement);
        numIdElement.setVal(BigInteger.valueOf(2));
        return numPr;
    }


    public static Highlight setFontBackground(String color) {
        Highlight highlight = new Highlight();
        highlight.setVal(color);
        return highlight;
    }

    /**
     * 获取demo中的样式模板
     *
     * @return 所有样式模板列表
     */
    @SneakyThrows
    public static List getContentList() {
        WordprocessingMLPackage wpMLPackage = WordprocessingMLPackage.load(new File( "demo.docx"));
        //标题样式
        List<Object> content = wpMLPackage.getMainDocumentPart().getContent();
        return content;
    }

    /**
     * 设置表格高度
     *
     * @param tr
     */
    public static void setTableHeight(ObjectFactory factory, Tr tr, Integer size) {
        TrPr trPr = tr.getTrPr();
        if (null == trPr) {
            trPr = factory.createTrPr();
        }
        CTHeight ctHeight = new CTHeight();
        ctHeight.setVal(BigInteger.valueOf(size));
        TrHeight trHeight = new TrHeight(ctHeight);
        trHeight.set(trPr);
        tr.setTrPr(trPr);
    }
}


