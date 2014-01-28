package de.mrphilip313.roleplay.core.database;

import java.util.Enumeration;

import org.bukkit.Location;

import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;
import de.mrphilip313.roleplay.data.saved.Locations;

public class WarpManager {
	
	public static void loadWarps(){
		BaseDBFunctions.loadWarps();
	}
	
	public static void addWarp(String warp, Location location){
		Locations.addWarp(warp, location);
		BaseDBFunctions.writeWarp(warp, location);
	}
	
	public static void delWarp(String warp){
		Locations.delWarp(warp);
		BaseDBFunctions.deleteWarp(warp);
	}
	
	public static Location getWarp(String warp){
		return Locations.getWarp(warp);
	}
	
	public static boolean doesWarpExist(String warp){
		return Locations.doesWarpExist(warp);
	}
	
	public static String getWarpList(){
		StringBuilder string = new StringBuilder();
		Enumeration<String> keys = Locations.warps.keys();
		while (keys.hasMoreElements()) {
			string.append(keys.nextElement());
			string.append("; ");
		}
		return string.toString();
	}
	
}
