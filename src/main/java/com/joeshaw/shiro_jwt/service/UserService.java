package com.joeshaw.shiro_jwt.service;

import com.joeshaw.shiro_jwt.domain.dto.request.RegisterDTO;
import com.joeshaw.shiro_jwt.domain.entity.User;

public interface UserService {
    String passWordLogin(String userId,String passWord);

   // void register( String userId, String userName,String password, String remark);

    void sendVerificationCode(String userId);

    String verificationCodeLogin(String userId, String code);

    User selectUser(String userId);
    int addUser(RegisterDTO registerDTO);
}
