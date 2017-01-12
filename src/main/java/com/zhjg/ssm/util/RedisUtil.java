package com.zhjg.ssm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 缓存工具类，通过@Component配置以redisUtil为id的bean
 * @author 327084
 *
 */

@Component("redisUtil")
public class RedisUtil{

	/**
	 * 自动装配RedisTemplate,以String类型为key
	 */
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	public void put(String key, String value) {  
        if (key==null || "".equals(key)) {  
            return;  
        }  
        redisTemplate.opsForHash().put(key, key, value);  
          
    }  
  
    /**
     * 将key,value键值对保存到redis
     * @param key
     * @param value
     */
    public void put(String key, Object value) {  
        if (key==null || "".equals(key)) {  
            return;  
        }  
        redisTemplate.opsForHash().put(key, key, new Gson().toJson(value));  
          
    }  
  
    /**
     * 取key对应的value，需要指定value的字节码
     * @param key
     * @param className
     * @return
     */
    public <T> T get(String key, Class<T> className) {  
        Object obj = redisTemplate.opsForHash().get(key, key);  
        if(obj == null){  
            return null;  
        }  
        return new Gson().fromJson(""+obj, className);  
    }  
  
    /**
     * 取key对应的value，value为String类型时适用
     * @param key
     * @return
     */
    public String get(String key) {  
        Object obj = redisTemplate.opsForHash().get(key, key);  
        if(obj == null){  
            return null;  
        }else{  
            return String.valueOf(obj);  
        }  
    }
}
