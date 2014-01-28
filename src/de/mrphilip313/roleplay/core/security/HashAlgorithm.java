package de.mrphilip313.roleplay.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
	
	public static String generateSalt(){
		SecureRandom random = new SecureRandom();
		byte salt[] = random.generateSeed(80);
		return String.valueOf(salt).substring(3);
	}
	
	public static String getDoubleSaltedHash(String input, String salt) {
	    return toSHA1(salt + toSHA1(salt + toSHA1(input)));
	}
}
