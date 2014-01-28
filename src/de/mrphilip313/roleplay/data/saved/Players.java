package de.mrphilip313.roleplay.data.saved;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.PlayerInformation;

public class Players {
	public static ConcurrentHashMap<String, PlayerInformation> players = new ConcurrentHashMap<String, PlayerInformation>();
	public static ConcurrentHashMap<String, String> rpnames = new ConcurrentHashMap<String, String>();
	public static ConcurrentHashMap<String, Boolean> freezedPlayers = new ConcurrentHashMap<String, Boolean>();
	
	
	public static void addPlayerEntry(String username, PlayerInformation pInfo){
		players.put(username, pInfo);
	}
	
	public static PlayerInformation getPlayerEntry(String username){
		return players.get(username);
	}
	
	public static void deletePlayerEntry(String username){
		players.remove(username);
		freezedPlayers.remove(username);
		rpnames.remove(username);
	}
	
	public static void setFreezed(String username){
		System.out.println("freezed " + username);
		freezedPlayers.put(username, true);
	}
	
	public static void setUnfreezed(String username){
		System.out.println("unfreezed " + username);
		freezedPlayers.put(username, false);
	}
	

	
	public static boolean isFreezed(String username){
		if(!(freezedPlayers.get(username) == null)) return freezedPlayers.get(username);
		return false;
	}
	
	public static String getRPName(Player player){
		return getRPName(player.getName());
	}
	
	public static String getRPName(String name){
		return rpnames.get(name);
	}
	
	public static void setRPName(Player player, String name){
		rpnames.put(player.getName(), name);
	}
	
	public static void setRPName(String player, String name){
		setRPName(Bukkit.getServer().getPlayer(player), name);
	}
	
	public static void setDisplayName(Player player, String name){
		player.setPlayerListName(name);
	}
}
