package com.aliang.wenda.service;

import com.aliang.wenda.dao.CommentDao;
import com.aliang.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/9 17:04
 * @Version 1.0
 **/
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    SensitiveService sensitiveService;

    /**
     * 获取评论
     * @param entityId
     * @param entityType
     * @return
     */
    public List<Comment> getCommentsByEntity(int entityId, int entityType){
        return commentDao.selectCommentByEntity(entityId, entityType);
    }

    /**
     * 新增一个评论
     * @param comment
     * @return
     */
    public int addComment(Comment comment){
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDao.addComment(comment) > 0 ? comment.getId() : 0;
    }

    /**
     * 获取评论数目
     * @param entityId
     * @param entityType
     * @return
     */
    public int getCommentCount(int entityId, int entityType){
        return commentDao.getCommentCount(entityId, entityType);
    }

    /**
     * 删除一个评论
     * @param commentId
     * @return
     */
    public boolean deleteComment(int commentId){
        return commentDao.updateComment(commentId, 1) > 0;
    }


    /**
     * 通过id查询评论
     * @param id
     * @return
     */
    public Comment getCommentById(int id) {
        return commentDao.getCommentById(id);
    }


    public int getUserCommentCount(int userId) {
        return commentDao.getUserCommentCount(userId);
    }
}
