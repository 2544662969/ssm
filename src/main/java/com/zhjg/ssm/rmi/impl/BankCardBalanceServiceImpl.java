package com.zhjg.ssm.rmi.impl;

import com.zhjg.ssm.rmi.BankCardBalanceService;

public class BankCardBalanceServiceImpl implements BankCardBalanceService{

	@Override
	public double getBalance(String cardNum) {
		//ģ���������ݿ�
		double balance = Math.random()*100;
		System.out.println("server: balance="+balance);
		return balance;
	}

}
