# docx4j

标签（空格分隔）： docx4j

---

## 引入依赖

```pom
<dependencies>
	<dependency>
		<groupId>org.docx4j</groupId>
		<artifactId>docx4j-core</artifactId>
		<version>8.2.6</version>
	</dependency>
	<dependency>
		<groupId>org.docx4j</groupId>
		<artifactId>docx4j-JAXB-ReferenceImpl</artifactId>
		<version>8.2.6</version>
	</dependency>
</dependencies>
```

## 用一些文本创建文档

```java
 @Test
    public void test1() throws Docx4JException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();//创建一个空白word
        wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");//添加一个文本段落
        wordMLPackage.save(new java.io.File("HelloWord1.docx"));
    }
```

## 添加带样式的文本

```java
 @Test
    public void test2() throws Docx4JException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello Word!");
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle","This is a subtitle!");
        wordMLPackage.save(new java.io.File("HelloWord2.docx"));
    }
```

## 添加表格

```java
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
```

## 给表格添加边框



```java
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
        border.setColor("auto");
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
```

