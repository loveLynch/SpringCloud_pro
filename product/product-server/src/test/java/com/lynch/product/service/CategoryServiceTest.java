package com.lynch.product.service;

import com.lynch.product.ProductApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * Created by lynch on 2020-01-06.
 **/
@Component
public class CategoryServiceTest extends ProductApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findByCategoryTypeIn() {
        categoryService.findByCategoryTypeIn(Arrays.asList(11, 22));
    }

}