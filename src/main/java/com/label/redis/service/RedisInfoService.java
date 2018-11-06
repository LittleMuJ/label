package com.label.redis.service;

import com.label.redis.service.impl.RedisInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: MuRunSen
 * @Date: 2018/11/2 14:54
 */
@Service
public class RedisInfoService {

    @Autowired
    private RedisInfoServiceImpl redisInfoService;

    //新增一个字符串类型的值，key是键，value是值
    public void setString(String key,String value){
        redisInfoService.setData(key,value);
    }

    public void setString(String key,Integer value){
        redisInfoService.setData(key,value);
    }

    //根据key值查找value
    public Object getString(String key){

        Object result = redisInfoService.getData(key);

        return result;
    }

    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (redisInfoService.exists(key)) {
            redisInfoService.delete(key);
        }
    }

    public void set(String key,String value,long timeout,TimeUnit unit) {
        redisInfoService.set(key,value,timeout,unit);
    }
}
