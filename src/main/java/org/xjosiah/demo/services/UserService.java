package org.xjosiah.demo.services;

import org.xjosiah.demo.entity.User;

import javax.sql.RowSet;

public interface UserService {
    User login(String username,String password);
    Boolean register(User user);
    Boolean checkWhetherHasUser(String username);
    String getUserPicByName(String username);
    User getUserByName(String username);
}
