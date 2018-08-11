package com.aliang.wenda.async.handler;

import com.aliang.wenda.async.EventHandler;
import com.aliang.wenda.async.EventModel;
import com.aliang.wenda.async.EventType;
import com.aliang.wenda.model.Message;
import com.aliang.wenda.model.User;
import com.aliang.wenda.service.MessageService;
import com.aliang.wenda.service.UserService;
import com.aliang.wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/10 11:46
 * @Version 1.0
 **/
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        //构造消息
        Message message = new Message();
        //系统通知
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getActorId());
        //刚刚发生
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExts("questionId"));

        //发送消息
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
