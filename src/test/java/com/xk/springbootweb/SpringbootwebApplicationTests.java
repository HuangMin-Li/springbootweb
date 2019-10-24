package com.xk.springbootweb;

import com.xk.springbootweb.pojo.User;
import com.xk.springbootweb.service.SendEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootwebApplicationTests {
    @Autowired
    public AmqpAdmin amqpAdmin;
    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Test
    public void pushMsg(){
        //消息发送到哪一个交换器
    rabbitTemplate.convertAndSend("msg_fanout_exchange","",new User(1234,"张三"));
    }
    @Test
    public void psubPublisher(){
        User user= new User();
        user.setId(333);
        user.setName("李四三");
        rabbitTemplate.convertAndSend("msg_fanout_exchange","",user);
    }
    @Test
    public void addFanoutExchange(){
        //1、新建FanoutExchange(发布/订阅模式的交换器)
        FanoutExchange fanoutExchange=new FanoutExchange("msg_fanout_exchange");
        //2、新建消息队列(Email SMS消息队列)
        Queue queueEmail=new Queue("email_queue");
        Queue queueSms = new Queue("sms_queue");
        //3、消息队列绑定到交换器
        Binding bindingEmail = new Binding("email_queue",Binding.DestinationType.QUEUE, "msg_fanout_exchange", "", null);
        Binding bindingSms=new Binding("sms_queue",Binding.DestinationType.QUEUE,"msg_fanout_exchange","",null);
        //4、将交换器、消息队列注册到AmqpAdmin中
        amqpAdmin.declareExchange(fanoutExchange);
        amqpAdmin.declareQueue(queueEmail);
        amqpAdmin.declareQueue(queueSms);
        amqpAdmin.declareBinding(bindingEmail);
        amqpAdmin.declareBinding(bindingSms);
    }
    //纯文本邮件发送测试
    @Autowired
    private SendEmailService sendEmailService;
    @Test
    public void sendSimpleMailTest() {
        String to="hmlovelzh@163.com";
        String subject="【纯文本邮件】标题";
        String text="Spring Boot纯文本邮件发送内容测试.....";
        sendEmailService.sendSimpleEmail(to,subject,text);
    }
    @Test
    public void sendComplexEmailTest() {
        String to="hmlovelzh@163.com";
        String subject="【复杂邮件】标题";
        StringBuilder text = new StringBuilder();
        text.append("<html><head></head>");
        text.append("<body><h1>Spring Boot复杂邮件发送内容测试.....</h1>");
        String rscId = "img001";
        text.append("<img src='cid:" +rscId+"'/></body>");text.append("</html>");
        String rscPath="D:\\Pictures\\view.jpg";
        String filePath="E:\\套餐资费说明.txt";
        sendEmailService.sendComplexEmail(to,subject,text.toString(),
                filePath,rscId,rscPath);
    }
}
