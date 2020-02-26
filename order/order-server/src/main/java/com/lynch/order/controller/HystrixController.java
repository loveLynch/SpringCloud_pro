package com.lynch.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by lynch on 2020-02-26.
 **/
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    //@HystrixCommand(fallbackMethod = "fallback")
//    @HystrixCommand //使用默认的降级方法，整个controller
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    }) //超时时间设置
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //设置熔断
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//断路器最小请求数
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//断路器休眠时间窗，结束后会将断路器设置为half open；如果下一次请求成功，会主逻辑，断路器closed；如果下一次失败，断路器进入open
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")//断路器打开的错误率条件
//    }) //服务熔断
    @HystrixCommand
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(@RequestParam("number") Integer number) {
        if ((number & 1) == 0) {
            return "success";
        } else {
            RestTemplate restTemplate = new RestTemplate();
            return String.valueOf(restTemplate.postForObject("http://127.0.0.1:8080/product/listForOrder",
                    Arrays.asList("157875196366160022"),
                    String.class));
        }
    }

    private String fallback() {
        return "太拥挤了，请稍后再试～～";
    }

    private String defaultFallback() {
        return "默认提示：太拥挤了，请稍后再试～～";
    }
}
