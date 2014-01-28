package de.mrphilip313.roleplay.core.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.saved.Players;

public class BanHandler extends BukkitRunnable {

	@Override
	public void run() {
		BaseDBFunctions.executeUpdate("UPDATE banned SET time = time - 1 WHERE time >= 1");
		BaseDBFunctions.executeUpdate("UPDATE banned SET banned = 0, permanent = 0 WHERE banned = 1 AND permanent = 1 AND time = 0");
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			PlayerInformation pInfo = Players.getPlayerEntry(player);
			if(pInfo.isBanned() && !pInfo.isPermBanned()){
				pInfo.processTimeBanned();
			}
		}
	}

}
