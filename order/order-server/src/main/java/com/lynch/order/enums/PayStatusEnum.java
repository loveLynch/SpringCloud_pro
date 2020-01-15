package com.lynch.order.enums;

import lombok.Getter;

/**
 * Created by lynch on 2020-01-13.
 * 支付状态
 **/
@Getter
public enum PayStatusEnum {
    WAIT(0, "等待支付"),

    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
