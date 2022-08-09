package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import me.leoko.advancedgui.manager.GuiWallManager;
import me.leoko.advancedgui.utils.GuiWallInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandListener implements Listener {

    @EventHandler
    public void onIslandCreate(IslandCreateEvent e){

        Data data = Data.getInstance();
        String type = data.getType(e.getPlayer().asPlayer());
        String schem = data.getSchematic(e.getIsland());

        if(!data.dynamicPlacing()){
            InstancesManager.getInstance().placeGUI(data.getLocation(e.getIsland(), type), data.getDirection(schem, type), data.getLayout(type), true );
        } else {


        }

    }

    // Removes GUI when disbanding the island.
    @EventHandler
    public void onIslandDisband(IslandDisbandEvent e){
        Data data = Data.getInstance();
        if(e.getIsland().getSchematicName() != data.getSchematic(e.getIsland())) return;

        Location guiLoc = data.getLocation(e.getIsland(), data.getTypeFromIsland(e.getIsland())) ;
        if(InstancesManager.getInstance().removeGUI(guiLoc)){
            Bukkit.getLogger().info("GUI from " + e.getPlayer().getName() + "' island successfully removed.");
        } else{
            Bukkit.getLogger().info("GUI could not be removed from " + e.getPlayer().getName() + "' island (Is there none?)");
        }
    }
}
