package com.nowcoder.community.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.standard.expression.MessageExpression;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailClient.class);

    //发送邮件主要就是借助这个和下面那个help类
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     *
     * @param to : 邮件接收者
     * @param subject : 主题
     * @param content : 内容
     */
    public void sendMail(String to, String subject, String content){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        }catch (MessagingException e){
            LOGGER.error("发送邮件失败" + e.getMessage());
            e.printStackTrace();
        }

    }
}
