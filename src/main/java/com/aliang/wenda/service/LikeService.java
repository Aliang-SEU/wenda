package com.aliang.wenda.service;

import com.aliang.wenda.utils.JedisAdapter;
import com.aliang.wenda.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/9 23:09
 * @Version 1.0
 **/
@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;


    /**
     * 获取喜欢的数量
     * @param entityType
     * @param entityId
     * @return
     */
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    /**
     * 获取用户对回答的状态
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId, int entityType, int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sIsmenber(likeKey, String.valueOf(userId))){
            return 1;
        }

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sIsmenber(disLikeKey, String.valueOf(userId)) ? -1 : 0;

    }

    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //添加一个赞
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));

        //返回当前的关注人数
        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId, int entityType, int entityId) {

        //添加一个踩
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        //删除一个赞
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        //返回当前的关注人数
        return jedisAdapter.scard(likeKey);
    }
}
