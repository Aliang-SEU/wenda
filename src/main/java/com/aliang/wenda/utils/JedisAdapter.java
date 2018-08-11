package com.aliang.wenda.utils;

import com.aliang.wenda.model.User;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/9 21:08
 * @Version 1.0
 **/
@Service
public class JedisAdapter implements InitializingBean{

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://localhost:6379/10");

    }

    public long sadd(String key, String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.sadd(key, value);
        }catch(Exception e){
            logger.error("redis保存发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }catch(Exception e){
            logger.error("redis发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public long srem(String key, String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.srem(key, value);
        }catch(Exception e){
            logger.error("redis发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return 0;
    }

    public boolean sIsmenber(String key, String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.sismember(key, value);
        }catch(Exception e){
            logger.error("redis保存发生异常" + e.getMessage());
        }finally {
            if(jedis != null)
                jedis.close();
        }
        return false;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 返回一个redis连接
     * @return
     */
    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 返回一个事务
     * @param jedis
     * @return
     */
    public Transaction multi(Jedis jedis){
        try{
            return jedis.multi();
        }catch(Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {

        }
        return null;
    }

    /**
     * 提交一个事务
     * @param tx
     * @param jedis
     * @return
     */
    public List<Object> exec(Transaction tx, Jedis jedis){
        try{
            return tx.exec();
        }catch(Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if(tx != null){
                try{
                    //无论如何都要关闭事务
                    tx.close();
                }catch(Exception e){
                    logger.error("发生异常" + e.getMessage());
                }
            }
            //顺便关闭redis连接
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key, double score, String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zadd(key, score, value);
        }catch(Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    //获取指定索引内的set集合
    public Set<String> zrange(String key, int start, int end){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.zrange(key, start, end);
        }catch(Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    //倒序获取指定索引内的set集合
    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 获取指定key集合的所有数目
     * @param key
     * @return
     */
    public long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    /**
     * 判断是否含有某个实体
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
