package de.mrphilip313.roleplay.core.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Players;

public class ChatUtils {
	public static void sendMessageToAdmins(String message){
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(Players.getPlayerEntry(player.getName()).getAdminlevel() > Adminlevel.USER){
				System.out.println("Adminnachricht: " + message);
				player.sendMessage(message);
			}
		}
	}
	
	public static void sendMessageToPlayers(String message){
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}
}
