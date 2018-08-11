package com.aliang.wenda;

import com.aliang.wenda.WendaApplication;
import com.aliang.wenda.dao.QuestionDao;
import com.aliang.wenda.dao.UserDao;
import com.aliang.wenda.model.EntityType;
import com.aliang.wenda.model.Question;
import com.aliang.wenda.model.User;
import com.aliang.wenda.service.FollowService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= WendaApplication.class)
public class InitDatabaseTests {

    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    FollowService followService;

    @Test
    public void initDatabase() {
        Random random = new Random();
        for(int i = 0; i < 11; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);
            for (int j = 1; j < i; ++j) {
                followService.follow(j, EntityType.ENTITY_USER, i);
            }

            user.setPassword("newpassword");
            userDao.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreatedDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("Balaababalalalal Content %d", i));
            questionDao.addQuestion(question);
            user.setPassword("xx");
            userDao.updatePassword(user);
        }

        //Assert.assertEquals("xx", userDao.selectById(1).toString());

        userDao.deleteById(1);
        //Assert.assertEquals(null, userDao.selectById(1));
    }

    @Test
    public void testQuestionTables(){
        Question question = new Question();
        question.setCommentCount(10);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 5 * 10);
        question.setCreatedDate(date);
        question.setUserId(11);
        question.setTitle(String.format("TITLE{%d}", 10));
        question.setContent(String.format("Balasdadas Content %d", 10));
        questionDao.addQuestion(question);
    }

    @Test
    public void testLatestQuestion(){
        //Question question = new Question();
        List<Question> list = questionDao.selectLatestQuestions(0,0,10);
        for(Question q : list)
            System.out.println(q.toString());
    }
}