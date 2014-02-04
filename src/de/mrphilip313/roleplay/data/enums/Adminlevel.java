package de.mrphilip313.roleplay.data.enums;

public class Adminlevel {
	public static final int USER = 0;
	public static final int MODERATOR = 1;
	public static final int ADMINISTRATOR = 2;
	public static final int SERVERVERWALTUNG = 3;

	public static String getName(int level){
		switch(level){
			case USER:
				return "User";
			case MODERATOR:
				return "Moderator";
			case ADMINISTRATOR:
				return "Administrator";
			case SERVERVERWALTUNG:
				return "Serververwaltung";
			default:
				return "";
		}
			
	}
}
