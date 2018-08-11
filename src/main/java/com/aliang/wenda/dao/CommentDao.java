package com.aliang.wenda.dao;

import com.aliang.wenda.model.Comment;
import com.aliang.wenda.model.Question;
import org.apache.ibatis.annotations.*;

import javax.xml.ws.soap.MTOM;
import java.util.List;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/9 16:58
 * @Version 1.0
 **/
@Mapper
public interface CommentDao {

    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "user_id, content, created_date, entity_id, entity_type, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;


    @Insert({"INSERT INTO", TABLE_NAME, "(", INSERT_FIELDS,
            ") VALUES(#{userId}, #{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment getCommentById(int id);

    @Select({"SELECT", SELECT_FIELDS, "FROM", TABLE_NAME,
            "WHERE entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"SELECT COUNT(id) FROM", TABLE_NAME, "WHERE entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"UPDATE", TABLE_NAME, "SET status=#{status} where id=#{id}"})
    int updateComment(@Param("id") int id, @Param("status") int status);

    @Select({"select count(id) from ", TABLE_NAME, " where user_id=#{userId}"})
    int getUserCommentCount(int userId);
}
