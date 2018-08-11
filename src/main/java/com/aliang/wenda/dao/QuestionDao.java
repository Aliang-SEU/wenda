package com.aliang.wenda.dao;

import com.aliang.wenda.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName QuestionDao
 * @Author Aliang
 * @Date 2018/8/8 14:43
 * @Version 1.0
 **/
@Mapper
public interface QuestionDao {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title, content, created_date, user_id, comment_count";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Select({"SELECT", SELECT_FIELDS, "from", TABLE_NAME, "Where id=#{id}"})
    Question selectById(int id);

    @Insert({"INSERT INTO", TABLE_NAME, "(", INSERT_FIELDS,
            ") VALUES(#{title}, #{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    /**
     * 查询最新的question
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question getById(int id);
}
