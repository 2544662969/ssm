package com.zhjg.ssm.jedis.pubsub1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class SubscribeThread extends Thread{

	private Jedis jedis = new Jedis("localhost", 6379);
	private JedisPubSub subscriber;
	private String channel;
	
	public SubscribeThread(JedisPubSub subscriber, String channel) {
		super();
		this.subscriber = subscriber;
		this.channel = channel;
	}

	public Jedis getJedis() {
		return jedis;
	}
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public JedisPubSub getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(JedisPubSub subscriber) {
		this.subscriber = subscriber;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public void run() {
		jedis.subscribe(subscriber, channel);
	}

	
}
