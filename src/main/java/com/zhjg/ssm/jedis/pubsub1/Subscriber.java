package com.zhjg.ssm.jedis.pubsub1;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub{

	private String name;
	
	public Subscriber(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void onMessage(String channel, String message) {
		System.out.println(Thread.currentThread().getName()+":"+ name+"收到了来自频道"+channel+"的消息："+message);
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println(Thread.currentThread().getName()+":"+name+"订阅了频道"+channel);
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println(Thread.currentThread().getName()+":"+name+"取消了对频道"+channel+"的订阅");
	}

}
