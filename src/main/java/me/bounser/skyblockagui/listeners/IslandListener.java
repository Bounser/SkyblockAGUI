package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandSchematicPasteEvent;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;

public class IslandListener implements Listener {

    @EventHandler
    public void onIslandSchematicPaste(IslandSchematicPasteEvent e){

        Data data = Data.getInstance();
        String type = data.getType(e.getLocation());
        String schem = data.getSchematic(e.getIsland());

        if(!data.dynamicPlacing()){
            // Load the chunks before placing the GUI.
            for(Chunk c : e.getIsland().getAllChunks()){ if(!c.isLoaded()) c.load(); }
            InstancesManager.getInstance().placeGUI(data.getLocation(e.getIsland(), type), data.getDirection(schem, type), data.getLayout(schem, type), true );
        } else {


        }
    }

    // Removes GUI when disbanding the island.
    @EventHandler
    public void onIslandDisband(IslandDisbandEvent e){
        Data data = Data.getInstance();
        if(e.getIsland().getSchematicName() != data.getSchematic(e.getIsland())) return;

        for(String type : new ArrayList<String>(Arrays.asList("overworld", "nether", "the_end")) ){
            Location guiLoc = data.getLocation(e.getIsland(), type);
            if(InstancesManager.getInstance().removeGUI(guiLoc)){
                Bukkit.getLogger().info(ChatColor.GREEN + "GUI from " + e.getPlayer().getName() + "' island (type " + type + ") successfully removed.");
            } else{
                Bukkit.getLogger().info(ChatColor.RED + "GUI could not be removed from " + e.getPlayer().getName() + "' island (type " + type + ") (Is there none?)");
            }
        }
    }
}
