package de.mrphilip313.roleplay.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.core.database.WarpManager;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Players;

public class WarpCommand extends RoleplayCommand{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if (Players.getPlayerEntry(player.getName()).getAdminlevel() >= Adminlevel.MODERATOR) {
				if (args.length >= 1) {
					switch (args[0]) {
					case "set":
						if (args.length == 2) {
							if (args[1].length() <= 32) {
								if (!WarpManager.doesWarpExist(args[1])) {
									WarpManager.addWarp(args[1],
											player.getLocation());
									player.sendMessage(sendPlayerCommandSuccses("Der Warp \""
											+ args[1] + "\" wurde erstellt"));
								} else {
									player.sendMessage(sendPlayerCommandError("Der Warp \""
											+ args[1]
											+ "\" ist bereits vorhanden"));
								}
							} else {
								player.sendMessage(sendPlayerCommandError("Der Warpname darf maximal 32 Zeichen enthalten"));
							}
						} else {
							player.sendMessage(sendPlayerSyntax("set", "[name]"));
						}
						return true;

					case "del":
						if (args.length == 2) {
							if (WarpManager.doesWarpExist(args[1])) {
								WarpManager.delWarp(args[1]);
								player.sendMessage(sendPlayerCommandSuccses("Der Warp \""
										+ args[1] + "\" wurde gelöscht"));
							} else {
								player.sendMessage(sendPlayerCommandError("Der Warp existiert nicht"));
							}

						} else {
							player.sendMessage(sendPlayerSyntax("del", "[name]"));
						}
						return true;

					case "list":
						if (args.length == 1) {
							player.sendMessage(ChatColor.BLUE
									+ "------------- " + ChatColor.BOLD
									+ "Warplist" + ChatColor.RESET
									+ ChatColor.BLUE + " -------------");
							player.sendMessage(ChatColor.DARK_AQUA
									+ WarpManager.getWarpList());
							player.sendMessage(ChatColor.BLUE
									+ "------------- " + ChatColor.BOLD
									+ "Warplist" + ChatColor.RESET
									+ ChatColor.BLUE + " -------------");
						} else {
							player.sendMessage(sendPlayerSyntax("list", null));
						}
						return true;

					case "hilfe":
						player.sendMessage(ChatColor.RED + "/warp");
						player.sendMessage(ChatColor.GOLD + "    set "
								+ ChatColor.GREEN + " [name]");
						player.sendMessage(ChatColor.GOLD + "    del "
								+ ChatColor.GREEN + " [name]");
						player.sendMessage(ChatColor.GOLD + "    list ");
						player.sendMessage(ChatColor.GOLD + "    [name] ");
						return true;

					default:
						if (args.length == 1) {
							if (WarpManager.doesWarpExist(args[0])) {
								player.teleport(WarpManager.getWarp(args[0]));
								player.sendMessage(sendPlayerCommandSuccses("Du hast dich zum Warp \""
										+ args[0] + "\" teleportiert"));
							}
						} else {
							player.sendMessage(sendPlayerSyntax("", "[name]"));
						}
						return true;
					}
				} else {
					player.sendMessage(sendPlayerCommandError("Benutze \"/warp hilfe\" um eine Beschreibung des Befehls zu bekommen"));
					return true;
				}
			} else {
				player.sendMessage(sendPlayerCommandError("Du musst mindestens Moderator sein, um diesen Befehl zu nutzen"));
			}
			
		}
		return true;
	}

	@Override
	public String getPrefix() {
		return "[Warp]";
	}

	@Override
	public String getBaseCommand() {
		return "warp";
	}
	

}
