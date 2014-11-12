package com.sb.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	static final String HEXES = "0123456789abcdef";

	private static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

	public static String digest(String toMd5) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("md5");
			md5.update(toMd5.getBytes());
			return getHex(md5.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encode(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff; // 把byte变成int，和1相与，原值不变，这里可以加盐
				String hex = Integer.toHexString(number);
				if (hex.length() == 1) {
					sb.append("0");
				}
				sb.append(hex);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
//	public static void main(String[] args) {
//	// MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
//	System.out.println(MD5.encode(""));
//	// MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
//	System.out.println(MD5.encode("a"));
//	// MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
//	System.out.println(MD5.encode("abc"));
//}
}
