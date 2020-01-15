package com.lynch.product.service;

import com.lynch.product.domain.ProductCategory;

import java.util.List;

/**
 * Created by lynch on 2020-01-06.
 * 类目
 **/
public interface CategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
