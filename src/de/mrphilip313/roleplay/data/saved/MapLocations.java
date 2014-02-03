package de.mrphilip313.roleplay.data.saved;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.BlockVector;

import de.mrphilip313.roleplay.core.database.core.BaseDBFunctions;

public class MapLocations {
    public static List<BlockVector> mapLocations;

    public static void load() {
        mapLocations = new ArrayList<BlockVector>();
        BaseDBFunctions.loadMaps();
    }

    public static void add(BlockVector vec) {
    	BaseDBFunctions.addMap(vec);
    	mapLocations.add(vec);
    }
    
    public static void remove(BlockVector vec){
    	BaseDBFunctions.deleteMap(vec);
    	mapLocations.remove(vec);
    }
}