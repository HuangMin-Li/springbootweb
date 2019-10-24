package com.xk.springbootweb.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Myservice {
    @RabbitListener(queues = "email_queue")
    public void getMessage(Message message){
        System.out.println("接收到消息："+new String(message.getBody()));
    }
}
