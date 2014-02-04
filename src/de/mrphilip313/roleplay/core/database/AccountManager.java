package de.mrphilip313.roleplay.core.database;

import org.bukkit.entity.Player;

import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.saved.Locations;
import de.mrphilip313.roleplay.data.saved.Players;

public class AccountManager {
	private Player player;
	private String username;
	
	public AccountManager(Player player){
		this.player = player;
		this.username = player.getName();
	}
	
	public void registerPlayer(String password, String rpname){
		BaseDBFunctions.registerPlayer(player,  password);
		Players.addPlayerEntry(username, standartInfo());
		Players.setRPName(player, rpname);
		Players.setDisplayName(player, username);
		player.teleport(Locations.gameSpawn);
	}
	
	public void logoutPlayer(){
		BaseDBFunctions.savePlayer(player);
		player.kickPlayer("Logged Out");
		Players.deletePlayerEntry(username);
	}
	
	public void loginPlayer(){
		Players.setRPName(player, Players.rpnames.get(username));
		Players.setDisplayName(player, username);
		
		BaseDBFunctions.loadPlayer(player);
		
		PlayerInformation pInfo = Players.getPlayerEntry(player);
		if(pInfo.isBanned()){
			player.teleport(Locations.bannedSpawn);
			return;
		} else {
			if(pInfo.isAdminOnDuty() || BaseDBFunctions.timeBanUp(player)){
				pInfo.setAdminOnDuty(false);
				player.teleport(Players.getPlayerEntry(this.username).getLocation());
			} else {
				// TODO mehrere Spawns
				player.teleport(Locations.gameSpawn);
			}
			player.getInventory().setContents(pInfo.getInventory());
			player.getInventory().setArmorContents(pInfo.getArmor());
			player.getEnderChest().setContents(pInfo.getEnder());
		}
	}
	
	private PlayerInformation standartInfo(){
		PlayerInformation pInfo = new PlayerInformation();
		pInfo.setUsername(this.username);
		pInfo.setLoggedIn(true);
		return pInfo;
	}
	
	public boolean isPasswordCorrect(String password){
		return BaseDBFunctions.isPasswordCorrect(this.username, password);
	}
	
	public boolean isPlayerRegistered(){
		return BaseDBFunctions.isUserRegistered(this.username);
	}
	
	public boolean doesRPNameExist(String rpname){
		return BaseDBFunctions.doesRPNameExist(rpname);
	}
	
	public boolean isPlayerLoggedIn(){
		PlayerInformation pInfo = Players.getPlayerEntry(username);
		if(pInfo != null && pInfo.isLoggedIn()) return true;
		return false;
	}	
}
