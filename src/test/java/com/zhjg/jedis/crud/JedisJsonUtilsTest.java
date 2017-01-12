package com.zhjg.jedis.crud;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.zhjg.ssm.pojo.User;
import com.zhjg.ssm.template.JedisCacheTemplate;

/**
 * 测试JedisCacheTemplate
 * @author 327084
 *
 */
public class JedisJsonUtilsTest {

	private static JedisCacheTemplate template;
	private static Jedis jedis;
	static{
		jedis = new Jedis("localhost", 6379);
		template = new JedisCacheTemplate();
		template.setJedis(jedis);
	}

//----------------------------------------value作为字符串保存在redis服务器------------------------------------------------------------------
	
	@Test//元素为String类型
	public void testGetCollectionString(){
		try {
			List<String> list = (List<String>)template.getCollection("users", String.class, ArrayList.class);
			Set<String> set = (Set<String>)template.getCollection("users", String.class, HashSet.class);
			if(list != null){
				for (String str : list) {
					System.out.println(str);
				}
			}else{
				System.out.println(list);
			}
			if(set != null){
				Iterator<String> iterator = set.iterator();
				while(iterator.hasNext()){
					System.out.println(iterator.next());
				}
			}else{
				System.out.println(set);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetCollection(){
		try {
			List<User> list = (List<User>)template.getCollection("users", User.class, ArrayList.class);
			Set<User> set = (Set<User>)template.getCollection("users", User.class, HashSet.class);
			if(list != null){
				for (User user : list) {
					System.out.println(user.getName());
				}
			}else{
				System.out.println(list);
			}
			if(set != null){
				Iterator<User> iterator = set.iterator();
				while(iterator.hasNext()){
					System.out.println(iterator.next().getName());
				}
			}else{
				System.out.println(set);
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSet(){
		Set<User> set =  template.getSet("users", User.class);
		System.out.println(set);
	}
	
	@Test
	public void testUpdateList(){
		List<String> strs = new ArrayList<String>();
		strs.add("a");
		strs.add("b");
		strs.add("c");
		strs.add("d");
		template.updateList("users", strs);
		List<String> value = template.getList("users", String.class);
		if(value != null){
			for (String str : value) {
				System.out.println(str);
			}
		}else{
			System.out.println(value);
		}
	}
	
	@Test
	public void testDeleteList(){
		template.deleteList("users");
	}
	
	@Test
	public void testGetList(){
		List<User> users = template.getList("users", User.class);
		if(users != null){
			for (User user : users) {
				System.out.println(user.getName());
			}
		}else{
			System.out.println(users);
		}
		
	}
	
	@Test
	public void testAddList(){
		List<User>users = new ArrayList<User>();
		users.add(new User("1", "jack", "132"));
		users.add(new User("2", "tom", "132"));
		users.add(new User("3", "mike", "132"));
		template.addList("users", users);
	}
	
	
	@Test
	public void testAddObject(){
		User user = new User("5", "zhjg", "123");
		template.addSimpleObject("user", user);
	}
	
	@Test
	public void testGetObject(){
		User user = template.getSimpleObject("user", User.class);
		if(user != null){
			System.out.println(user.getName());
		}else{
			System.out.println(user);
		}
	}
	
	@Test
	public void testDeleteObject(){
		template.deleteSimpleObject("user");
	}
	
	@Test
	public void testUpdateObject(){
		User user = new User("6", "sss", "123");
		template.updateSimpleObject("user", user);
	}

	//----------------------------------------value作为字符串保存在redis服务器------------------------------------------------------------------
}
