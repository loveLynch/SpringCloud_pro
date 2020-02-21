package com.lynch.user.enums;

import lombok.Getter;

/**
 * Created by lynch on 2020-02-21.
 **/
@Getter
public enum RoleEnum {
    BUYER(1, "买家"),
    SELLER(2, "卖家"),
    ;

    private Integer code;

    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
