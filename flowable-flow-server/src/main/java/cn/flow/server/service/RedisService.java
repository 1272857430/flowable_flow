package cn.flow.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 用户流程发起，避免重复发起的时间锁
 */
@Service
public class RedisService {

    private static final String LOCK_PREFIX = "START_FLOW_";
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 加锁
     */
    public boolean saveLock(String redisKey, int lockTime){
        try {
            ValueOperations<String,String> operations = redisTemplate.opsForValue();
            if (!StringUtils.isEmpty(operations.get(LOCK_PREFIX + redisKey))){
                logger.error("流程已经被发起过，加锁失败！");
                return false;
            }
            operations.set(LOCK_PREFIX + redisKey, redisKey, lockTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error("加锁失败！");
        }
        return false;
    }

    /**
     * 释放锁
     */
    public boolean releaseLock(String redisKey){
        try {
            redisTemplate.delete(LOCK_PREFIX + redisKey);
            return true;
        } catch (Exception e) {
            logger.error("释放锁失败", e);
        }
        return false;
    }
}
