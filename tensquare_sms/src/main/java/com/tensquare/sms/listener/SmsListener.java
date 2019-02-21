package com.tensquare.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消费者
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    /**
     *  解析消息
     * @param map 消息
     */
    @RabbitHandler
    public void executeSms(Map<String,String> map){
        System.out.println("手机号:"+map.get("mobile"));
        System.out.println("验证码:"+map.get("checkCode"));
    }
}
