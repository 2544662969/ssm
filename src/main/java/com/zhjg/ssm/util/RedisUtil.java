package com.zhjg.ssm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * ���湤���࣬ͨ��@Component������redisUtilΪid��bean
 * @author 327084
 *
 */

@Component("redisUtil")
public class RedisUtil{

	/**
	 * �Զ�װ��RedisTemplate,��String����Ϊkey
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
     * ��key,value��ֵ�Ա��浽redis
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
     * ȡkey��Ӧ��value����Ҫָ��value���ֽ���
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
     * ȡkey��Ӧ��value��valueΪString����ʱ����
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
