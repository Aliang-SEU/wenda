package com.aliang.wenda.model;

import org.springframework.stereotype.Component;

/**
 * @ClassName HostHolder
 * @Author Aliang
 * @Date 2018/8/8 21:29
 * @Version 1.0
 **/
@Component
public class HostHolder {

    //每个线程都拥有一个拷贝
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}


