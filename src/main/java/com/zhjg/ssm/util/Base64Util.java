package com.zhjg.ssm.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.iharder.Base64;

public class Base64Util {

	private static final String CHARSET = "UTF-8"; 
	
	public static String encode(String oriStr){
		String reval = null;
		try {
			reval = Base64.encodeBytes(oriStr.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reval;
	}
	
	public static String decode(String oriStr){
		String reval = null;
		try {
			reval = new String(Base64.decode(oriStr), CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reval;
	}
}
