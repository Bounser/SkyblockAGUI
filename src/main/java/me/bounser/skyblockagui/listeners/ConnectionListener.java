package me.bounser.skyblockagui.listeners;

import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        if(Data.getInstance().dynamicPlacing()) InstancesManager.getInstance().executeDynamicPlacement(e.getPlayer(), true);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        if(Data.getInstance().dynamicPlacing()) InstancesManager.getInstance().executeDynamicRemoval(e.getPlayer().getLocation());

    }
}
