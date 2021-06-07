package org.xjosiah.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xjosiah.demo.dao.UserMapper;
import org.xjosiah.demo.entity.User;
import org.xjosiah.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        return userMapper.loginUser(username, password);
    }

    @Override
    public Boolean register(User user) {
        return userMapper.createUser(user);
    }

    @Override
    public Boolean checkWhetherHasUser(String username) {
        User user = getUserByName(username);
        return user != null;
    }

    @Override
    public String getUserPicByName(String username) {
        return userMapper.findUserByName(username).getPicUrl();
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.findUserByName(username);
    }
}
