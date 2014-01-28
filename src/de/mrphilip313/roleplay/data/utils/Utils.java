package de.mrphilip313.roleplay.data.utils;

public class Utils {
	public static String buildString(String[] input, int begin){
		StringBuilder builder = new StringBuilder();
		for (int i = begin; i < input.length; i++) {
			builder.append(input[i]);
			if(i+1 < input.length) builder.append(" ");
		}
		return builder.toString();
	}
	
}
