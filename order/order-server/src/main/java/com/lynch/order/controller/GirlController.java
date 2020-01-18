package com.lynch.order.controller;

import com.lynch.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lynch on 2020-01-18.
 **/
@RestController
public class GirlController {
    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("/girl/print")
    public String print() {
        return "name：" + girlConfig.getName() + " age：" + girlConfig.getAge();
    }
}
