package org.inlighting.service.impl;

import org.inlighting.Entities.UserInfo;
import org.inlighting.dao.UserInfoDao;
import org.inlighting.service.UserService1;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService1 {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}
