package com.aliang.wenda.async;

import java.util.List;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/10 10:55
 * @Version 1.0
 **/
public interface EventHandler {

    //处理接口
    void doHandle(EventModel model);

    //注册自己
    List<EventType> getSupportEventTypes();
}
