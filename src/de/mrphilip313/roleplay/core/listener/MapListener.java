package de.mrphilip313.roleplay.core.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import de.mrphilip313.roleplay.data.PlayerInformation;
import de.mrphilip313.roleplay.data.saved.MapLocations;
import de.mrphilip313.roleplay.data.saved.Players;

public class MapListener implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent event) {
        if(!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        PlayerInformation pInfo = Players.getPlayerEntry(player);
        if(event.getRightClicked() instanceof ItemFrame){
        	ItemFrame frame = (ItemFrame) event.getRightClicked();
        	if(player.getItemInHand() != null && player.getItemInHand().getType() == Material.BLAZE_ROD && pInfo.isAdminOnDuty()){
        		if(frame.getItem() != null && frame.getItem().getType() == Material.MAP){
                    if(MapLocations.mapLocations.contains(frame.getLocation().toVector().toBlockVector())) {
                    	MapLocations.remove(frame.getLocation().toVector().toBlockVector());
                        player.sendMessage(ChatColor.YELLOW + "Die Map wurde gelöscht");
                    } else {
                    	MapLocations.add(frame.getLocation().toVector().toBlockVector());
                        player.sendMessage(ChatColor.YELLOW + "Die Map wurde hinzugefügt");
                    }      			
        		}
        		event.setCancelled(true);
        	} else {
        		if(MapLocations.mapLocations.contains(frame.getLocation().toVector().toBlockVector()) && !pInfo.isAdminOnDuty()) event.setCancelled(true);
        	}
        }
    }
    
    @EventHandler
    public void onFrameBreak(HangingBreakByEntityEvent event) {
	    if(!(event.getRemover() instanceof Player)) return;
        Player player = (Player) event.getRemover();
        PlayerInformation pInfo = Players.getPlayerEntry(player);
        if(event.getEntity() instanceof ItemFrame){
        	ItemFrame frame = (ItemFrame) event.getEntity();
        	if(player.getItemInHand() != null && player.getItemInHand().getType() == Material.BLAZE_ROD && pInfo.isAdminOnDuty()){
        		if(frame.getItem() != null && frame.getItem().getType() == Material.MAP){
                    if(MapLocations.mapLocations.contains(frame.getLocation().toVector().toBlockVector())) {
                    	MapLocations.remove(frame.getLocation().toVector().toBlockVector());
                        player.sendMessage(ChatColor.YELLOW + "Die Map wurde gelöscht");
                    } else {
                    	MapLocations.add(frame.getLocation().toVector().toBlockVector());
                        player.sendMessage(ChatColor.YELLOW + "Die Map wurde hinzugefügt");
                    }      			
        		}
        		event.setCancelled(true);
        	} else {
        		if(MapLocations.mapLocations.contains(frame.getLocation().toVector().toBlockVector()) && !pInfo.isAdminOnDuty()) event.setCancelled(true);
        	}
        }	    
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        List<Entity> ents = event.getPlayer().getNearbyEntities(Bukkit.getViewDistance()*16, 256, Bukkit.getViewDistance()*16);
        for(Entity ent : ents) {
            if(!(ent instanceof ItemFrame)) continue;
            if(!MapLocations.mapLocations.contains(ent.getLocation().toVector().toBlockVector())) continue;
            ItemFrame frame = (ItemFrame) ent;
            if(frame.getItem() != null && frame.getItem().getType() == Material.MAP) {
                event.getPlayer().sendMap(Bukkit.getMap(frame.getItem().getDurability()));
            }
        }
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {

        for(Player player : Bukkit.getOnlinePlayers()) {
            for(Entity ent : event.getChunk().getEntities()) {
                if(!(ent instanceof ItemFrame)) continue;
                if(!MapLocations.mapLocations.contains(ent.getLocation().toVector().toBlockVector())) continue;
                ItemFrame frame = (ItemFrame) ent;
                if(frame.getItem().getType() == Material.MAP) {
                    player.sendMap(Bukkit.getMap(frame.getItem().getDurability()));
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerMove(PlayerTeleportEvent event) {

        List<Entity> ents = event.getPlayer().getNearbyEntities(Bukkit.getViewDistance()*16, 256, Bukkit.getViewDistance()*16);
        for(Entity ent : ents) {
            if(!(ent instanceof ItemFrame)) continue;
            if(!MapLocations.mapLocations.contains(ent.getLocation().toVector().toBlockVector())) continue;
            ItemFrame frame = (ItemFrame) ent;
            if(frame.getItem() != null && frame.getItem().getType() == Material.MAP) {
                event.getPlayer().sendMap(Bukkit.getMap(frame.getItem().getDurability()));
            }
        }
    }
}