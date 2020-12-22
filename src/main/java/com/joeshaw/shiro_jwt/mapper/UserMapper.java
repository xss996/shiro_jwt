package com.joeshaw.shiro_jwt.mapper;

import com.joeshaw.shiro_jwt.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

@Mapper
public interface UserMapper {

    User selectById(@Param("userId") String userId);

    LinkedHashMap<String, Object> selectUserPermissionById(@Param("userId") String userId);

    User selectByName(@Param("userName") String userName);

    LinkedHashMap<String, Object> selectUserPermissionByName(@Param("userName") String userId);

    int insertUser(@Param("user") User user);
}