package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandSchematicPasteEvent;
import com.bgsoftware.superiorskyblock.api.events.PlayerChangeRoleEvent;
import me.bounser.skyblockagui.SkyblockAGUI;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class IslandListener implements Listener {

    @EventHandler
    public void onIslandSchematicPaste(IslandSchematicPasteEvent e){

        new BukkitRunnable() {
            @Override
            public void run() {
                InstancesManager.getInstance().setupGUIs(e.getIsland(), Data.getInstance().dynamicPlacing());
            }
        }.runTaskLater(SkyblockAGUI.getInstance(), 5);
    }

    // Removes GUI when disbanding the island.
    @EventHandler
    public void onIslandDisband(IslandDisbandEvent e){

        InstancesManager.getInstance().removeAllIslandGUIs(e.getIsland());

    }

    @EventHandler
    public void onRolChange(PlayerChangeRoleEvent e){

        if(e.getNewRole().getPreviousRole() != null && (e.getNewRole().getWeight()) > e.getNewRole().getPreviousRole().getWeight())
        InstancesManager.getInstance().executeDynamicPlacement(e.getPlayer().asPlayer(), true);
        else InstancesManager.getInstance().executeDynamicRemoval(e.getPlayer().asPlayer().getLocation());

    }
}
