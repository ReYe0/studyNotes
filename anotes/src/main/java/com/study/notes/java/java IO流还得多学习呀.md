# java IO流还得多学习呀
ByteArrayInputStream 和 ByteArrayOutputStream

标签（空格分隔）： java

---

程序 通过 ByteArrayInputStream 读取 ByteArray
程序 通过 ByteArrayOutputStream 写入 ByteArray

```java
byte[] bb = new byte[]{49,50,51};
InputStream in = new ByteArrayInputStream(bb);
DataInputStream dis = new DataInputStream(in);
```
## InputStream ,ByteArrayInputStream

## DataInputStream



