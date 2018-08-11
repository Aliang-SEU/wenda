package com.aliang.wenda.dao;

import com.aliang.wenda.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @ClassName LoginTicketDao
 * @Author Aliang
 * @Date 2018/8/8 20:34
 * @Version 1.0
 **/
@Mapper
public interface LoginTicketDao {

    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "user_id, expired, status, ticket";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES(#{userId}, #{expired}, #{status}, #{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"SELECT", SELECT_FIELDS,"FROM", TABLE_NAME, "where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update", TABLE_NAME, "SET STATUS=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
