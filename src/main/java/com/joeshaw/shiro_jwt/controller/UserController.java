package com.joeshaw.shiro_jwt.controller;

import com.joeshaw.shiro_jwt.domain.dto.request.PassWordLoginDTO;
import com.joeshaw.shiro_jwt.domain.dto.request.RegisterDTO;
import com.joeshaw.shiro_jwt.domain.dto.response.Result;
import com.joeshaw.shiro_jwt.domain.entity.User;
import com.joeshaw.shiro_jwt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/user/")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "register", name = "用户注册")
    public Object userRegister(@RequestBody @Valid RegisterDTO registerDTO) {
        //1.查询用户是否存在
        User user = userService.selectUser(registerDTO.getUserId());
        if (user != null) {
            return new ResponseEntity(new Result("用户已存在"), HttpStatus.OK);
        }
        if (1 == userService.addUser(registerDTO))
            return new ResponseEntity(new Result("注册成功"), HttpStatus.OK);
        else
            return new ResponseEntity(new Result("注册失败"), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping(value = "loginByPsw", name = "用户密码登录")
    public Object userLoginWithPsw(@RequestBody @Valid PassWordLoginDTO passWordLoginDTO) {
        log.info("登录传递的请求参数:{}",passWordLoginDTO);
        if (StringUtils.isEmpty(passWordLoginDTO.getUserId())){
            return new ResponseEntity(new Result("用户名为空"),HttpStatus.BAD_REQUEST);
        }
        //1.查询用户是否存在
        User user = userService.selectUser(passWordLoginDTO.getUserId());
        if (null==user){
            return new ResponseEntity(new Result("用户不存在"),HttpStatus.BAD_REQUEST);
        }
        String token = userService.passWordLogin(passWordLoginDTO.getUserId(), passWordLoginDTO.getPassword());
        return new ResponseEntity(new Result(token),HttpStatus.OK);
    }

    @PostMapping(value = "getVerificationCode", name = "获取验证码")
    public void sendVerificationCode() {

    }

    @PostMapping(value = "login/vCode", name = "用户验证码登录")
    public void userLoginWithVerificationCode() {

    }

    @RequestMapping(value = "/test",name="测试")
    @RequiresPermissions({"queryMyUserInfo"})
    public Object test(){
        return new ResponseEntity(new Result("测试通过"),HttpStatus.OK);
    }

}
