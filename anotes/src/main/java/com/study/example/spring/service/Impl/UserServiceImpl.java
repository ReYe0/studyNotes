package com.study.example.spring.service.Impl;

import com.study.example.spring.dao.UserDao;
import com.study.example.spring.service.UserService;

/**
 * @Description: studyNotes
 * @Author: 二爷
 * @E-mail: 1299461580@qq.com
 * @Date: 2023/3/5 21:48
 */
public class UserServiceImpl implements UserService {
    public void setUserDao(UserDao userDao) {
        System.out.println("我是UserServiceImpl里面的setUserDao方法");
    }
}
