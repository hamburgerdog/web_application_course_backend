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
        System.out.println(user);
        return userMapper.createUser(user);
    }

    @Override
    public Boolean checkWhetherHasUser(String username) {
        User user = userMapper.findUserByName(username);
        return user != null;
    }
}
