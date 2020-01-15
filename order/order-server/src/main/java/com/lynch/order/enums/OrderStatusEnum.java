package com.lynch.order.enums;

import lombok.Getter;

/**
 * Created by lynch on 2020-01-13.
 * 订单状态
 **/
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
