# file

标签（空格分隔）： file

---

## 解決BufferedReader读取中文乱码问题
```java
InputStream in=new FileInputStream("D:\\temp\\user2.txt");
System.out.println(stream2String(in));

//stream2String方法的主要代码:

sb = new StringBuffer();
//bfReader = new BufferedReader(new InputStreamReader(in));  //会出乱码
bfReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

String line = bfReader.readLine();
```