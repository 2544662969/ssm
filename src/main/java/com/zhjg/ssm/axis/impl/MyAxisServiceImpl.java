package com.zhjg.ssm.axis.impl;

import com.zhjg.ssm.axis.MyAxisService;

public class MyAxisServiceImpl implements MyAxisService{

	@Override
	public void sayHello(String name) {
		System.out.println("name>>>>>>>>>>>>"+name);
	}

}
