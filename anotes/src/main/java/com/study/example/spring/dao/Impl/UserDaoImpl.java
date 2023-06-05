package com.study.example.spring.dao.Impl;

import com.study.example.spring.dao.UserDao;
import com.study.example.spring.service.Impl.UserServiceImpl;

/**
 * @Description: studyNotes
 * @Author: 二爷
 * @E-mail: 1299461580@qq.com
 * @Date: 2023/3/5 21:51
 */
public class UserDaoImpl implements UserDao {
    public void setUserService(UserServiceImpl userService) {
        System.out.println("我是UserDaoImpl下setUserService的方法");
    }
}
