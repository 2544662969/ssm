package com.zhjg.ssm.rmi.impl;

import com.zhjg.ssm.rmi.BankCardBalanceService;

public class BankCardBalanceServiceImpl implements BankCardBalanceService{

	@Override
	public double getBalance(String cardNum) {
		//模拟连接数据库
		double balance = Math.random()*100;
		System.out.println("server: balance="+balance);
		return balance;
	}

}
