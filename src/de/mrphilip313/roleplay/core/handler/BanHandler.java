package de.mrphilip313.roleplay.core.handler;

import org.bukkit.scheduler.BukkitRunnable;

import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;

public class BanHandler extends BukkitRunnable {

	@Override
	public void run() {
		BaseDBFunctions.executeUpdate("UPDATE banned SET time = time - 1 WHERE time >= 1");
		BaseDBFunctions.executeUpdate("UPDATE banned SET banned = 0, permanent = 0 WHERE banned = 1 AND permanent = 1 AND time = 0");
		System.out.println("Banhandler");
	}

}
