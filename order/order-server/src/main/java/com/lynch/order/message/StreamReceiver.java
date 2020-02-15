package com.lynch.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by lynch on 2020-02-02.
 **/
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {


//    @StreamListener(StreamClient.INPUT)
//    public void process(Object message) {
//        log.info("StreamReceiver: {}", message);
//
//    }


    /**
     * 接收orderDTO对象 消息
     *
     * @param message
     */
    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2)
    public String process(Object message) {
        log.info("StreamReceiver: {}", message);
        return "received.";

    }

    /**
     * 接收回复的 消息
     *
     * @param message
     */
    @StreamListener(StreamClient.INPUT2)
    public void process2(Object message) {
        log.info("StreamReceiver2: {}", message);

    }
}
