package com.lynch.product.service;

import com.lynch.product.domain.ProductInfo;
import com.lynch.product.dto.CartDTO;

import java.util.List;

/**
 * Created by lynch on 2020-01-06.
 **/
public interface ProductService {

    /**
     * 查询所以在架商品
     *
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     *
     * @param productIdList
     * @return
     */
    List<ProductInfo> findList(List<String> productIdList);

    /**
     * 扣库存
     *
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
