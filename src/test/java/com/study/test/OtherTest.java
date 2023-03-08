package com.study.test;

import org.dom4j.*;
import org.junit.Test;

public class OtherTest {
    @Test
    public void test(){
        String str = "2303";
        System.out.println(str.substring(0, 2));
        System.out.println(str.substring(2, 4));
    }

    @Test
    public void test2(){
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
    public void test3(){
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
}
