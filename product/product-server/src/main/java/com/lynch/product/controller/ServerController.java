package com.lynch.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lynch on 2020-01-14.
 **/
@RestController
public class ServerController {

    @RequestMapping("/msg")
    public String msg() {
        return "this is java.com.lynch.product msg";
    }

}
