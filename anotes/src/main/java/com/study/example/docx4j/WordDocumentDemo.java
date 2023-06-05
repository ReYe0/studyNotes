package com.study.example.docx4j;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.toc.TocGenerator;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.P;
import org.docx4j.wml.SdtBlock;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WordDocumentDemo {
    public static void main(String[] args) throws Docx4JException, IOException {
        // 转换Word文档为PDF
        WordprocessingMLPackage wpMLPackage = WordprocessingMLPackage.load(new File("mingyang.docx"));
        MainDocumentPart mainDocumentPart = wpMLPackage.getMainDocumentPart();
        List<Object> paragraphs = mainDocumentPart.getContent();
        P paragraph = (P)paragraphs.get(16);
        SdtBlock sdtBlock = (SdtBlock)paragraphs.get(17);
        ContentAccessor contentAccessor = sdtBlock.getSdtContent();
//        FieldsPreprocessor.updatePageNumbers(mainDocumentPart);
//        TocGeneratorHelper tocGeneratorHelper = new TocGeneratorHelper();
//        TocGenerator tocGenerator = tocGeneratorHelper.createTocGenerator(mainDocumentPart);
//        tocGenerator.updateToc(contentAccessor.getContent());






//        TocGeneratorHelper helper = new TocGeneratorHelper();
//        TocGenerator tocGenerator = helper.createTocGenerator(mainDocumentPart);
//        tocGenerator.updateToc(contentAccessor.getContent());
        TocGenerator tocGenerator = new TocGenerator(wpMLPackage);
        tocGenerator.updateToc();
//        Docx4jProperties.setProperty("docx4j.toc.BookmarksIntegrity.remediate", true);
//        tocGenerator.updateToc(false); // excluding page numbers
        wpMLPackage.save(new File("mingyang2.docx"));
//        List<Object> content1 = wpMLPackage.getMainDocumentPart().getContent();
//        Object o = content1.get(12);
//
//        System.out.println(12);
//        PdfConversion convert = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(wpMLPackage);
//        OutputStream out = new FileOutputStream(new File("output-file-path.pdf"));
//        convert.output(out);

//// 使用iText搜索文本并获取页码
//        PdfReader pdfReader = new PdfReader("mingyang.pdf");
//        int numPages = pdfReader.getNumberOfPages();
//
//        for (int i = 1; i <= numPages; i++) {
//            PdfDictionary page = pdfReader.getPageN(i);
//            PdfObject content = page.get(PdfName.CONTENTS);
//            if (content == null) {
//                continue;
//            }
//            PRStream stream = (PRStream) PdfReader.getPdfObject(content);
//            byte[] data = PdfReader.getStreamBytes(stream);
//            PdfString baseFont = (PdfString) page.getAsArray(PdfName.RESOURCES).getAsDict(PdfName.FONT).get(new PdfName("F1"));
//
//            if (baseFont != null) {
//                String contentStr = new String(data, "UTF-8");
//                if (contentStr.contains("search-text")) {
//                    System.out.println("Page Number of Text: " + i);
//                }
//            }
//        }
//
//        pdfReader.close();

    }
}