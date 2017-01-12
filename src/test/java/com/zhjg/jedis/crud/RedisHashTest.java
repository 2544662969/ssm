package com.zhjg.jedis.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.zhjg.ssm.pojo.User;

/**
 * 测试Jedis中操作Hash的方法
 * @author 327084
 *
 */
public class RedisHashTest {

	private static Jedis jedis;
	static{
		jedis = new Jedis("localhost", 6379);
	}

	@Test
	public void testhsetnx(){
		//如果key对应的hash中不存在指定的field，添加该field并设置指定的value，返回1
		//如果已经存在该field，不做操作并返回0
		long value = jedis.hsetnx("user", "id", "2");
		System.out.println(value);
		System.out.println(jedis.hget("user", "id"));
		value = jedis.hsetnx("user", "age", "20");
		System.out.println(value);
		System.out.println(jedis.hget("user", "age"));
	}
	
	@Test
	public void testhvals(){
		//获取key对应的hash中所有的field对应的value,并封装成List返回
		List<String> list = jedis.hvals("user");
		System.out.println(list);
	}
	
	@Test
	public void testhmget(){
		//获取key对应的hash中指定的field对应的value,并封装成List返回
		List<String> list = jedis.hmget("user", "id", "name");
		System.out.println(list);
	}
	
	@Test
	public void testhmset(){
		//将Map中的键值对作为field-value存放到key对应的hash中
		Map<String, String> user = new HashMap<String, String>();
		user.put("id", "1");
		user.put("name", "zhangsan");
		user.put("password", "123");
		jedis.hmset("user", user);
		testhget();
	}
	
	@Test
	public void testhlen(){
		//返回key对应的hash中field的个数
		long count = jedis.hlen("user");
		System.out.println(count);
	}
	
	@Test
	public void testhkeys(){
		//将key对应的hash中所有field以Set形式返回
		Set<String> fields = jedis.hkeys("user");
		System.out.println(fields);
	}
	
	@Test
	public void testhincrBy(){
		//对key对应的hash中指定的field值做加减操作，该feild对应的value必须可转为Integer,否则报错
		System.out.println(jedis.hget("user", "id"));
		jedis.hincrBy("user", "id", 1);
		System.out.println(jedis.hget("user", "id"));
		jedis.hincrBy("user", "id", -2);
		System.out.println(jedis.hget("user", "id"));
	}
	
	@Test
	public void testhgetAll(){
		//获取key对应的hash中所有的field-value对并封装成map返回
		Map<String, String> user = jedis.hgetAll("user");
		System.out.println(user.get("id"));
		System.out.println(user.get("name"));
		System.out.println(user.get("password"));
	}
	
	@Test
	public void testhdel(){
		//删除key对应的hash中指定的field
		System.out.println(jedis.hexists("user", "id"));
		jedis.hdel("user", "id");
		System.out.println(jedis.hexists("user", "id"));
	}
	
	@Test
	public void testhexists(){
		//判断key对应的hash中是否存在某个field
		System.out.println(jedis.hexists("user", "id"));
		System.out.println(jedis.hexists("user", "age"));
	}
	
	@Test
	public void testhget(){
		//取key下某个field对应的value
		System.out.println(jedis.hget("user", "id"));
		System.out.println(jedis.hget("user", "name"));
		System.out.println(jedis.hget("user", "password"));
	}
	
	@Test
	public void testhset(){
		//在同一个key下存放多对field-value对，适合用来存放对象
		User user = new User("1", "zhangsan", "123"); 
		jedis.hset("user", "id", user.getId());
		jedis.hset("user", "name", user.getName());
		jedis.hset("user", "password", user.getPassword());
	}
}
