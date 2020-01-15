package com.lynch.order.dto;

import lombok.Data;

/**
 * Created by lynch on 2020-01-15.
 **/
@Data
public class CartDTO {
    //商品id
    private String productId;

    //商品数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
