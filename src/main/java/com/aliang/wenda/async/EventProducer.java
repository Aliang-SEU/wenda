package com.aliang.wenda.async;

import com.aliang.wenda.utils.JedisAdapter;
import com.aliang.wenda.utils.RedisKeyUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/10 10:49
 * @Version 1.0
 **/
@Service
public class EventProducer {

    //事件队列
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 生产者产生事件
     * @param eventModel
     * @return
     */
    public boolean fireEvent(EventModel eventModel){
        try{
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        }catch(Exception e){
            return false;
        }
    }


}
