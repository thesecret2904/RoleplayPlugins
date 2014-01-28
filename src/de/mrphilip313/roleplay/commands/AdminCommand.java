package de.mrphilip313.roleplay.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.Players;
import de.mrphilip313.roleplay.data.utils.Utils;

public class AdminCommand extends RoleplayCommand{

	public AdminCommand(){}
	
	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(cmdSender instanceof Player){
			Player sender = (Player) cmdSender;
			
			switch (cmd.getName()) {
			case "ban":
				if(args != null && args.length >= 2){
					PlayerInformation pInfo = Players.getPlayerEntry(sender);
					if(pInfo.getAdminlevel() > Adminlevel.USER){
						if(pInfo.isAdminOnDuty()){
							if(Bukkit.getServer().getPlayer(args[0]) != null){
								Player player = Bukkit.getServer().getPlayer(args[0]);
								PlayerInformation pInfo2 = Players.getPlayerEntry(player);
								pInfo2.setBanned(true);
								pInfo2.setBanner(sender.getName());
								pInfo2.setBanReason(Utils.buildString(args, 1));
								pInfo2.setPermBanned(true);
								player.kickPlayer("Du wurdes permanent gebannt !\nWenn du Fragen hast, kannst du mit /sup auf dem Server ein Support-Ticket erstellen");
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

			default:
				// TODO
				return true;
			}
		}
		return true;
	}

	@Override
	public String getPrefix() {
		return "[Admin]";
	}

	@Override
	public String getBaseCommand() {
		return null;
	}

}
