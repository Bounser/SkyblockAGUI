package me.bounser.skyblockagui.listeners;

import me.bounser.skyblockagui.SkyblockAGUI;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        if(Data.getInstance().dynamicPlacing()) InstancesManager.getInstance().executeDynamicPlacement(e.getPlayer(), true);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        Location loc = e.getPlayer().getLocation();

        new BukkitRunnable() {
            @Override
            public void run() {
                if(Data.getInstance().dynamicPlacing()) InstancesManager.getInstance().executeDynamicRemoval(loc);
            }
        }.runTaskLater(SkyblockAGUI.getInstance(), 3);
    }
}
