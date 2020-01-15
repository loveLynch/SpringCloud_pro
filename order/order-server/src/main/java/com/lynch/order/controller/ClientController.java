package com.lynch.order.controller;

import com.lynch.order.dto.CartDTO;
import com.lynch.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lynch on 2020-01-14.
 **/
@RestController
@Slf4j
public class ClientController {

//    @Autowired //一、2
//    private LoadBalancerClient loadBalancerClient;

//    @Autowired //一、3
//    private RestTemplate restTemplate;

//    @Autowired
//    private ProductClient productClient;

//    @GetMapping("/getProductMsg")
//    public String getProductMsg() {
        /*一、RestTemplate三种方法*/
        //1.第一种方式（直接使用restTemplate，url写死）
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:8080/msg", String.class);
//        log.info("response {}", response);

        //2.第二种方式（利用loadBalancerClient通过应用名获取url，然后再使用restTemplate)
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/msg";
//        String response = restTemplate.getForObject(url, String.class);
//        log.info("response {}", response);

        //3.第三种方式（利用@LoadBlanced，可在restTemplate里使用应用名字）
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
//        log.info("response {}", response);
//        return response;


        /*二、使用Feign*/
//        String response = productClient.productMsg();
//        log.info("response {}", response);
//        return response;
//
//
//    }

//    @GetMapping("/getProductList")
//    public String getProductList() {
//        List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
//        log.info("response {}", productInfoList);
//        return "ok";
//
//    }
//
//
//    @GetMapping("/productDecreaseStock")
//    public String productDecreaseStock() {
//        productClient.decreaseStock(Arrays.asList(new CartDTO("164103465734242707", 3)));
//        return "ok";
//    }
}
