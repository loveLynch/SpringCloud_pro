package com.lynch.product.exception;

import com.lynch.product.enums.ResultEnum;

/**
 * Created by lynch on 2020-01-15.
 **/
public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
