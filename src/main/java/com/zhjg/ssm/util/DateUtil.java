package com.zhjg.ssm.util;

import java.util.Date;

public class DateUtil {

	/**
	 * <p>��ǰϵͳʱ����date���ķ�����</p>
	 * @param date
	 * @return
	 */
	public static int pastMinutes(Date date){
	
		long tmp = new Date().getTime() - date.getTime();
		int reval = (int)tmp/1000/60;
		return reval;
	}
	
}
