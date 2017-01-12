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
//----------------------------------------value��Ϊ�ַ����б�����redis���������ַ����б�------------------------------------------------------------------
	
	@Test
	public void testRedisStringList(){
		//��ȡ�ַ�������ĳ���; cmd:llen key
		long length = jedis.llen("menulist");
		System.out.println(length);
		//�����б�ĵ�һ�����±�Ϊ0��Ԫ�أ�������Ԫ�ش��б���ɾ��; cmd:lpop key
		System.out.println(jedis.lpop("menulist"));
		length = jedis.llen("menulist");
		System.out.println(length);
		//�����б�����һ�����±꣩Ԫ�أ�������Ԫ�ش��б���ɾ��; cmd:rpop key
		System.out.println(jedis.lpop("menulist"));
		length = jedis.llen("menulist");
		System.out.println(length);
		
	}
	
	@Test
	public void testlrem(){
		//��count>0�ӱ�ͷ��ʼ��ɾ��count�ľ���ֵ����value��ͬ��Ԫ��
		//��count<0�ӱ�β��ʼ��ɾ��count�ľ���ֵ����value��ͬ��Ԫ��
		//��count=0ɾ������������value��ͬ��Ԫ��
		//jedis.lpush("lrem", "zz", "zz", "xx", "yy", "zz", "zz");
		testlrange();
		jedis.lrem("lrem", -2, "zz");
		testlrange();
		
	}
	
	@Test
	public void testltrim(){
		//ɾ��list��ָ����Χ֮�������Ԫ��
		jedis.ltrim("menulist", 0, 1);
		List<String> list = jedis.lrange("menulist", 0, jedis.llen("menulist")-1);
		System.out.println(list);
		
	}
	
	@Test
	public void testlrange(){
		//��ȡlistָ���±귶Χ�ڵ�value����ͨ���÷�����ȡlist����Ԫ��
		List<String> list = jedis.lrange("lrem", 0, jedis.llen("lrem")-1);
		System.out.println(list);
		
	}
	
	@Test
	public void testlset(){
		//�޸�listָ��λ�õ�value
		System.out.println(jedis.lindex("menuList", 0));
		jedis.lset("menuList", 0, "newValue");
		System.out.println(jedis.lindex("menuList", 0));
	}
	
	@Test
	public void testRename(){
		//������key
		String newKey = "menuList";
		jedis.renamenx("menulist", newKey);
		System.out.println(jedis.exists("menulist"));
		System.out.println(jedis.exists("menuList"));
	}
	
	@Test
	public void testrpop(){
		//�����б����һ��Ԫ�ز�������б���ɾ��
		System.out.println("before>>>>>"+jedis.llen("menulist"));
		String firstEle = jedis.rpop("menulist");
		System.out.println(firstEle);
		System.out.println("after>>>>>"+jedis.llen("menulist"));
	}
	
	@Test
	public void testlpop(){
		//�����б��һ��Ԫ�ز�������б���ɾ��
		System.out.println("before>>>>>"+jedis.llen("menulist"));
		String firstEle = jedis.lpop("menulist");
		System.out.println(firstEle);
		System.out.println("after>>>>>"+jedis.llen("menulist"));
	}
	
	@Test
	public void testllen(){
		//��ȡ�ַ����б�ĳ���; cmd:llen key
		long length = jedis.llen("menulist");
		System.out.println(length);
	}
	
	@Test
	public void testRPush(){
		jedis.del("menulist");
		//��һϵ���ַ�����Ϊ�ַ����б�����redis; cmd:rpush key value1 value2....
		//r����right��Ҳ���Ǵ��б�β������
		jedis.rpush("menulist", "menu:create", "menu:delete", "menu:update", "menu:query");
		System.out.println(jedis.lindex("menulist", 0));
	}

	@Test
	public void testLPush(){
		jedis.del("menulist");
		//��һϵ���ַ�����Ϊ�ַ����б�����redis; cmd:lpush key value1 value2....
		//l����left��Ҳ���Ǵ��б�ͷ������
		jedis.lpush("menulist", "menu:create", "menu:delete", "menu:update", "menu:query");
		System.out.println(jedis.lindex("menulist", 0));
	}

}