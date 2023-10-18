package com.study.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.sun.management.OperatingSystemMXBean;
import org.dom4j.*;
import org.hyperic.sigar.Sigar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.TextAnchor;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherTest {
    /**
     * 得到根元素
     *
     * @param xml xml字符串
     * @return
     */
    public static Element obtainRootElement(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            return document.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取硬盘空间信息
     *
     * @param dir 目录
     * @return
     * @throws IOException
     */
    public static Map<String, Long> getDiskSpace(String dir) throws IOException {
        Sigar sigar = new Sigar();
        File file = new File(dir);
        if (!file.exists()) {
            throw new IOException("dir not found: " + dir);
        }

        long totalSpace = file.getTotalSpace() / 1024l / 1024l;
        long freeSpace = file.getFreeSpace() / 1024l / 1024l;
        long usableSpace = file.getUsableSpace() / 1024l / 1024;

        Map<String, Long> countTable = new HashMap<>(3);
        countTable.put("totalSpace", totalSpace);
        countTable.put("freeSpace", freeSpace);
        countTable.put("usableSpace", usableSpace);
        return countTable;
    }

    private static void os() {
        File[] roots = File.listRoots();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuUsage = osBean.getSystemCpuLoad() * 100;
        double cpuUsage2 = osBean.getProcessCpuLoad() * 100;
        System.out.println(cpuUsage);
        System.out.println(cpuUsage2);

        // 获取内存信息
        com.sun.management.OperatingSystemMXBean sunOsBean =
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalMemory = sunOsBean.getTotalPhysicalMemorySize();
        long freeMemory = sunOsBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;

        double memoryUsage = (double) usedMemory / totalMemory * 100;
        System.out.println(memoryUsage);
//        for (File _file : roots) {
//
//            System.out.println(_file.getPath());
//
////System.out.println(_file.getName());
//
//            System.out.println("Free space = " + _file.getFreeSpace());
//
//            System.out.println("Usable space = " + _file.getUsableSpace());
//
//            System.out.println("Total space = " + _file.getTotalSpace());
//
//            System.out.println("used space = " + (_file.getTotalSpace() - _file.getFreeSpace()));
//
//            System.out.println();
//        }
    }

    @Test
    public void test() {
        String str = "2303";
        System.out.println(str.substring(0, 2));
        System.out.println(str.substring(2, 4));
    }

    @Test
    public void test2() {
        String str = "<PosConfig alarm_smooth_a=\"1\" alarm_smooth_b=\"1\" dgm_type=\"21\" position_type=\"30\" task_id=\"0\" mod_code=\"1\" value_addr=\"-1\" alarm_addr=\"-1\" cc_mod_id=\"0\" byUnit=\"0\" byUnitName=\"℃\" unitNum=\"4\" hhh_limit=\"\" hh_limit=\"20\" h_limit=\"10\" hi_value=\"100\" low_value=\"-100\" ref_vol=\"1000\" ref_eng=\"-1000\" scale=\"0\" corr=\"1\" dataaddress=\"80001\" datamode=\"0\" slaveid=\"5\" normal=\"0\" value_left=\"0\" value_right=\"0\" sensor_id=\"0\" datatype=\"1\" posShow=\"0\"/>";
        Document document = null;
        try {
            document = DocumentHelper.parseText(str);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        System.out.println("根节点：" + root);
        Attribute hh_limit = root.attribute(13);
        Attribute h_limit = root.attribute(14);
        System.out.println(hh_limit);
        System.out.println(h_limit);

        String hh_limitValue = hh_limit.getValue();
        String h_limitValue = h_limit.getValue();

        System.out.println("报警值：" + hh_limitValue);
        System.out.println("预警值：" + h_limitValue);


    }

    @Test
    public void test3() {
//        byte a = 1;//预警
//        byte a = 2;//报警
//        for (int i = 0; i < 8; i++) {
//            System.out.println((int)((a>>(i)) & 0x1));
//        }
//        int bit = (int)((a>>1) & 0x1);
//        System.out.println("获取第一个bit值为：" + bit);


//        System.out.println(100/1000f);
        int val = 16248;
        String s = Integer.toBinaryString(val);
        String substring = s.substring(9, 10);
        String a = "0123456789";
        String b = a.substring(9, 10);
        System.out.println(b);
        System.out.println(substring);
    }

    @Test
    public void ddd() {
//        Object b = null;
//        int a = (int) b;
//        System.out.println(2&4);
//        String b = "-0.0RMB";
//        String substring = b.substring(0, b.length()-3);
//        System.out.println(substring);
//        String a = "-0.0";
//        float v = Float.parseFloat(a);
//        System.out.println(Math.abs(v));

//        String regEx = "[^0-9]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher("010机组");
//        String result = m.replaceAll("").trim();
//        System.out.println(result);

        String date = null;
        String date2 = null;
        try {
            date = dateToStamp("2021-09-07 09:09:39");
            date2 = dateToStamp("2021-09-07 09:09:49");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        System.out.println(date2);
//结果：1630976979000

    }

    /**
     * 时间转换成时间戳,参数和返回值都是字符串
     *
     * @param s
     * @return res
     * @throws ParseException
     */
    public String dateToStamp(String s) throws ParseException {
        String res;
        //设置时间模版
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    @Test
    public void test55() {
//        String regEx = "/^\d+\.?\d*/g";
        Pattern p = Pattern.compile("[^0-9].\\D+$");
        String str = "叶片3Y-20";
//        Matcher m = p.matcher(str.trim());
//        String machineName = m.replaceAll("").trim() + "#";
//        System.out.println(machineName);
        String machineName = "凉水泉34撒的发斯蒂芬";
        Pattern p2 = Pattern.compile("[^0-9]");
        Matcher m2 = p2.matcher(machineName.trim());
        machineName = m2.replaceAll("").trim();
        System.out.println(machineName);
    }

    @Test
    public void test66() {
        String xml = "<PosConfig alarm_smooth_a=\"1\" alarm_smooth_b=\"1\" sensor_A0=\"1\" sensor_A1=\"1\" sensor_B0=\"1\" />";
        Element root = obtainRootElement(xml);
        Attribute sensor_a0 = root.attribute("sensor_A0");
        sensor_a0.setValue("2");
        System.out.println(root);
    }

    @Test
    public void test999() {
        // 创建一个数组
//        ArrayList<Integer> numbers = new ArrayList<>();

        // 往数组中添加元素
//        numbers.add(1);
//        numbers.add(2);
//        numbers.add(3);
//        numbers.add(4);
//        System.out.println("ArrayList: " + numbers);

        // 所有元素乘以 10
//        System.out.print("更新 ArrayList: ");

        // 将 lambda 表达式传递给 forEach
//        int e =1 ;
//        numbers.forEach((e) -> {
//            e = e * 10;
//            System.out.print(e + " ");
//        });
//        System.out.println(e);
//        String atr = "34.44RP";
//        boolean rpm = atr.contains("RPM");
//        System.out.println(rpm);
//        ArrayList<Map> maps = new ArrayList<>();
//        ArrayList<Map> maps2 = new ArrayList<>();
//        Map<String, Object> stringObjectHashMap = new HashMap<>();
//        stringObjectHashMap.put("test",123);
//        maps.add(stringObjectHashMap);
//        stringObjectHashMap.put("test",6666);
//        maps2.add(stringObjectHashMap);
//        System.out.println(maps);
//        System.out.println(maps2);
//        double abs = Math.abs(0.0);
//        System.out.println(abs);
//        double[] doubles = new double[12];
//        doubles[0] = 123453465434f;

        SimpleDateFormat df = new SimpleDateFormat("yyyyM.Mdd");
        Date parse = null;
        try {
            parse = df.parse(df.format(1679279507359l));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(df.format(parse));
    }

    @Test
    public void test12() {
//        String str = "sdf";
//        P p1 = new P();
//        p1.getContent().add(str);
        int a = 0x80000000;
        String s = Integer.toHexString(a);
        System.out.println(s);
        System.out.println(a == 80000000);
    }

    @Test
    public void test1111() {
        ModbusFactory modbusFactory = new ModbusFactory();
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("192.168.0.7");
        ipParameters.setPort(502);
//        ipParameters.setEncapsulated(false);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, true);
        master.setTimeout(3000);
        master.setRetries(3);
        try {
            master.init();
        } catch (ModbusInitException e) {
            System.out.println("初始化失败");
        }
        // 从站地址
        int slaveId = 1;
        // 寄存器地址
        int startOffset = 1;
        byte[] byteArray = {0x01, 0x02};
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        DataInputStream dis = new DataInputStream(bais);
        short[] shortArray = new short[byteArray.length / 2];
        for (int i = 0; i < shortArray.length; i++) {
            try {
                shortArray[i] = dis.readShort();
            } catch (IOException e) {
                System.out.println("字节数组转换失败");
            }
        }
//        WriteMultipleRegistersRequest aa = new WriteMultipleRegistersRequest();
//        int function = aa.getFunction();
//        try {
//            aa.setBytes(byteArray);
//        } catch (ModbusNumberException e) {
//            System.out.println("设置失败");
//        }
//        int serverAddress = aa.getServerAddress();
//        int startAddress = aa.getStartAddress();
//        System.out.println(function);

        // 写入值
//        WriteMaskRegisterRequest writeMaskRegisterRequest = new WriteMaskRegisterRequest(slaveId, startOffset, shortArray);
//        WriteCoilRequest
        WriteRegistersRequest request = null;
        try {
            request = new WriteRegistersRequest(slaveId, startOffset, shortArray);
        } catch (ModbusTransportException e) {
            System.out.println("写入失败");
        }
        try {
            master.send(request);
        } catch (ModbusTransportException e) {
            System.out.println("发送失败");
            e.printStackTrace();
        }
        // 关闭连接
        master.destroy();

    }

    @Test
    public void test11() throws IOException {
        // A网站的访问量统计
        TimeSeries timeSeries = new TimeSeries("A网站访问量统计", Month.class);
        // 添加数据  如果你是从数据库中获取数据，你就写个循环塞值就行了。
        timeSeries.add(new Month(1, 2013), 100);
        timeSeries.add(new Month(2, 2013), 200);
        timeSeries.add(new Month(3, 2013), 300);
        timeSeries.add(new Month(4, 2013), 400);
        timeSeries.add(new Month(5, 2013), 560);
        timeSeries.add(new Month(6, 2013), 600);
        timeSeries.add(new Month(7, 2013), 750);
        timeSeries.add(new Month(8, 2013), 890);
        timeSeries.add(new Month(9, 2013), 120);
        timeSeries.add(new Month(10, 2013), 400);
        timeSeries.add(new Month(11, 2013), 1200);
        timeSeries.add(new Month(12, 2013), 1600);

        // B网站的访问量统计
        //如果有更多的就继续添加就行了
        TimeSeries timeSeries2 = new TimeSeries("B网站访问量统计", Month.class);
        // 添加数据
        timeSeries2.add(new Month(1, 2013), 50);
        timeSeries2.add(new Month(2, 2013), 100);
        timeSeries2.add(new Month(3, 2013), 150);
        timeSeries2.add(new Month(4, 2013), 200);
        timeSeries2.add(new Month(5, 2013), 220);
        timeSeries2.add(new Month(6, 2013), 300);
        timeSeries2.add(new Month(7, 2013), 340);
        timeSeries2.add(new Month(8, 2013), 400);
        timeSeries2.add(new Month(9, 2013), 450);
        timeSeries2.add(new Month(10, 2013), 500);
        timeSeries2.add(new Month(11, 2013), 70);
        timeSeries2.add(new Month(12, 2013), 800);

        // 定义时间序列的集合
        TimeSeriesCollection lineDataset = new TimeSeriesCollection();
        lineDataset.addSeries(timeSeries);
        lineDataset.addSeries(timeSeries2);

        JFreeChart chart = ChartFactory.createTimeSeriesChart("访问量统计时间折线图", "月份", "访问量", lineDataset, true, true, true);

        //设置主标题
        chart.setTitle(new TextTitle("A,B网站访问量统计对比图", new Font("隶书", Font.ITALIC, 15)));
        //设置子标题
        TextTitle subtitle = new TextTitle("2016年度", new Font("黑体", Font.BOLD, 12));
        chart.addSubtitle(subtitle);
        chart.setAntiAlias(true);

        //设置时间轴的范围。
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("M月"));
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));

        //设置曲线是否显示数据点
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) plot.getRenderer();
        xylinerenderer.setBaseShapesVisible(true);

        //设置曲线显示各数据点的值
        XYItemRenderer xyitem = plot.getRenderer();
        xyitem.setBaseItemLabelsVisible(true);
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12));
        plot.setRenderer(xyitem);

        File file = new File("linechart.png");
        //最后返回组成的折线图数值
        ChartUtilities.saveChartAsPNG(file, chart, 700, 500);

//            return fileName;

    }

    @Test
    public void test23423() {
//        int a = 5;
//        // 使用 log10() 方法计算位数
//        int numOfDigits = (int) (Math.log10(a) + 1);
//        System.out.println(numOfDigits); // 输出 5
//
//        // 使用 log() 方法计算位数
//        numOfDigits = (int) (Math.log(a) / Math.log(10) + 1);
//        System.out.println(numOfDigits); // 输出 5
//        float a = 0.043688685f;
//        System.out.println(a/9.8f);
//        System.out.println("详细分析1".substring(4,5));
//        System.out.println(11%2);
//        String localIP = null;
//        try {
//            localIP = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        System.out.println(localIP);
//        DecimalFormat num_df = new DecimalFormat("#.000");
//        String formattedNum = num_df.format(0.1);
//        System.out.println(formattedNum);
//        System.out.println(80/26);
//        System.out.println(80% 26 == 0 ? 0 : 1);
//        int macNum = 40;
//        System.out.println((macNum * 2) / 26 + (((macNum * 2) % 26) == 0 ? 0 : 1));
//        macNum += 1+ 2+3;
//        System.out.println(macNum);
//        float a = ((1531978.0f - 1.0f) / 1000f - (1520411.0f - 1.0f) * 1.0f / 1000f / 1.0f) / 1.0f;
//        System.out.println(a);
//        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(4);
//        //添加元素
//        queue.offer("a");
//        queue.offer("b");
//        queue.offer("c");
//        queue.offer("d");
//        if (queue.size() >= 4){
//            queue.remove();
//        }
//        queue.offer("e");
//        for(String q : queue){
//            System.out.println(q);
//        }

        Properties properties = new Properties();

        try {
            String projectPath = System.getProperty("user.dir");
            FileInputStream file = new FileInputStream("./src/main/resources/application.properties");
            properties.load(file);
            file.close();

//            properties.setProperty("database.url", "jdbc:mysql://new_host:3306/new_db");
//            properties.setProperty("database.username", "new_username");
            properties.setProperty("tcpServerByPOF.tcpOrFtp", "false");

            FileOutputStream fileOut = new FileOutputStream("application.properties");
            properties.store(fileOut, null);
            fileOut.close();

            System.out.println("属性文件修改成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test123() {
        try {
            os();
//            Map<String, Long> diskSpace = getDiskSpace("D:\\");
//            System.out.println(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSystemUsage() {
        final long GB = 1024 * 1024 * 1024;
        while (true) {
            OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            String osJson = JSON.toJSONString(operatingSystemMXBean);
//            System.out.println("osJson is " + osJson);
            JSONObject jsonObject = JSON.parseObject(osJson);
            double processCpuLoad = jsonObject.getDouble("processCpuLoad") * 100;
            double systemCpuLoad = jsonObject.getDouble("systemCpuLoad") * 100;
            Long totalPhysicalMemorySize = jsonObject.getLong("totalPhysicalMemorySize");
            Long freePhysicalMemorySize = jsonObject.getLong("freePhysicalMemorySize");
            double totalMemory = 1.0 * totalPhysicalMemorySize / GB;
            double freeMemory = 1.0 * freePhysicalMemorySize / GB;
            double memoryUseRatio = 1.0 * (totalPhysicalMemorySize - freePhysicalMemorySize) / totalPhysicalMemorySize * 100;

            StringBuilder result = new StringBuilder();
            result.append(new Date(System.currentTimeMillis()))
                    .append("\n系统CPU占用率: ")
                    .append(twoDecimal(systemCpuLoad))
                    .append("%，内存占用率：")
                    .append(twoDecimal(memoryUseRatio))
                    .append("%，系统总内存：")
                    .append(twoDecimal(totalMemory))
                    .append("GB，系统剩余内存：")
                    .append(twoDecimal(freeMemory))
                    .append("GB，该进程占用CPU：")
                    .append(twoDecimal(processCpuLoad))
                    .append("%");
            File[] roots = File.listRoots();
            for (File _file : roots) {
                result.append("\t\n磁盘：" + _file.getPath())
                                .append("\tFree space = " + _file.getFreeSpace() / 1024l / 1024l / 1024l + " GB")
                                .append("\tUsable space = " + _file.getUsableSpace() / 1024l / 1024l / 1024l + " GB")
                                .append("\tTotal space = " + _file.getTotalSpace() / 1024l / 1024l / 1024l + " GB")
                                .append("\tused space = " + (_file.getTotalSpace() - _file.getFreeSpace()) / 1024l / 1024l / 1024l + " GB");
            }
            System.out.println(result.toString());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double twoDecimal(double doubleValue) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    @Test
    public void fsdf(){
//        String filePath = "D:\\BaiduNetdiskDownload\\华能华池紫坊畔风电场_20230601_20230721_V2.0.7.5\\06号风机-Z6-叶片\\22061714081707197_20230601_20230722_sqlData.sql";
        String filePath = "D:\\BaiduNetdiskDownload\\华能华池紫坊畔风电场_20230601_20230721_V2.0.7.5\\01号风机-Z1-\\22061714081813126_20230601_20230722_sqlData.sql";
//        String filePath = "D:\\BaiduNetdiskDownload\\中广核湖北天门项目2023.4.19-5.24\\新建文本文档.txt";
        long l = System.currentTimeMillis();
        try {
            // 创建输入流
            FileInputStream inputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            int available = inputStream.available();
            // 创建临时文件，用于存储修改后的数据
            //若空间不够，会导致数据被剪切掉
            File[] roots = File.listRoots();
            String disk = System.getProperty("java.io.tmpdir");
            long usableSpace = 0l;
            //选取一个空间最大的盘
            for (File _file : roots) {
                if (_file.getUsableSpace() > usableSpace ){
                    usableSpace = _file.getUsableSpace();
                    disk = _file.getPath();
                }
            }
            File file = new File(disk + "temp\\");
            if (!file.exists()) file.mkdir();
            File tempFile = File.createTempFile("temp", null,file);//原本文件路径
            PrintWriter printWriter = new PrintWriter(tempFile);

            // 逐行处理文件内容
            String line;
            boolean flag = false;
            String row3 = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("LOCK TABLES `d_")){//第一行
                    String disable_keys = bufferedReader.readLine();
                    if (disable_keys.contains("DISABLE KEYS")){//第二行
                        row3 = bufferedReader.readLine();
                        if (row3.contains("ENABLE KEYS ")){//第三行
                            flag = false;//空表
                            String s = bufferedReader.readLine();//去掉最后一行
                            if (!s.contains("UNLOCK TABLES")){
                                printWriter.println(s);
                            }
                        }else {
                            flag = true;
                        }
                    }else {//第二次运行没有其他注释，第二行就是数据
                        printWriter.println(line);
                        printWriter.println(disable_keys);
                    }
                }else if (!(line.startsWith("/*!") && line.endsWith("*/;"))) {
                    printWriter.println(line);
                }
                if (flag){
                    printWriter.println(line);
                    printWriter.println(row3);
                    row3 = null;
                    while (flag){
                        String nextRow = bufferedReader.readLine();
                        if (!nextRow.contains("` ENABLE KEYS")) printWriter.println(nextRow);
                        if (nextRow.contains("UNLOCK TABLES")) flag = false;
                    }
                }
            }
            // 关闭流
            bufferedReader.close();
            printWriter.close();
            inputStream.close();
            inputStreamReader.close();

            // 删除原始文件
            File originalFile = new File(filePath);
            originalFile.delete();

            // 重命名临时文件为原始文件名
            tempFile.renameTo(originalFile);
            tempFile.deleteOnExit();
            System.out.println("文件内容已成功修改！");
        } catch (IOException e) {
            System.out.println("处理文件时出现错误：" + e.getMessage());
        }
    }
    @Test
    public  void  sfd(){
        String filePath = "D:\\BaiduNetdiskDownload\\华能华池紫坊畔风电场_20230601_20230721_V2.0.7.5\\01号风机-Z1-\\22061714081813126_20230601_20230722_sqlData.sql";
        String[] split = filePath.split(File.separator);
        List<String> list = Arrays.asList(split);
        list.remove(list.size());
        String s = list.toString();
        System.out.println(s);
    }

    @Test
    public void sdfasdf(){
//        String disk = System.getProperty("java.io.tmpdir");
//        System.out.println(disk);
        File file = new File("D:\\BaiduNetdiskDownload\\华能华池紫坊畔风电场_20230601_20230721_V2.0.7.5\\06号风机-Z6-叶片\\22061714081707197_20230601_20230722_sqlData.sql");
        String s = tail2(file, 1);
        System.out.println(s.contains("-- Dump completed on"));

    }
    public String tail2( File file, int lines) {
        java.io.RandomAccessFile fileHandler = null;
        try {
            fileHandler = new java.io.RandomAccessFile( file, "r" );
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();
            int line = 0;
            for(long filePointer = fileLength; filePointer != -1; filePointer--){
                fileHandler.seek( filePointer );
                int readByte = fileHandler.readByte();
                //\n是换行，英文是linefeed，ASCII码是0xA。
                //\r是回车，英文是carriage return ,ASCII码是0xD。
                // windows下enter是 \n\r,unix下是\n,mac下是\r
                //'\r'是回车，'\n'是换行，前者使光标到行首，后者使光标下移一格。通常用的Enter是两个加起来。
                if( readByte == 0xA ) {//0xA是新行
                    if (filePointer < fileLength) {
                        line = line + 1;
                    }
                } else if( readByte == 0xD ) {//0xD是回车
                    if (filePointer < fileLength-1) {
                        line = line + 1;
                    }
                }
                if (line >= lines) {
                    break;
                }
                sb.append( ( char ) readByte );
            }
            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch( java.io.FileNotFoundException e ) {
            e.printStackTrace();
            return null;
        } catch( java.io.IOException e ) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (fileHandler != null )
                try {
                    fileHandler.close();
                } catch (IOException e) {
                }
        }
    }
    @Test
    public void sdfds(){
//        final int[] arr = new int[2];
//        arr[0] = 1;
//        arr[1] = 2;
//        arr[0] = 3;
//        System.out.println(arr[0]);

        String yearAndMonth = "5";
//        if ("0".equals(yearAndMonth)) return "1998-08-28 12:00:00";
        String year = "20" + yearAndMonth.substring(0, 2);//获取年份
    }
}
