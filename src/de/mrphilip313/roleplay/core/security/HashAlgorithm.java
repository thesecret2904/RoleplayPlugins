package de.mrphilip313.roleplay.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashAlgorithm {
	public static String toSHA1(String input){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
		}
		return byteArrayToHexString(md.digest(input.getBytes()));
	}
	
	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i=0; i < b.length; i++) {
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}
	
	public static String doubleSHAHash(String password){
		return toSHA1(toSHA1(password));
	}
}
