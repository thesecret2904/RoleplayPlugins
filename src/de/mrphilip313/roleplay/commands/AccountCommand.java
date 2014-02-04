package de.mrphilip313.roleplay.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.core.database.AccountManager;

public class AccountCommand extends RoleplayCommand{
	
	public AccountCommand(){}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			AccountManager am = new AccountManager(player);
			
			if(args.length >= 1)
			{
				switch (args[0]) {
					case "register":
						if(!am.isPlayerRegistered()){				
							if(args.length == 4){
								if (!am.doesRPNameExist(args[1])) {
									if (args[2].equals(args[3])) {
										player.sendMessage(sendPlayerCommandSuccses("Du hast dich erfolgreich registriert"));
										am.registerPlayer(args[2], args[1]);
									} else {
										player.sendMessage(sendPlayerCommandError("Die eingegebenen Passwörter stimmen nicht überein"));
									} 
								} else {
									player.sendMessage(sendPlayerCommandError("Dieser Name ist bereits vergeben"));
								}
							} else {
								player.sendMessage(sendPlayerSyntax("register", "[rpname] [passwort] [wiederholung]"));
							}
						} else {
							player.sendMessage(sendPlayerCommandError("Du bist bereits registriert"));
						}	
						return true;					
					case "login":
						if (!am.isPlayerLoggedIn()){
							if(args.length == 2){
								if(am.isPasswordCorrect(args[1])){
									am.loginPlayer();
									player.sendMessage(sendPlayerCommandSuccses("Du hast dich erfolgreich eingeloggt"));
								} else {
									player.sendMessage(sendPlayerCommandError("Das ist eingegebene Passwort ist falsch"));
								}
							} else {
								player.sendMessage(sendPlayerSyntax("login", "[passwort]"));
							}
						} else {
							player.sendMessage(sendPlayerCommandError("Du bist bereits eingeloggt"));
						}
						return true;

					case "logout":
						if (am.isPlayerLoggedIn()) {
							if (args.length == 1) {
								am.logoutPlayer();
							} else {
								player.sendMessage(sendPlayerSyntax("logout", ""));
							}
						} else {
							player.sendMessage(sendPlayerCommandError("Du bist nicht angemeldet"));
						}
						return true;
	
					case "hilfe":
						player.sendMessage(ChatColor.RED + "/account");
						player.sendMessage(ChatColor.GOLD + "    register " + ChatColor.GREEN + " [password] [wiederholung]");
						player.sendMessage(ChatColor.GOLD + "    login " + ChatColor.GREEN + " [password]");
						player.sendMessage(ChatColor.GOLD + "    logout ");
						player.sendMessage(ChatColor.GOLD + "    changepw " + ChatColor.GREEN + " [altes pw] [neues pw]");					
						return true;
						
					default:
						player.sendMessage(sendPlayerCommandError("Benutze \"/account hilfe\" um eine Beschreibung des Befehls zu bekommen"));					
						return true;
				}
			}
			else
			{
				player.sendMessage(sendPlayerCommandError("Benutze \"/account hilfe\" um eine Beschreibung des Befehls zu bekommen"));
			}
		}
		return true;
	}	
		
	@Override
	public String getPrefix() {
		return "[Account]";
	}

	@Override
	public String getBaseCommand() {
		return "account";
	}

}
