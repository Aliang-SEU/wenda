package com.aliang.wenda.async;

import com.aliang.wenda.controller.CommentController;
import com.aliang.wenda.utils.JedisAdapter;

import com.aliang.wenda.utils.RedisKeyUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/10 10:57
 * @Version 1.0
 **/
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    private ApplicationContext applicationContext;

    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    //简历eventhandler和 event 关联起来

    @Override
    public void afterPropertiesSet() throws Exception {
        //获取所有实现EventHandler的类
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans != null) {
            for(Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                //获取支持的事件
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();

                for(EventType type : eventTypes) {
                    if(!config.containsKey(type)){
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        //启用一个新线程处理来处理各种事件
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                //一直处理
                while(true){
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for(String message : events){
                        if(message.equals(key)){
                            continue;
                        }

                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if(!config.containsKey(eventModel.getType())){
                            logger.error("不能识别的事件");

                        }
                        //分发事件到处理器
                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        //启动线程
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
