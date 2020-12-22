package com.joeshaw.shiro_jwt.service.impl;

import com.joeshaw.shiro_jwt.common.constant.GlobalConstant;
import com.joeshaw.shiro_jwt.common.enums.LoginEnum;
import com.joeshaw.shiro_jwt.common.util.CommonsUtils;
import com.joeshaw.shiro_jwt.common.util.JwtUtils;
import com.joeshaw.shiro_jwt.domain.dto.request.RegisterDTO;
import com.joeshaw.shiro_jwt.domain.entity.User;
import com.joeshaw.shiro_jwt.mapper.UserMapper;
import com.joeshaw.shiro_jwt.mapper.UserRoleMapper;
import com.joeshaw.shiro_jwt.service.UserService;
import com.joeshaw.shiro_jwt.shiro.CustomizedToken;
import jdk.nashorn.internal.objects.Global;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@Order(0)
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public String passWordLogin(String userId, String passWord) {
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 制作CustomizedToken执行登录
        CustomizedToken customizedToken = new CustomizedToken(userId, passWord, LoginEnum.BY_PASSWORD.getLoginType());
        subject.login(customizedToken);
        // 若登陆成功返回相关token
        return JwtUtils.sign(userId, GlobalConstant.TOKEN_SECRET);
    }

    @Override
    public void sendVerificationCode(String userId) {

    }

    @Override
    public String verificationCodeLogin(String userId, String code) {
        return null;
    }

    @Override
    public User selectUser(String userId) {
        return userMapper.selectById(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addUser(RegisterDTO registerDTO) {
        // 制作用户密码,然后将用户插入user表中
        String encryptPassword = CommonsUtils.encryptPassword(registerDTO.getPassword(), String.valueOf(registerDTO.getUserId()));
        User user = new User();
         userMapper.insertUser(User.getUser(registerDTO.getUserId(), registerDTO.getUserName(), encryptPassword, registerDTO.getRemark()));
        // 增加用户角色中间表,注册最基本角色
        userRoleMapper.insert(registerDTO.getUserId(), 200);
        return 1;

    }
}
