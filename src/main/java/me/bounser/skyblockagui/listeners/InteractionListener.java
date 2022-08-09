package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class InteractionListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e){

        if((e.getRightClicked().getType() != EntityType.ITEM_FRAME &&
           e.getRightClicked().getType() != EntityType.GLOW_ITEM_FRAME) ||
           SuperiorSkyblockAPI.getIslandAt(e.getRightClicked().getLocation()) != null) return;






    }



}
