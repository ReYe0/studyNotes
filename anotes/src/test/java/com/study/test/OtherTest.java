package com.study.test;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import org.dom4j.*;
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
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public  void test23423(){
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
        System.out.println("详细分析1".substring(4,5));
    }
}
