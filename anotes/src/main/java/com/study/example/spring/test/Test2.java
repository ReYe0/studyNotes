package com.study.example.spring.test;

import com.study.example.spring.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: studyNotes
 * @Author: 二爷
 * @E-mail: 1299461580@qq.com
 * @Date: 2023/3/5 21:49
 */
public class Test2 {
    public static void main(String[]args){
        //创建ApplicationContext,加载配置文件，实例化容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("配置文件加载完成");
        //根据beanName获得容器中的Bean实例
        UserService userService = (UserService) applicationContext.getBean("userService");
        System.out.println(userService);
    }
}
