package com.study.test;


import org.apache.commons.lang3.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.DocumentModel;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.*;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Color;
import org.docx4j.wml.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

/**
 * @author maqi
 * @date 2020/11/16 11:49
 */
@SuppressWarnings("all")
public class Docx4jTest {

    private static final ObjectFactory factory = Context.getWmlObjectFactory();
    private WordprocessingMLPackage wordMLPackage;
    private MainDocumentPart mainDocumentPart;
    private DocumentModel documentModel;


    public static void main(String[] args) throws Exception {

        Docx4jTest docx4jTest = new Docx4jTest();
        WordprocessingMLPackage wordprocessingMLPackage = WordprocessingMLPackage
                .createPackage(PageSizePaper.valueOf("A4"), false); // A4纸，//横版:true

        docx4jTest.wordMLPackage = wordprocessingMLPackage;
        docx4jTest.mainDocumentPart = wordprocessingMLPackage.getMainDocumentPart();

        docx4jTest.documentModel = wordprocessingMLPackage.getDocumentModel();

        SectPr sectPr = docx4jTest.wordMLPackage.getDocumentModel().getSections().get(docx4jTest.wordMLPackage.getDocumentModel().getSections().size() - 1).getSectPr();
        if (sectPr == null) {
            sectPr = factory.createSectPr();
            docx4jTest.mainDocumentPart.addObject(sectPr);
            docx4jTest.documentModel.getSections().get(docx4jTest.documentModel.getSections().size() - 1).setSectPr(sectPr);
        }

        String watermark = "水印水印";
        // 修改值为 first even default 测试
        String hdrFtrRef = "first";

        // header footer 判断规则 规则为： first even default
        if (hdrFtrRef.equals("first")) {
            // first：设置 first、default两个header
            docx4jTest.createHeader(sectPr, "first", watermark);
            docx4jTest.createFooter(sectPr, "first");
            //首页不同时
            sectPr.setTitlePg(new BooleanDefaultTrue());

        } else if (hdrFtrRef.equals("even")) {
            // even: 设置even default 两个header 并在 setting.xml 设置 evenAndOddHeaders
            docx4jTest.createHeader(sectPr, "even", watermark);
            docx4jTest.createFooter(sectPr, "even");
            DocumentSettingsPart documentSettingsPart = docx4jTest.mainDocumentPart.getDocumentSettingsPart();
            CTSettings contents = documentSettingsPart.getContents();
            //奇偶不同时设置
            contents.setEvenAndOddHeaders(new BooleanDefaultTrue());
        }
        // default，增加一个header footer 设置为rels为default
        docx4jTest.createHeader(sectPr, "default", watermark);
        docx4jTest.createFooter(sectPr, "default");

        docx4jTest.mainDocumentPart.addObject(makePageBr());
        // end cover page

        docx4jTest.mainDocumentPart.addStyledParagraphOfText("Heading1", "页面内容");
        docx4jTest.mainDocumentPart.addObject(makePageBr());
        docx4jTest.mainDocumentPart.addStyledParagraphOfText("Normal", "页面内容11111");
        docx4jTest.mainDocumentPart.addObject(makePageBr());

        docx4jTest.wordMLPackage.save(new java.io.File("headerfooterwatermark_test.docx"));

    }

    private void createFooter(SectPr sectPr, String type) throws Exception {
        FooterPart footerPart = new FooterPart(new PartName("/word/footer-" + type + ".xml"));
        Ftr ftr = factory.createFtr();

        // Bind the header JAXB elements as representing their header parts
        footerPart.setJaxbElement(ftr);
        Relationship relationship = mainDocumentPart.addTargetPart(footerPart);
        wordMLPackage.getParts().put(footerPart);

        FooterReference footerReference = factory.createFooterReference();
        footerReference.setType(HdrFtrRef.fromValue(type));
        footerReference.setId(relationship.getId());
        P paragraph = factory.createP();
        if ("first".equals(type)) {
            createHeaderFooterThreePart1(paragraph);
        } else {
            createHeaderFooterThreePart(paragraph);
        }
        ftr.getContent().add(paragraph);
        sectPr.getEGHdrFtrReferences().add(footerReference);

    }

    private void createHeader(SectPr sectPr, String type, String watermark) throws Exception {

        HeaderPart headerPart = new HeaderPart(new PartName("/word/heade-" + type + ".xml"));
        Relationship relationship = this.mainDocumentPart.addTargetPart(headerPart);
        Hdr hdr = null;
        if (StringUtils.isNoneBlank(watermark)) {
            setWatermarkHdr(headerPart, watermark);
            hdr = headerPart.getJaxbElement();
        } else {
            hdr = factory.createHdr();
        }

        // Bind the header JAXB elements as representing their header parts
        headerPart.setJaxbElement(hdr);
        P paragraph = factory.createP();
        if ("first".equals(type)) {
            createHeaderFooterThreePart1(paragraph);
        } else {
            createHeaderFooterThreePart(paragraph);
        }
        hdr.getContent().add(paragraph);

        // headerPart.getJaxbElement().getContent().add(e)

        // Add the reference to both header parts to the Main Document Part
        HeaderReference headerReference = factory.createHeaderReference();
        headerReference.setType(HdrFtrRef.fromValue(type));
        headerReference.setId(relationship.getId());
        sectPr.getEGHdrFtrReferences().add(headerReference);

    }

    /**
     * 添加页眉页脚，左中右 三部分内容
     *
     * @return 页脚对象
     */
    private void createHeaderFooterThreePart(P paragraph) {
        RPr fontRPr = getRPr("宋体", "000000", "22", STHint.EAST_ASIA, true, false, false, false);
        R run = factory.createR();
        run.setRPr(fontRPr);
        paragraph.getContent().add(run);

        // tab
        paragraph.getContent().add(getTextField("left少时诵诗书"));
        R r1 = factory.createR();
        R.Ptab rPtab = factory.createRPtab();
        rPtab.setAlignment(STPTabAlignment.CENTER);
        rPtab.setRelativeTo(STPTabRelativeTo.MARGIN);
        rPtab.setLeader(STPTabLeader.NONE);
        r1.getContent().add(rPtab);
        paragraph.getContent().add(r1);
        // 中间内容
        SdtContentBlock sdtContentBlock = factory.createSdtContentBlock();
        sdtContentBlock.getContent().add(getTextField("第"));
        sdtContentBlock.getContent().add(getFieldBegin());
        sdtContentBlock.getContent().add(getPageNumberField());
        sdtContentBlock.getContent().add(getFieldEnd());
        sdtContentBlock.getContent().add(getTextField("页"));
        sdtContentBlock.getContent().add(getTextField(" 总共"));
        sdtContentBlock.getContent().add(getFieldBegin());
        sdtContentBlock.getContent().add(getTotalPageNumberField());
        sdtContentBlock.getContent().add(getFieldEnd());
        sdtContentBlock.getContent().add(getTextField("页"));
        paragraph.getContent().add(sdtContentBlock);
        // tab
        R r2 = factory.createR();
        R.Ptab rPtab1 = factory.createRPtab();
        rPtab1.setAlignment(STPTabAlignment.RIGHT);
        rPtab1.setRelativeTo(STPTabRelativeTo.MARGIN);
        rPtab1.setLeader(STPTabLeader.NONE);
        r2.getContent().add(rPtab1);

        // 右边内容
        paragraph.getContent().add(r2);
        paragraph.getContent().add(getTextField("right塑料袋"));
    }

    /**
     * 添加页眉页脚，左中右 三部分内容
     *
     * @return 页脚对象
     */
    private void createHeaderFooterThreePart1(P paragraph) {
        RPr fontRPr = getRPr("宋体", "000000", "22", STHint.EAST_ASIA, true, false, false, false);
        R run = factory.createR();
        run.setRPr(fontRPr);
        paragraph.getContent().add(run);

        // tab
        paragraph.getContent().add(getTextField("9990090"));
        R r1 = factory.createR();
        R.Ptab rPtab = factory.createRPtab();
        rPtab.setAlignment(STPTabAlignment.CENTER);
        rPtab.setRelativeTo(STPTabRelativeTo.MARGIN);
        rPtab.setLeader(STPTabLeader.NONE);
        r1.getContent().add(rPtab);
        paragraph.getContent().add(r1);
        // 中间内容
        SdtContentBlock sdtContentBlock = factory.createSdtContentBlock();
        sdtContentBlock.getContent().add(getTextField("第"));
        sdtContentBlock.getContent().add(getFieldBegin());
        sdtContentBlock.getContent().add(getPageNumberField());
        sdtContentBlock.getContent().add(getFieldEnd());
        sdtContentBlock.getContent().add(getTextField("页"));
        sdtContentBlock.getContent().add(getTextField(" 总共"));
        sdtContentBlock.getContent().add(getFieldBegin());
        sdtContentBlock.getContent().add(getTotalPageNumberField());
        sdtContentBlock.getContent().add(getFieldEnd());
        sdtContentBlock.getContent().add(getTextField("页"));
        paragraph.getContent().add(sdtContentBlock);
        // tab
        R r2 = factory.createR();
        R.Ptab rPtab1 = factory.createRPtab();
        rPtab1.setAlignment(STPTabAlignment.RIGHT);
        rPtab1.setRelativeTo(STPTabRelativeTo.MARGIN);
        rPtab1.setLeader(STPTabLeader.NONE);
        r2.getContent().add(rPtab1);

        // 右边内容
        paragraph.getContent().add(r2);
        paragraph.getContent().add(getTextField("uuuuuuuu"));
    }

    private R getTextField(String content) {
        Text text = factory.createText();
        R run = factory.createR();
        text.setValue(content);
        run.getContent().add(text);
        return run;
    }

    private static R getPageNumberField() {
        R run = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue("PAGE \\* MERGEFORMAT");
        run.getContent().add(factory.createRInstrText(txt));
        return run;
    }

    private static R getTotalPageNumberField() {
        R run = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue(" NUMPAGES \\* MERGEFORMAT ");
        run.getContent().add(factory.createRInstrText(txt));
        return run;
    }

    private static R getFieldBegin() {
        R run = factory.createR();
        FldChar fldchar = factory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        run.getContent().add(fldchar);
        return run;
    }

    private R getFieldEnd() {
        FldChar fldcharend = factory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        R run = factory.createR();
        run.getContent().add(fldcharend);
        return run;
    }

    public RPr getRPr(String fontFamily, String colorVal, String fontSize, STHint sTHint, boolean isBlod,
                      boolean isUnderLine, boolean isItalic, boolean isStrike) {
        RPr rPr = factory.createRPr();
        RFonts rf = new RFonts();
        rf.setHint(sTHint);
        rf.setAscii(fontFamily);
        rf.setHAnsi(fontFamily);
        rPr.setRFonts(rf);

        BooleanDefaultTrue bdt = factory.createBooleanDefaultTrue();
        rPr.setBCs(bdt);
        if (isBlod) {
            rPr.setB(bdt);
        }
        if (isItalic) {
            rPr.setI(bdt);
        }
        if (isStrike) {
            rPr.setStrike(bdt);
        }
        if (isUnderLine) {
            U underline = new U();
            underline.setVal(UnderlineEnumeration.SINGLE);
            rPr.setU(underline);
        }

        Color color = new Color();
        color.setVal(colorVal);
        rPr.setColor(color);

        HpsMeasure sz = new HpsMeasure();
        sz.setVal(new BigInteger(fontSize));
        rPr.setSz(sz);
        rPr.setSzCs(sz);
        return rPr;
    }


    private void setWatermarkHdr(HeaderPart headerPart, String text) throws Exception {

        ImagePngPart imagePart = new ImagePngPart(new PartName("/media/background.png"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(createWaterMark(text), "png", out);
        byte[] imagebytes = out.toByteArray();
        imagePart.setBinaryData(imagebytes);
        Relationship rel = headerPart.addTargetPart(imagePart, RelationshipsPart.AddPartBehaviour.REUSE_EXISTING);

        String openXML = "<w:hdr mc:Ignorable=\"w14 wp14\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">"
                + "<w:p>"
                + "<w:pPr>"
                + "<w:pStyle w:val=\"Header\"/>"
                + "</w:pPr>"
                + "<w:r>"
                + "<w:rPr>"
                + "<w:noProof/>"
                + "</w:rPr>"
                + "<w:pict>"
                + "<v:shapetype coordsize=\"21600,21600\" filled=\"f\" id=\"_x0000_t75\" o:preferrelative=\"t\" o:spt=\"75\" path=\"m@4@5l@4@11@9@11@9@5xe\" stroked=\"f\">"
                + "<v:stroke joinstyle=\"miter\"/>"
                + "<v:formulas>"
                + "<v:f eqn=\"if lineDrawn pixelLineWidth 0\"/>"
                + "<v:f eqn=\"sum @0 1 0\"/>"
                + "<v:f eqn=\"sum 0 0 @1\"/>"
                + "<v:f eqn=\"prod @2 1 2\"/>"
                + "<v:f eqn=\"prod @3 21600 pixelWidth\"/>"
                + "<v:f eqn=\"prod @3 21600 pixelHeight\"/>"
                + "<v:f eqn=\"sum @0 0 1\"/>"
                + "<v:f eqn=\"prod @6 1 2\"/>"
                + "<v:f eqn=\"prod @7 21600 pixelWidth\"/>"
                + "<v:f eqn=\"sum @8 21600 0\"/>"
                + "<v:f eqn=\"prod @7 21600 pixelHeight\"/>"
                + "<v:f eqn=\"sum @10 21600 0\"/>"
                + "</v:formulas>"
                + "<v:path gradientshapeok=\"t\" o:connecttype=\"rect\" o:extrusionok=\"f\"/>"
                + "<o:lock aspectratio=\"t\" v:ext=\"edit\"/>"
                + "</v:shapetype>"
                + "<v:shape id=\"WordPictureWatermark835936646\" o:allowincell=\"f\" o:spid=\"_x0000_s2050\" style=\"position:absolute;margin-left:0;margin-top:0;width:467.95pt;height:615.75pt;z-index:-251657216;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin\" type=\"#_x0000_t75\">"
                + "<v:imagedata blacklevel=\"22938f\" gain=\"19661f\" o:title=\"docx4j-logo\" r:id=\"" + rel.getId() + "\"/>"
                + "</v:shape>"
                + "</w:pict>"
                + "</w:r>"
                + "</w:p>"
                + "</w:hdr>";

        Hdr hdr = (Hdr) XmlUtils.unmarshalString(openXML);
        headerPart.setJaxbElement(hdr);
    }

    private static BufferedImage createWaterMark(String content) {
        Integer width = 1000;
        Integer height = 1360;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 获取bufferedImage对象
        String fontType = "宋体";
        Integer fontStyle = Font.PLAIN;
        Integer fontSize = 30;
        Font font = new Font(fontType, fontStyle, fontSize);
        Graphics2D g2d = image.createGraphics(); // 获取Graphics2d对象
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2d.dispose();
        for (int i = 1; i <= 20; i += 2) {
            for (int j = 1; j <= 10; j += 2) {
                int px = j * 100;
                int py = i * 100;
                g2d = image.createGraphics();
                g2d.setColor(java.awt.Color.black);
                g2d.setStroke(new BasicStroke(1)); // 设置字体
                g2d.setFont(font); // 设置字体类型 加粗 大小
                g2d.translate(px, py);// 设置原点
                g2d.rotate(Math.toRadians(-30));// 设置倾斜度
                FontRenderContext context = g2d.getFontRenderContext();
                Rectangle2D bounds = font.getStringBounds(content, context);
                g2d.drawString(content, 0, 0);
                g2d.dispose();
            }
        }
        return image;
    }

    private static P makePageBr() throws Exception {
        P p = factory.createP();
        R r = factory.createR();
        Br br = factory.createBr();
        br.setType(STBrType.PAGE);
        r.getContent().add(br);
        p.getContent().add(r);
        return p;
    }
}


