package de.mrphilip313.roleplay.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.commands.RoleplayCommand;
import de.mrphilip313.roleplay.core.chat.ChatUtils;
import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Locations;
import de.mrphilip313.roleplay.data.saved.Players;
import de.mrphilip313.roleplay.data.utils.Utils;

public class BanSystem extends RoleplayCommand{

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			Player sender = (Player) cmdSender;
			String nameSender = sender.getName();
			PlayerInformation pInfo = Players.getPlayerEntry(sender);
			
			switch (cmd.getName()) {
				case "ban":
					if(args != null && args.length >= 2){
						if(pInfo.getAdminlevel() > Adminlevel.USER){
							if(pInfo.isAdminOnDuty()){
								if(Bukkit.getServer().getPlayer(args[0]) != null){
									Player player = Bukkit.getServer().getPlayer(args[0]);
									String reason = Utils.buildString(args, 1);
									BaseDBFunctions.banUser(player.getName(), sender.getName(), reason);
									player.kickPlayer("Du wurdes von " + nameSender + " permanent gebannt !\nWenn du Fragen hast, kannst du mit /sup auf dem Server ein Support-Ticket erstellen");
									ChatUtils.sendMessageToPlayers(ChatColor.RED + player.getName() + " wurde von " + nameSender + " permanent gebannt");
									ChatUtils.sendMessageToPlayers(ChatColor.RED + "Grund: " + reason);
								} else {
									sender.sendMessage(sendPlayerCommandError("Der Spieler ist nicht online"));
								}
							} else {
								sender.sendMessage(sendPlayerCommandError("Du musst dafür Onduty sein!"));
							}
						} else {
							sender.sendMessage(sendPlayerAdminRightsFailure(Adminlevel.MODERATOR_N));
						}
					} else {
						sender.sendMessage(sendPlayerSyntaxNOSB("ban", "[player] [reason]"));
					}
					return true;

			case "timeban":
				if(args != null && args.length >= 3){
					if(pInfo.getAdminlevel() > Adminlevel.USER){
						if(pInfo.isAdminOnDuty()){
							if(Bukkit.getServer().getPlayer(args[0]) != null){
								Player player = Bukkit.getServer().getPlayer(args[0]);
								String reason = Utils.buildString(args, 2);
								BaseDBFunctions.banUser(player.getName(), sender.getName(), reason , Integer.parseInt(args[1]));
								player.kickPlayer("Du wurdest von " + nameSender + " für " + args[2] +"Minuten gebannt !\nGrund: " + reason + "\nWenn du Fragen hast, kannst du mit /sup auf dem Server ein Support-Ticket erstellen");
								ChatUtils.sendMessageToPlayers(ChatColor.RED + player.getName() + " wurde von " + nameSender + " für " + args[2] + " Minuten gebannt");
								ChatUtils.sendMessageToPlayers(ChatColor.RED + "Grund: " + reason);
							} else {
								sender.sendMessage(sendPlayerCommandError("Der Spieler ist nicht online"));
							}
						} else {
							sender.sendMessage(sendPlayerCommandError("Du musst dafür Onduty sein"));
						}
					} else {
						sender.sendMessage(sendPlayerAdminRightsFailure(Adminlevel.MODERATOR_N));
					}
				} else {
					sender.sendMessage(sendPlayerSyntaxNOSB("ban", "[player] [reason]"));
				}
				return true;
				
			case "unban":
				if(args != null && args.length == 1){
					if (pInfo.getAdminlevel() > Adminlevel.USER) {
						if (pInfo.isAdminOnDuty()) {
							if(Bukkit.getServer().getPlayer(args[0]) != null){
								Player player = Bukkit.getServer().getPlayer(args[0]);
								player.sendMessage(sendPlayerCommandSuccses("Du wurdest entbannt"));
								player.teleport(Locations.loginSpawn);
							}
						}
					}
				}
			}
		}		
		return true;
	}

	@Override
	public String getPrefix() {
		return "[Ban]";
	}

	@Override
	public String getBaseCommand() { 
		return null;
	}

}
