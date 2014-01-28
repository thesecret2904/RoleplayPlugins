package de.mrphilip313.roleplay.data.saved;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Locations {
	public static Location loginSpawn = new Location(getWorld(), 417, 24, -855);
	public static Location gameSpawn = new Location(getWorld(), 408, 24, -856);
//	public static Location loginSpawn = new Location(getWorld(), -760, 62, -330);
//	public static Location gameSpawn = new Location(getWorld(), -763, 62, -317);
	public static Location bannedSpawn = new Location(getWorld(), 436, 69, -792);
	
	public static ConcurrentHashMap<String, Location> warps = new ConcurrentHashMap<String, Location>();
	
	public static World getWorld(){
		return Bukkit.getServer().getWorlds().get(0);
	}
	
	public static void addWarp(String warp, Location loc){
		warps.put(warp, loc);
	}
	
	public static void delWarp(String warp){
		warps.remove(warp);
	}
	
	public static Location getWarp(String warp){
		return warps.get(warp);
	}
	
	public static boolean doesWarpExist(String warp){
		return warps.containsKey(warp);
	}
}
