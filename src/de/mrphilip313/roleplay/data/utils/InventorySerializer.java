package de.mrphilip313.roleplay.data.utils;

import java.io.IOException;

import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.utility.StreamSerializer;

public class InventorySerializer {
    /**
     * serialize ItemStacks
     * 
     * @param ItemsStack[]
     * @return data
     */
    public static String packInventory(ItemStack[] items){
    	StringBuilder builder = new StringBuilder();
    	StreamSerializer serializer = new StreamSerializer();
    	
    	try {
			for (int i = 0; i < items.length; i++) {
				if(items[i] != null){
					builder.append(serializer.serializeItemStack(items[i]));
				}
				builder.append(";");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return builder.toString();
    }
    
    /**
     * deserialize ItemStacks
     * @param data
     * @return ItemStack[]
     */
    public static ItemStack[] unpackInventory(String data){
    	String[] slots = data.split(";");
    	ItemStack[] result = new ItemStack[slots.length];
    	
    	StreamSerializer serializer = new StreamSerializer();
    	
    	try {
		   	for (int i = 0; i < slots.length; i++) {
		   		if(slots[i].length() > 0){
			   		result[i] = serializer.deserializeItemStack(slots[i]);		   			
		   		}

		   	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return result;
    }
}
