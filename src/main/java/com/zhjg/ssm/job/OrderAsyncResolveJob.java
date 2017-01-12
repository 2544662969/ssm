package com.zhjg.ssm.job;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.zhjg.ssm.pojo.Order;
import com.zhjg.ssm.template.JedisCacheTemplate;

public class OrderAsyncResolveJob {

	@Autowired
	private JedisCacheTemplate template;
	
	public void dealOrder(){
		long length = template.getJedis().llen("orderlist");
		if(length > 0){
			String tmp = template.getJedis().rpop("orderlist");
			Order order = JSON.parseObject(tmp, Order.class);
			System.out.println("已处理订单>>"+order.toString());
		}else{
			System.out.println("没有未处理订单");
		}
	}
	
	public static void main(String[] args) {
		//生成订单
		for(int i=0;i<100;i++){
			Order order = new Order();
			order.setOrderId(UUID.randomUUID().toString());
			order.setProductId(UUID.randomUUID().toString());
			order.setUserId(UUID.randomUUID().toString());
			int count = (int)(Math.random()*100);
			order.setCount(count);
			order.setMoney(Math.random()*10000);
			order.setCreate(new Date());
			order.setStatus("0");
			Jedis jedis = new Jedis("localhost",6379);
			jedis.lpush("orderlist", JSON.toJSONString(order));
		}
	}
}
