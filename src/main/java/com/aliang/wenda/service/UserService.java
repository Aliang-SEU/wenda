package com.aliang.wenda.service;

import com.aliang.wenda.dao.LoginTicketDao;
import com.aliang.wenda.dao.UserDao;
import com.aliang.wenda.model.LoginTicket;
import com.aliang.wenda.model.User;
import com.aliang.wenda.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName UserService
 * @Author Aliang
 * @Date 2018/8/8 15:24
 * @Version 1.0
 **/
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    public Map<String, String> register(String username, String password){
        Map<String, String> map = new HashMap<>();
         if(StringUtils.isBlank(username)){
             map.put("msg", "用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
             map.put("msg", "密码不能为空");
             return map;
        }

        User user = userDao.selectByName(username);
        if(user != null){
            map.put("msg", "用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png" , new Random().nextInt(1000)));
        //加盐保证密码的安全性
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        userDao.addUser(user);

        // 登陆
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public User getUser(int id){
        return userDao.selectById(id);
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public Map<String, Object> login(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg", "用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);
        if(user == null){
            map.put("msg", "用户名或密码错误");
            return map;
        }

        //验证用户的密码
        if(!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msg", "用户名或密码错误");
            return map;
        }

        //登录成功 给用户下发一个ticket与用户关联
        String ticket = addLoginTicket(user.getId());
        map.put("userId", user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        //延时100天
        now.setTime(now.getTime() + 3600*24*100);
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket, 1);
    }

    public User selectByName(String name) {
        return userDao.selectByName(name);
    }
}
