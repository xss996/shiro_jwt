package com.joeshaw.shiro_jwt.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Table;


@Data
@EqualsAndHashCode(callSuper = false,of = "userId")
@Builder
@Table(name = "sys_user")
@Accessors(chain = true)
public class User {
    private Integer id;
    private String userId;
    private String userName;
    private String password;
    private String userRemarks;


    public static User getUser(String userId, String userName, String encryptPassword, String remark) {
        return User.builder().userId(userId).userName(userName).password(encryptPassword).userRemarks(remark).build();
    }
}
