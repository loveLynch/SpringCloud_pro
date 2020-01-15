package com.lynch.product.enums;

import lombok.Getter;

/**
 * Created by lynch on 2020-01-06.
 * <p>
 * 商品上下架状态
 **/
@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
