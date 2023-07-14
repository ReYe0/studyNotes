package com.study.test;

/**
 * @Description: studyNotes
 * @Author: 二爷
 * @E-mail: 1299461580@qq.com
 * @Date: 2023/7/14 21:55
 */
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Document;
import org.docx4j.wml.P;
import org.docx4j.wml.R;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String docxFilePath = "C:\\Users\\Administrator\\Desktop\\111.docx";
        String fieldText = "二爷";

        try {
            WordprocessingMLPackage wordMLPackage = Docx4J.load(new File(docxFilePath));
            List<Object> contentList = wordMLPackage.getMainDocumentPart().getContent();
            int pageNum = 1;

            for (Object obj : contentList) {
                if (obj instanceof P) {
                    P paragraph = (P) obj;
                    String paragraphText = paragraph.toString();

                    if (paragraphText.contains(fieldText)) {
                        System.out.println("Field found at page: " + pageNum);
                        return;
                    }
                }
                pageNum++;
            }

            System.out.println("Field not found");
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }
}
