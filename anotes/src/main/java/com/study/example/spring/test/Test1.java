package com.study.example.spring.test;

import com.study.example.spring.dao.UserDao;
import com.study.example.spring.service.UserService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Description: studyNotes
 * @Author: 二爷
 * @E-mail: 1299461580@qq.com
 * @Date: 2023/3/5 21:49
 */
public class Test1 {
    public static void main(String[]args){
        //创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //创建读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //加载配置文件
        reader.loadBeanDefinitions("beans.xml");
        System.out.println("加载完配置文件");
        //获取Bean实例对象
        UserService userService = (UserService) beanFactory.getBean("userService");
        UserDao userDao = (UserDao) beanFactory.getBean("userDao");
    }
}
