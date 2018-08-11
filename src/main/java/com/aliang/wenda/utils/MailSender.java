package com.aliang.wenda.utils;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * @Description 邮件服务
 * @Author Aliang
 * @Date 2018/8/10 16:03
 * @Version 1.0
 **/
@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * 发邮件
     * @param to
     * @param subject
     * @param template
     * @param model
     * @return
     */
    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            //昵称
            String nick = MimeUtility.encodeText("牛客中级课");
            //发送方邮箱
            InternetAddress from = new InternetAddress(nick + "<550850113@qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            //邮箱正文类
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            //文本通过velocity引擎渲染得到自定义的邮件
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }

    /**
     * 初始化邮件发送类
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("550850113@qq.com");
        mailSender.setPassword("19940124hzl");
        mailSender.setHost("pop.qq.com");
        //mailSender.setHost("smtp.qq.com");
        mailSender.setPort(995);
        mailSender.setProtocol("pop3");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.pop.ssl.enable", true);
        javaMailProperties.put("mail.pop.auth", true);
        //javaMailProperties.put("mail.smtp.starttls.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }


}
