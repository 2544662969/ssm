package com.zhjg.ssm.rmi;

/**
 * 接受远程客户端调用;项目service接口都可以作为远程调用接口为方便查看单独建包
 * @author 327084
 *
 */
public interface BankCardBalanceService {
	//根据卡号查询账户余额
	public double getBalance(String cardNum);

}
