package com.lynch.product.common;

import lombok.Data;

/**
 * Created by lynch on 2020-01-15.
 **/
@Data
public class DecreaseStockInput {

    private String productId;
    private Integer productQuantity;

    public DecreaseStockInput() {
    }

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
