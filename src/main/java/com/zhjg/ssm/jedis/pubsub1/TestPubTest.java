package com.zhjg.ssm.jedis.pubsub1;

import redis.clients.jedis.Jedis;

public class TestPubTest {

	public static void main(String[] args) throws InterruptedException {
		String channel1 = "channel1";
		String channel2 = "channel2";
		
		Subscriber s1 = new Subscriber("s1");
		Subscriber s2 = new Subscriber("s2");
		
		SubscribeThread t1 = new SubscribeThread(s1, channel1);
		t1.start();
		Thread.sleep(1000);
		
		
		SubscribeThread t2 = new SubscribeThread(s1, channel2);
		t2.start();
		Thread.sleep(1000);
		
		SubscribeThread t3 = new SubscribeThread(s2, channel1);
		t3.start();
		Thread.sleep(1000);
		
		Jedis jedis = new Jedis("localhost", 6379);
		Publisher p1 = new Publisher("p1", jedis);
		Publisher p2 = new Publisher("p2", jedis);
		
		p1.setChannel(channel1);
		p2.setChannel(channel2);
		
		Thread.sleep(5000);
		p1.publish("aaaaaaaaaaaaa");
		Thread.sleep(5000);
		p2.publish("bbbbbbbbbbbbb");
		
		
		
	}
}
