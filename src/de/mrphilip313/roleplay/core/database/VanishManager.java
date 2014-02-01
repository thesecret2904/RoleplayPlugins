package de.mrphilip313.roleplay.core.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.saved.Players;

public class VanishManager {
	
	public static void vanishForAllPlayers(Player invis){
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!Players.getPlayerEntry(player).isAdminOnDuty()){
				player.hidePlayer(invis);	
				Players.setVanished(player.getName());
			}

		}
	}
	
	public static void visibleForAllPlayers(Player invis){
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.showPlayer(invis);
			Players.setVisisble(player.getName());
		}
	}
	
	public static void updatePlayer(Player player){
		for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
			if(Players.getPlayerEntry(player).isAdminOnDuty()){
				player.showPlayer(player1);
			} else {
				if(Players.isVanished(player1.getName())) player.hidePlayer(player1);
				else player.showPlayer(player1);
			}
		}
	}
}
