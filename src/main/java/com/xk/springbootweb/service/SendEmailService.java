package com.xk.springbootweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class SendEmailService {
    @Autowired
    //邮件发送器
    private JavaMailSenderImpl mailSender;
    @Value("${spring.mail.username}")
    private String from;
    //简单邮件发送
    public void sendSimpleEmail(String to,String subject,String text){
        //创建一个简单的邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
            System.out.println("纯文本邮件发送成功");
        } catch (MailException e) {
            System.out.println("纯文本邮件发送失败 "+e.getMessage());e.printStackTrace();
        }
    }
    //复合邮件发送
    public void sendComplexEmail(String to,String subject,String text,String filePath,String rscId, String rscPath){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            //设置下载附件
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            System.out.println("复杂邮件发送成功");
        } catch (MessagingException e) {
            System.out.println("复杂邮件发送失败 "+e.getMessage());e.printStackTrace();
            }
        }


}
