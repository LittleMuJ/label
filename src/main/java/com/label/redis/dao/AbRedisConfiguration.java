package com.label.redis.dao;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author: MuRunSen
 * @Date: 2018/11/2 14:58
 */
//redis抽象类
public abstract class AbRedisConfiguration {

    protected RedisTemplate template;

    public RedisTemplate getTemple() {
        return template;
    }

    //保存值为字符串类型
    public void setData(String key,String value){
          getTemple().opsForValue().set(key,value);
    }

    //保存值为Integer类型
    public void setData(String key,Integer value){
          getTemple().opsForValue().set(key,value);
    }

    public Object getData(String key) {
        return getTemple().opsForValue().get(key);
    }

    public void delete(String key) {
        getTemple().delete(key);
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return template.hasKey(key);
    }

    //设置含有过期时间
    public  void set(String key, String value, long timeout, TimeUnit unit){
        getTemple().opsForValue().set(key,value,timeout,unit);
    }
}
