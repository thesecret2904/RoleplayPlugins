package de.mrphilip313.roleplay.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import de.mrphilip313.roleplay.RoleplayPlugin;
import de.mrphilip313.roleplay.core.chat.ChatUtils;
import de.mrphilip313.roleplay.core.database.VanishManager;
import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;
import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.enums.Adminlevel;
import de.mrphilip313.roleplay.data.saved.ChatDistances;
import de.mrphilip313.roleplay.data.saved.Locations;
import de.mrphilip313.roleplay.data.saved.Players;

public class RoleplayListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		Players.addPlayerEntry(player.getName(), new PlayerInformation());
		VanishManager.vanishForAllPlayers(player);
		VanishManager.updatePlayer(player);
		player.setFlying(false);
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getEnderChest().clear();
		if(BaseDBFunctions.isBanned(player.getName())){
			Players.setDisplayName(player, ChatColor.RED + player.getName());
			player.teleport(Locations.bannedSpawn);			
		} else {
			Players.setDisplayName(player, ChatColor.YELLOW + player.getName());
			player.teleport(Locations.loginSpawn);
			Players.setFreezed(player.getName());
			if(BaseDBFunctions.isUserRegistered(player.getName()))
				player.sendMessage(ChatColor.DARK_PURPLE + "[Account] " + ChatColor.DARK_GREEN + "Bitte logge dich mit deinem Password ein: " + ChatColor.GOLD + "/account login " + ChatColor.GREEN + "[password]");
			else
				player.sendMessage(ChatColor.DARK_PURPLE + "[Account] " + ChatColor.DARK_GREEN + "Bitte registrier dich mit einem Password: " + ChatColor.GOLD + "/account register " + ChatColor.GREEN + "[rpname] [password] [wiederholung]");
		}	
	}
	 
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		event.setQuitMessage(null);
		String name = event.getPlayer().getName();
		BaseDBFunctions.savePlayer(event.getPlayer());
		Players.deletePlayerEntry(name);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(Players.isFreezed(event.getPlayer().getName()))
			event.getPlayer().teleport(event.getPlayer());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		event.setDroppedExp(0);
	}
	
	@EventHandler
	public void onEntitySpawn(ItemSpawnEvent event){
		Entity entity = event.getEntity();
		if(entity instanceof ExperienceOrb)
			event.setCancelled(true);
	}
	
	
	@EventHandler
	public void onThrowable(ProjectileLaunchEvent event){
		if(event.getEntityType() != EntityType.EGG && event.getEntityType() != EntityType.ARROW){
			if(event.getEntity().getShooter() instanceof Player){
				Player player = (Player) event.getEntity().getShooter();
				if(Players.getPlayerEntry(player.getName()).getAdminlevel() < Adminlevel.SERVERVERWALTUNG){
					player.sendMessage(ChatColor.DARK_PURPLE + "[Fehler]" + ChatColor.RED + "Dein Adminrang ist zu niedrig");
					RoleplayPlugin.log(player.getName() + " tried to throw a Entity (" + event.getEntityType().name() + ")");
					event.getEntity().remove();
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		// TODO Adminrechte
		// TODO JOBS
		// TODO Fraktionen
		// TODO HÄuser / Hotels
		// event.setCancelled(true);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		RoleplayPlugin.log(event.getMessage());
		Player sender = event.getPlayer();
				
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			StringBuilder string = new StringBuilder();			
			boolean inNear = true;
			Double distance = sender.getLocation().distance(player.getLocation());
			if(distance <= ChatDistances.STATE1) string.append(ChatColor.WHITE);
			else if(distance <= ChatDistances.STATE2) string.append(ChatColor.GRAY);
			else if(distance <= ChatDistances.STATE3) string.append(ChatColor.DARK_GRAY);
			else if(distance <= ChatDistances.STATE4) string.append(ChatColor.BLACK);
			else inNear = false;
			if(inNear){
				string.append(Players.getRPName(player));
				string.append(" sagt: ");
				string.append(event.getMessage());
				player.sendMessage(string.toString());
			}		
		}
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event){
		PlayerInformation pInfo = Players.getPlayerEntry(event.getNamedPlayer().getName());
		String tag = event.getNamedPlayer().getName();		
		if(BaseDBFunctions.isBanned(tag)) event.setTag(ChatColor.RED + tag);
		else if(pInfo != null && pInfo.isAdminOnDuty()) event.setTag(ChatColor.LIGHT_PURPLE + tag);
		else event.setTag(ChatColor.RESET + tag);
	}
	
	@EventHandler
	public void onBlockPlayed(BlockPlaceEvent event){
		Material material = event.getBlockPlaced().getType();
		if(material == Material.LAVA || material == Material.STATIONARY_LAVA || material == Material.TNT || material == Material.BEDROCK){
			Player player = event.getPlayer();
			if(!(Players.getPlayerEntry(player.getName()).getAdminlevel() > Adminlevel.USER)){
				ChatUtils.sendMessageToAdmins(ChatColor.RED + player.getName() + " tried to place a Block (" + material.toString() + ")");
				event.setCancelled(true);
			}
		}	
	}
	
	@EventHandler
	public void onBucketUsed(PlayerBucketEmptyEvent event){
		Material material = event.getBucket();
		if(material == Material.LAVA_BUCKET){
			Player player = event.getPlayer();
			if(!(Players.getPlayerEntry(player.getName()).getAdminlevel() > Adminlevel.USER)){
				ChatUtils.sendMessageToAdmins(ChatColor.RED + player.getName() + " tried to place a Bucket (" + material.toString() + ")");
				event.setCancelled(true);
			}			
		}
	}
	
}
