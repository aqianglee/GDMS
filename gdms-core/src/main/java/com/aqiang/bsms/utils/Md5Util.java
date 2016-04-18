package com.aqiang.bsms.utils;

import java.security.MessageDigest;

public class Md5Util {
	public static String md5(String str) {
		StringBuffer buffer = new StringBuffer();
		try {
			char[] cs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			byte[] bytes = str.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] targ = md.digest(bytes);
			for (byte b : targ) {
				buffer.append(cs[(b >> 4) & 0x0f]);
				buffer.append(cs[b & 0x0f]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
