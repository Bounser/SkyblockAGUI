package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandSchematicPasteEvent;
import com.bgsoftware.superiorskyblock.api.events.PlayerChangeRoleEvent;
import me.bounser.skyblockagui.SkyblockAGUI;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class IslandListener implements Listener {

    // PLaces the GUI in the specific world type when the schematic is pasted.
    @EventHandler
    public void onIslandSchematicPaste(IslandSchematicPasteEvent e){

        new BukkitRunnable() {
            @Override
            public void run() {
                InstancesManager.getInstance().setupGUIs(e.getIsland());
            }
        }.runTaskLater(SkyblockAGUI.getInstance(), 5);

    }

    // Removes GUI when disbanding the island.
    @EventHandler
    public void onIslandDisband(IslandDisbandEvent e){

        InstancesManager.getInstance().removeAllIslandGUIs(e.getIsland());

    }

    // If the role of a player changes while on an island, the dynamic palcing will be executed (Mode 2)
    @EventHandler
    public void onRolChange(PlayerChangeRoleEvent e){

        if(e.getNewRole().getPreviousRole() != null && (e.getNewRole().getWeight()) > e.getNewRole().getPreviousRole().getWeight())
        InstancesManager.getInstance().executeDynamicPlacement(e.getPlayer().asPlayer(), true);
        else InstancesManager.getInstance().executeDynamicRemoval(e.getPlayer().asPlayer().getLocation());

    }
}
