package de.mrphilip313.roleplaycore;

public class Utils {
	
	public static String buildString(String[] input, int start){
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < input.length; i++) {
			builder.append(input[i]);
			if(i+1 < input.length) builder.append(" ");
		}
		return builder.toString();
	}
}
