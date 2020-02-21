package com.lynch.user.enums;

import lombok.Getter;

/**
 * Created by lynch on 2020-01-14.
 **/
@Getter
public enum ResultEnum {
    LOGIN_FAIL(1, "登陆失败"),
    ROLE_ERROR(2, "角色权限有误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
