package org.inlighting.service.impl;

import org.inlighting.Entities.User;
import org.inlighting.dao.UserDao;
import org.inlighting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
