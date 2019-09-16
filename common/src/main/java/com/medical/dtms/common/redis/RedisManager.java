/**
 * BEYONDSOFT.COM INC
 */
package com.medical.dtms.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medical.dtms.common.util.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author yulijun
 * @version $Id: RedisManager.java, v 0.1 2018/8/3 17:55 by Exp $$
 * @Description redis操作类
 */
@Component
public class RedisManager {

    @Autowired
    private JedisPool jedisPool;


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @param seconds   超期时间
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, int seconds) {
        // 配置key value
        return RedisUtil.tryLock(this.getJedis(), lockKey, requestId, seconds);
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {
        return RedisUtil.releaseLock(this.getJedis(), lockKey, requestId);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public void set(final String key, String value) {
        this.getJedis().set(key, value);
    }

    /**
     * 写入缓存并设置有效时间
     *
     * @param key
     * @param value
     * @return
     */
    public void setEx(final String key, String value, int seconds) {
        Jedis jedis = this.getJedis();
        jedis.set(key, String.valueOf(value));
        jedis.expire(key, seconds);
    }

    /**
     * 批量删除对应的key
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的key
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            this.getJedis().del(key);
        }
    }

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return this.getJedis().exists(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return this.getJedis().get(key);
    }


    /**
     * 添加一个hash
     *
     * @param key
     * @param value
     */
    public void hmSet(String key, Map<String, String> value) {
        this.getJedis().hmset(key, value);
    }

    /**
     * 获取对应key的hash
     *
     * @param key
     * @return
     */
    public Map<String, String> hmGet(String key) {
        return this.getJedis().hgetAll(key);
    }

    /**
     * 往hash中添加一个元素
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hSet(String key, String hashKey, String value) {
        this.getJedis().hset(key, hashKey, value);
    }

    /**
     * 获取hash中对应的value
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String hGet(String key, String hashKey) {
        return this.getJedis().hget(key, hashKey);
    }


    /**
     * 列表左边添加
     *
     * @param key
     * @param value
     */
    public void lPush(String key, String value) {
        this.getJedis().lpush(key, value);
    }

    /**
     * 列表右边取出并删除，阻塞
     *
     * @param key
     * @return
     */
    public String brPop(String key) {
        return this.getJedis().rpop(key);
    }


    /**
     * 获取redis连接
     *
     * @return
     */
    private Jedis getJedis() {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis;
        } catch (Exception e) {
            throw new RuntimeException("获取redis连接失败", e);
        }
    }

}
