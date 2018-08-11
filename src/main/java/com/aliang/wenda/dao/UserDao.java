package com.aliang.wenda.dao;

import com.aliang.wenda.model.User;
import org.apache.ibatis.annotations.*;


/**
 * @ClassName UserDao
 * @Author Aliang
 * @Date 2018/8/8 13:25
 * @Version 1.0
 **/
@Mapper
public interface UserDao {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name, password, salt, head_url";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO", TABLE_NAME, "(", INSERT_FIELDS,
            ") VALUES(#{name}, #{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE id=#{id}"})
    User selectById(int id);

    @Update({"UPDATE", TABLE_NAME, "SET password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"DELETE FROM", TABLE_NAME, "WHERE id=#{id}"})
    void deleteById(int id);

    @Select({"SELECT", SELECT_FIELDS, "FROM", TABLE_NAME, "WHERE name=#{username}"})
    User selectByName(String username);
}
