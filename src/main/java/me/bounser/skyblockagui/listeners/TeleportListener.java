package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import me.bounser.skyblockagui.SkyblockAGUI;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class TeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){

        if(Objects.equals(SuperiorSkyblockAPI.getIslandAt(e.getTo()), SuperiorSkyblockAPI.getIslandAt(e.getFrom()))) return;

        new BukkitRunnable() {
            @Override
            public void run() {

                if (SuperiorSkyblockAPI.getIslandAt(e.getFrom()) != null)
                    InstancesManager.getInstance().executeDynamicRemoval(e.getFrom());

                if(SuperiorSkyblockAPI.getIslandAt(e.getTo()) != null)
                    InstancesManager.getInstance().executeDynamicPlacement(e.getPlayer(), true);

            }
        }.runTaskLater(SkyblockAGUI.getInstance(), 3);

    }
}
