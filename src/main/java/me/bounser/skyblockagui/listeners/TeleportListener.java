package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){

        if(SuperiorSkyblockAPI.getIslandAt(e.getTo()).equals(SuperiorSkyblockAPI.getIslandAt(e.getFrom()))) return;

        if(SuperiorSkyblockAPI.getIslandAt(e.getFrom()) == null) InstancesManager.getInstance().executeDynamicRemoval(e.getFrom());

        if(SuperiorSkyblockAPI.getIslandAt(e.getTo()) != null) InstancesManager.getInstance().executeDynamicRemoval(e.getTo());
    }
}
