package org.xjosiah.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.xjosiah.demo.entity.User;

@Component
@Mapper
public interface UserMapper {
    User findUserByName(@Param("username") String name);

    User loginUser(@Param("username") String name, @Param("password") String pd);

    Boolean createUser(@Param("user") User user);

    Integer deleteUser(@Param("username") String name, @Param("password") String pd);
}
