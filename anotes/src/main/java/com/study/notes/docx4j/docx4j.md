# docx4j

标签（空格分隔）： docx4j

---

## 1.快速入门

**官网：**[Office Open XML - OOXML 字处理ML文件的剖析](http://officeopenxml.com/anatomyofOOXML)

### 1.1.引入依赖

有些应该是多的，按需要引入

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
    <dependency>
        <groupId>org.docx4j</groupId>
        <artifactId>docx4j</artifactId>
        <version>6.1.2</version>
    </dependency>
</dependencies>
```

### 1.2.用一些文本创建文档

```java
 @Test
    public void test1() throws Docx4JException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();//创建一个空白word
        wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");//添加一个文本段落
        wordMLPackage.save(new java.io.File("HelloWord1.docx"));
    }
```

### 1.3.添加带样式的文本

```java
 @Test
    public void test2() throws Docx4JException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello Word!");
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle","This is a subtitle!");
        wordMLPackage.save(new java.io.File("HelloWord2.docx"));
    }
```

### 1.4.添加表格

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

### 1.5.给表格添加边框



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

### 1.6.给表格添加其他样式

```java
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
```



## 2.加载已有的word修改其表格中的值

```java
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
```

