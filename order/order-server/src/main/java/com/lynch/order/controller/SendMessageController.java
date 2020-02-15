package com.lynch.order.controller;

import com.lynch.order.dto.OrderDTO;
import com.lynch.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by lynch on 2020-02-02.
 **/
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;


//    @GetMapping("/sendMessage")
//    public void process() {
//        String message = "now " + new Date();
//        streamClient.output().send(MessageBuilder.withPayload(message).build());
//
//    }

    /**
     * 发送orderDTO对象
     */
    @GetMapping("/sendMessage")
    public void process() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());

    }
}
