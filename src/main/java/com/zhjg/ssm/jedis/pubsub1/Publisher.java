package com.zhjg.ssm.jedis.pubsub1;

import redis.clients.jedis.Jedis;

public class Publisher {

	private String name;
	private Jedis jedis;
	private String channel;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Publisher(String name, Jedis jedis) {
		super();
		this.name = name;
		this.jedis = jedis;
	}

	public void publish(String message){
		jedis.publish(channel, message);
		System.out.println(Thread.currentThread().getName()+":"+name+"在频道"+channel+"发不了一条消息："+message);
	}
}
