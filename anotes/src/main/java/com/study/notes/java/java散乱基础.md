# java散乱基础

标签（空格分隔）： java

---

## 去除科学计数法
将其转为大精度直接输出
```java
BigDecimal bg=new BigDecimal(val+"");//去除科学计数法
```

## 时间戳转为日期格式

```java
 public static String timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
        Date date;
        try {
        date = sdf.parse(sdf.format(timeLong));
        return sdf.format(date);
        } catch (ParseException e) {
        e.printStackTrace();
        return null;
        }
}
```