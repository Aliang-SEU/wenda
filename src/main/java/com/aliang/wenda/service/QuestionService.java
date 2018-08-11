package com.aliang.wenda.service;

import com.aliang.wenda.dao.QuestionDao;
import com.aliang.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @ClassName QuestionService
 * @Author Aliang
 * @Date 2018/8/8 15:23
 * @Version 1.0
 **/
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveService sensitiveService;

    public List<Question> getLatestQuestions(int userId, int offset, int limit){
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    /**
     * 增加一个question
     * @param question
     * @return
     */
    public int addQuestion(Question question){
        //脚本过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));

        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public Question selectById(int id){
        return questionDao.selectById(id);
    }

    public int updateCommentCount(int id, int count) {
        return questionDao.updateCommentCount(id, count);
    }

    public Question getById(int id) {
        return questionDao.getById(id);
    }
}
