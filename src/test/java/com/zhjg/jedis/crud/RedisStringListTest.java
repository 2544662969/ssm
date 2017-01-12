package com.zhjg.jedis.crud;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.zhjg.ssm.template.JedisCacheTemplate;

public class RedisStringListTest {

	private static JedisCacheTemplate template;
	private static Jedis jedis;
	static{
		jedis = new Jedis("localhost", 6379);
		template = new JedisCacheTemplate();
		template.setJedis(jedis);
	}
//----------------------------------------value作为字符串列表保存在redis服务器的字符串列表------------------------------------------------------------------
	
	@Test
	public void testRedisStringList(){
		//获取字符串数组的长度; cmd:llen key
		long length = jedis.llen("menulist");
		System.out.println(length);
		//返回列表的第一个（下标为0）元素，并将该元素从列表中删除; cmd:lpop key
		System.out.println(jedis.lpop("menulist"));
		length = jedis.llen("menulist");
		System.out.println(length);
		//返回列表的最后一个（下标）元素，并将该元素从列表中删除; cmd:rpop key
		System.out.println(jedis.lpop("menulist"));
		length = jedis.llen("menulist");
		System.out.println(length);
		
	}
	
	@Test
	public void testlrem(){
		//当count>0从表头开始，删除count的绝对值个与value相同的元素
		//当count<0从表尾开始，删除count的绝对值个与value相同的元素
		//当count=0删除表中所有与value相同的元素
		//jedis.lpush("lrem", "zz", "zz", "xx", "yy", "zz", "zz");
		testlrange();
		jedis.lrem("lrem", -2, "zz");
		testlrange();
		
	}
	
	@Test
	public void testltrim(){
		//删除list中指定范围之外的所有元素
		jedis.ltrim("menulist", 0, 1);
		List<String> list = jedis.lrange("menulist", 0, jedis.llen("menulist")-1);
		System.out.println(list);
		
	}
	
	@Test
	public void testlrange(){
		//获取list指定下标范围内的value，可通过该方法获取list所有元素
		List<String> list = jedis.lrange("lrem", 0, jedis.llen("lrem")-1);
		System.out.println(list);
		
	}
	
	@Test
	public void testlset(){
		//修改list指定位置的value
		System.out.println(jedis.lindex("menuList", 0));
		jedis.lset("menuList", 0, "newValue");
		System.out.println(jedis.lindex("menuList", 0));
	}
	
	@Test
	public void testRename(){
		//重命名key
		String newKey = "menuList";
		jedis.renamenx("menulist", newKey);
		System.out.println(jedis.exists("menulist"));
		System.out.println(jedis.exists("menuList"));
	}
	
	@Test
	public void testrpop(){
		//返回列表最后一个元素并将其从列表中删除
		System.out.println("before>>>>>"+jedis.llen("menulist"));
		String firstEle = jedis.rpop("menulist");
		System.out.println(firstEle);
		System.out.println("after>>>>>"+jedis.llen("menulist"));
	}
	
	@Test
	public void testlpop(){
		//返回列表第一个元素并将其从列表中删除
		System.out.println("before>>>>>"+jedis.llen("menulist"));
		String firstEle = jedis.lpop("menulist");
		System.out.println(firstEle);
		System.out.println("after>>>>>"+jedis.llen("menulist"));
	}
	
	@Test
	public void testllen(){
		//获取字符串列表的长度; cmd:llen key
		long length = jedis.llen("menulist");
		System.out.println(length);
	}
	
	@Test
	public void testRPush(){
		jedis.del("menulist");
		//将一系列字符串作为字符串列表保存在redis; cmd:rpush key value1 value2....
		//r代表right，也就是从列表尾部插入
		jedis.rpush("menulist", "menu:create", "menu:delete", "menu:update", "menu:query");
		System.out.println(jedis.lindex("menulist", 0));
	}

	@Test
	public void testLPush(){
		jedis.del("menulist");
		//将一系列字符串作为字符串列表保存在redis; cmd:lpush key value1 value2....
		//l代表left，也就是从列表头部插入
		jedis.lpush("menulist", "menu:create", "menu:delete", "menu:update", "menu:query");
		System.out.println(jedis.lindex("menulist", 0));
	}

}