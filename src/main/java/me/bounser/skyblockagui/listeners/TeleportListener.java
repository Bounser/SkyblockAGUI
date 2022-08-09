package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.google.gson.InstanceCreator;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(SuperiorSkyblockAPI.getIslandAt(e.getTo()) == null) {
            InstancesManager.getInstance().executeDynamicRemoval(e.getPlayer());
            return;
        }
        InstancesManager.getInstance().executeDynamicRemoval(e.getPlayer());
        InstancesManager.getInstance().executeDynamicPlacement(e.getPlayer());
    }
}
