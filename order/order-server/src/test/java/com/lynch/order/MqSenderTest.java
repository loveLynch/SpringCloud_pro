package com.lynch.order;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lynch on 2020-02-02.
 * 发送mq消息测试
 **/
@Component
public class MqSenderTest extends OrderApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "now: " + new Date());
    }


    @Test
    public void sendOrder() {
        amqpTemplate.convertAndSend("myOrder", "fruit", "now: " + new Date());
    }
}
