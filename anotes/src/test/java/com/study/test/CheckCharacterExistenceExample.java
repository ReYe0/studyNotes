package com.study.test;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FootnotesPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.docx4j.wml.P;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.File;

public class CheckCharacterExistenceExample {
    public static void main(String[] args) throws Exception {
        // 加载文档
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File("20230714保存一下振动分析报告 (6).docx"));
        MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
        Document contents = mainDocumentPart.getContents();
        FootnotesPart footnotesPart = mainDocumentPart.getFootnotesPart();
        String xml = mainDocumentPart.getXML();
        if (xml.contains("检")){
            System.out.println(7666);
        }

        // 检查文档中是否存在目标字符
        String targetText = "检";
        boolean characterExists = checkCharacterExists(wordMLPackage, targetText);

        if (characterExists) {
            System.out.println("目标字符存在于文档中。");
        } else {
            System.out.println("目标字符未找到。");
        }
    }

    // 检查文档中是否存在目标字符
    private static boolean checkCharacterExists(WordprocessingMLPackage wordMLPackage, String targetText) {
        for (Object object : wordMLPackage.getMainDocumentPart().getContent()) {
            if (object instanceof P) {
                P paragraph = (P) object;
                for (Object child : paragraph.getContent()) {
                    if (child instanceof JAXBElement && ((JAXBElement<?>) child).getName().equals(new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "r"))) {
                        JAXBElement<?> runElement = (JAXBElement<?>) child;
                        if (runElement.getValue() instanceof Text) {
                            Text text = (Text) runElement.getValue();
                            if (text.getValue().contains(targetText)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
