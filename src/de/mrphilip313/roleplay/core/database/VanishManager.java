package de.mrphilip313.roleplay.core.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.saved.Players;

public class VanishManager {
	
	public static void vanishForAllPlayers(Player invis){
		Players.setVanished(invis.getName());
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			PlayerInformation pInfo = Players.getPlayerEntry(player);
			if(pInfo == null || (pInfo != null && !pInfo.isAdminOnDuty()))
			if(Players.getPlayerEntry(player) == null || !(Players.getPlayerEntry(player).isAdminOnDuty())){
				player.hidePlayer(invis);	
			}

		}
	}
	
	public static void visibleForAllPlayers(Player invis){
		Players.setVisisble(invis.getName());
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.showPlayer(invis);
		}
	}
	
	public static void updatePlayer(Player player){
		for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
			PlayerInformation pInfo = Players.getPlayerEntry(player);
			if(pInfo != null && pInfo.isAdminOnDuty()){
				player.showPlayer(player1);
			} else {
				if(Players.isVanished(player1.getName())) player.hidePlayer(player1);
				else player.showPlayer(player1);
			}
		}
	}
}
