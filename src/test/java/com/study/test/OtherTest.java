package com.study.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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


    }
}
