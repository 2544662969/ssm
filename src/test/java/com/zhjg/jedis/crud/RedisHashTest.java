package com.zhjg.jedis.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.zhjg.ssm.pojo.User;

/**
 * ����Jedis�в���Hash�ķ���
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
		//���key��Ӧ��hash�в�����ָ����field����Ӹ�field������ָ����value������1
		//����Ѿ����ڸ�field����������������0
		long value = jedis.hsetnx("user", "id", "2");
		System.out.println(value);
		System.out.println(jedis.hget("user", "id"));
		value = jedis.hsetnx("user", "age", "20");
		System.out.println(value);
		System.out.println(jedis.hget("user", "age"));
	}
	
	@Test
	public void testhvals(){
		//��ȡkey��Ӧ��hash�����е�field��Ӧ��value,����װ��List����
		List<String> list = jedis.hvals("user");
		System.out.println(list);
	}
	
	@Test
	public void testhmget(){
		//��ȡkey��Ӧ��hash��ָ����field��Ӧ��value,����װ��List����
		List<String> list = jedis.hmget("user", "id", "name");
		System.out.println(list);
	}
	
	@Test
	public void testhmset(){
		//��Map�еļ�ֵ����Ϊfield-value��ŵ�key��Ӧ��hash��
		Map<String, String> user = new HashMap<String, String>();
		user.put("id", "1");
		user.put("name", "zhangsan");
		user.put("password", "123");
		jedis.hmset("user", user);
		testhget();
	}
	
	@Test
	public void testhlen(){
		//����key��Ӧ��hash��field�ĸ���
		long count = jedis.hlen("user");
		System.out.println(count);
	}
	
	@Test
	public void testhkeys(){
		//��key��Ӧ��hash������field��Set��ʽ����
		Set<String> fields = jedis.hkeys("user");
		System.out.println(fields);
	}
	
	@Test
	public void testhincrBy(){
		//��key��Ӧ��hash��ָ����fieldֵ���Ӽ���������feild��Ӧ��value�����תΪInteger,���򱨴�
		System.out.println(jedis.hget("user", "id"));
		jedis.hincrBy("user", "id", 1);
		System.out.println(jedis.hget("user", "id"));
		jedis.hincrBy("user", "id", -2);
		System.out.println(jedis.hget("user", "id"));
	}
	
	@Test
	public void testhgetAll(){
		//��ȡkey��Ӧ��hash�����е�field-value�Բ���װ��map����
		Map<String, String> user = jedis.hgetAll("user");
		System.out.println(user.get("id"));
		System.out.println(user.get("name"));
		System.out.println(user.get("password"));
	}
	
	@Test
	public void testhdel(){
		//ɾ��key��Ӧ��hash��ָ����field
		System.out.println(jedis.hexists("user", "id"));
		jedis.hdel("user", "id");
		System.out.println(jedis.hexists("user", "id"));
	}
	
	@Test
	public void testhexists(){
		//�ж�key��Ӧ��hash���Ƿ����ĳ��field
		System.out.println(jedis.hexists("user", "id"));
		System.out.println(jedis.hexists("user", "age"));
	}
	
	@Test
	public void testhget(){
		//ȡkey��ĳ��field��Ӧ��value
		System.out.println(jedis.hget("user", "id"));
		System.out.println(jedis.hget("user", "name"));
		System.out.println(jedis.hget("user", "password"));
	}
	
	@Test
	public void testhset(){
		//��ͬһ��key�´�Ŷ��field-value�ԣ��ʺ�������Ŷ���
		User user = new User("1", "zhangsan", "123"); 
		jedis.hset("user", "id", user.getId());
		jedis.hset("user", "name", user.getName());
		jedis.hset("user", "password", user.getPassword());
	}
}
