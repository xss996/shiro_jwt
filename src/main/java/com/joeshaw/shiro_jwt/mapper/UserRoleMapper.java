package com.joeshaw.shiro_jwt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    void insert(@Param("userId") String userId, @Param("roleId") Integer roleId);
}
