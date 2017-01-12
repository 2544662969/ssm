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
 * �����ࣺ
 * jedis��spring����ʱ����redis�����ģ����,����fastjson
 * 1��String���͵�key,value��CRUD
 * 2��String���͵�key,�Զ������͵�value��CRUD
 * 3��String���͵�key,�������͵�value��CRUD
 * �����������͵�value�洢��redis���������ᱻת��ΪJSON��
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
	 * ����key����ַ������͵�value
	 * @param key
	 * @param value
	 */
	public void add(String key, String value){
		jedis.set(key, value);
		
	}
	
	/**
	 * ɾ���ַ������͵�key,value
	 * @param key
	 * @param value
	 */
	public void delete(String key){
		jedis.del(key);
	}
	
	/**
	 * �޸�key��Ӧ��value
	 * @param key
	 * @param value
	 */
	public void update(String key, String newValue){
		delete(key);
		add(key, newValue);
	}
	
	/**
	 * ��ѯ���ַ������͵�key,value
	 * @param key
	 * @param value
	 */
	public String get(String key){
		return jedis.get(key);
	}
	
	/**
	 * ��ӣ��Զ�������value
	 * @param key
	 * @param value
	 */
	public void addSimpleObject(String key, Object value){
		jedis.set(key, JSON.toJSONString(value));
	}
	
	/**
	 * ɾ���Զ�������value
	 * @param key
	 * @param value
	 */
	public void deleteSimpleObject(String key){
		delete(key);
	}
	
	/**
	 * �޸ģ��Զ�������value
	 * @param key
	 * @param value
	 */
	public void updateSimpleObject(String key, Object newValue){
		deleteSimpleObject(key);
		addSimpleObject(key, newValue);
	}
	
	/**
	 * ��ѯ���Զ�������value
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
	 * ���List���͵�value
	 * @param key
	 * @param value
	 */
	public void addList(String key, List<?> value){
		addSimpleObject(key, value);
	}
	
	
	/**
	 * ����key��ȡvalue,����valueת��ΪArrayList����
	 * @param key
	 * @param elemClazz:����Ԫ������
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
	 * ɾ��List���͵�value
	 * @param key
	 */
	public void deleteList(String key){
		delete(key);
	}
	
	/**
	 * �޸�List���͵�value
	 * @param key
	 * @param newValue
	 */
	public void updateList(String key, List<?> newValue){
		deleteList(key);
		addList(key, newValue);
	}
	
	/**
	 * ����key��ȡvalue,����valueת��ΪHashSet����
	 * @param key
	 * @param elemClazz������Ԫ������
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
	 * ����key��ȡvalue������valueת��Ϊָ���ļ���
	 * @param key
	 * @param elemClazz������Ԫ������
	 * @param collectionType:��������
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
