package com.label.redis.service.impl;

import com.label.redis.dao.AbRedisConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: MuRunSen
 * @Date: 2018/11/2 14:56
 */
@Component
public class RedisInfoServiceImpl extends AbRedisConfiguration {

    @Resource(name = "redisInfoRecord")
    private RedisTemplate temple;

    public RedisTemplate getTemple() {
        return temple;
    }
}
