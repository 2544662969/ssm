package com.zhjg.ssm.rmi;

/**
 * ����Զ�̿ͻ��˵���;��Ŀservice�ӿڶ�������ΪԶ�̵��ýӿ�Ϊ����鿴��������
 * @author 327084
 *
 */
public interface BankCardBalanceService {
	//���ݿ��Ų�ѯ�˻����
	public double getBalance(String cardNum);

}
