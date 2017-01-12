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
		System.out.println(Thread.currentThread().getName()+":"+ name+"�յ�������Ƶ��"+channel+"����Ϣ��"+message);
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println(Thread.currentThread().getName()+":"+name+"������Ƶ��"+channel);
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println(Thread.currentThread().getName()+":"+name+"ȡ���˶�Ƶ��"+channel+"�Ķ���");
	}

}
