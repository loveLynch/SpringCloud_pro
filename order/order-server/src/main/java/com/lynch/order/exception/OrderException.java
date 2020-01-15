package com.lynch.order.exception;


import com.lynch.order.enums.ResultEnum;

/**
 * Created by lynch on 2020-01-14.
 **/
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
