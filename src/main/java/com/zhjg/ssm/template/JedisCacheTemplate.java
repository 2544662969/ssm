package com.zhjg.ssm.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 缓存类：
 * jedis与spring整合时操作redis缓存的模版类,依赖fastjson
 * 1、String类型的key,value的CRUD
 * 2、String类型的key,自定义类型的value的CRUD
 * 3、String类型的key,集合类型的value的CRUD
 * 所有上述类型的value存储到redis服务器都会被转化为JSON串
 * @author 327084
 *
 */
public class JedisCacheTemplate {

	private static Class<?> DEFAULT_COLLECTION_TYPE = ArrayList.class;
	
	private Jedis jedis;
	
	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	/**
	 * 根据key添加字符串类型的value
	 * @param key
	 * @param value
	 */
	public void add(String key, String value){
		jedis.set(key, value);
		
	}
	
	/**
	 * 删除字符串类型的key,value
	 * @param key
	 * @param value
	 */
	public void delete(String key){
		jedis.del(key);
	}
	
	/**
	 * 修改key对应的value
	 * @param key
	 * @param value
	 */
	public void update(String key, String newValue){
		delete(key);
		add(key, newValue);
	}
	
	/**
	 * 查询：字符串类型的key,value
	 * @param key
	 * @param value
	 */
	public String get(String key){
		return jedis.get(key);
	}
	
	/**
	 * 添加：自定义类型value
	 * @param key
	 * @param value
	 */
	public void addSimpleObject(String key, Object value){
		jedis.set(key, JSON.toJSONString(value));
	}
	
	/**
	 * 删除自定义类型value
	 * @param key
	 * @param value
	 */
	public void deleteSimpleObject(String key){
		delete(key);
	}
	
	/**
	 * 修改：自定义类型value
	 * @param key
	 * @param value
	 */
	public void updateSimpleObject(String key, Object newValue){
		deleteSimpleObject(key);
		addSimpleObject(key, newValue);
	}
	
	/**
	 * 查询：自定义类型value
	 * @param key
	 * @param value
	 */
	public <T> T getSimpleObject(String key, Class<T> clazz){
		String json = get(key);
		if(json != null && !json.trim().equals("")){
			return (T)JSON.parseObject(json, clazz);
		}
		return null;
	}
	
	/**
	 * 添加List类型的value
	 * @param key
	 * @param value
	 */
	public void addList(String key, List<?> value){
		addSimpleObject(key, value);
	}
	
	
	/**
	 * 根据key获取value,并将value转化为ArrayList类型
	 * @param key
	 * @param elemClazz:集合元素类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key, Class<T> elemClazz){
		String json = get(key);
		List<T> list = new ArrayList<T>();
		if(json == null || json.trim().equals("")){
			return null;
		}
		JSONArray array = JSON.parseArray(json);
		if(array == null || array.size() <= 0){
			return null;
		}
		for (int i=0;i<array.size();i++) {
			if(elemClazz.getName().equals("java.lang.String")){
				list.add((T)array.get(i));
			}else{
				JSONObject obj = array.getJSONObject(i);
				list.add(JSON.parseObject(JSON.toJSONString(obj), elemClazz));
			}
		}
		return list; 
	}
	
	/**
	 * 删除List类型的value
	 * @param key
	 */
	public void deleteList(String key){
		delete(key);
	}
	
	/**
	 * 修改List类型的value
	 * @param key
	 * @param newValue
	 */
	public void updateList(String key, List<?> newValue){
		deleteList(key);
		addList(key, newValue);
	}
	
	/**
	 * 根据key获取value,并将value转化为HashSet类型
	 * @param key
	 * @param elemClazz：集合元素类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Set<T> getSet(String key, Class<T> elemClazz){
		String json = jedis.get(key);
		Set<T> set = new HashSet<T>();
		if(json == null || json.trim().equals("")){
			return null;
		}
		JSONArray array = JSON.parseArray(json);
		if(array == null || array.size() <= 0){
			return null;
		}
		for (int i=0;i<array.size();i++) {
			if(elemClazz.getName().equals("java.lang.String")){
				set.add((T)array.get(i));
			}else{
				JSONObject obj = array.getJSONObject(i);
				set.add(JSON.parseObject(JSON.toJSONString(obj), elemClazz));
			}
		}
		return set;
	}
	
	/**
	 * 根据key获取value，并将value转化为指定的集合
	 * @param key
	 * @param elemClazz：集合元素类型
	 * @param collectionType:集合类型
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> Collection<T> getCollection(String key, Class<T> elemClazz, Class collectionType) throws InstantiationException, IllegalAccessException{
		String json = jedis.get(key);
		if(collectionType == null){
			collectionType = DEFAULT_COLLECTION_TYPE;
		}
		Collection<T> collection = (Collection<T>)collectionType.newInstance();
		if(json == null || json.trim().equals("")){
			return null;
		}
		JSONArray array = JSON.parseArray(json);
		if(array == null || array.size() <= 0){
			return null;
		}
		for (int i=0;i<array.size();i++) {
			if(elemClazz.getName().equals("java.lang.String")){
				collection.add((T)array.get(i));
			}else{
				JSONObject obj = array.getJSONObject(i);
				collection.add(JSON.parseObject(JSON.toJSONString(obj), elemClazz));
			}
		}
		return collection;
	}

}
