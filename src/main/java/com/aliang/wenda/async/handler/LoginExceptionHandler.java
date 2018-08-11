package com.aliang.wenda.async.handler;

import com.aliang.wenda.async.EventHandler;
import com.aliang.wenda.async.EventModel;
import com.aliang.wenda.async.EventType;
import com.aliang.wenda.model.Message;
import com.aliang.wenda.service.MessageService;
import com.aliang.wenda.utils.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 登录异常处理类
 * @Author Aliang
 * @Date 2018/8/10 15:43
 * @Version 1.0
 **/
@Component
public class LoginExceptionHandler implements EventHandler{

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        // xxxx判断发现这个用户登陆异常
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", model.getExts("username"));
        mailSender.sendWithHTMLTemplate(model.getExts("email"), "登陆IP异常", "mails/login_exception.html", map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
