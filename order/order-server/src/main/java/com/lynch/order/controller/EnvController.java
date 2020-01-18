package com.lynch.order.controller;

import com.lynch.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by lynch on 2020-01-15.
 **/
@RestController
@RequestMapping("/env")
@RefreshScope
public class EnvController {

    @Autowired
    private GirlConfig girlConfig;

    @Value("${env}")
    private String env;

    @GetMapping("/print")
    public String test() {
        return env;
    }

    @GetMapping("/girl")
    public String print() {
        return girlConfig.toString();
    }

}
