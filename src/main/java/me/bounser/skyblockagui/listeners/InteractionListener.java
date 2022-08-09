package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import me.bounser.skyblockagui.tools.RegisterManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractionListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e){

        Entity entity = e.getRightClicked();
        Player p = e.getPlayer();

        if((entity.getType() != EntityType.ITEM_FRAME &&
            entity.getType() != EntityType.GLOW_ITEM_FRAME) ||
           SuperiorSkyblockAPI.getIslandAt(entity.getLocation()) != null ||
           !RegisterManager.getInstance().isRegistering(p, "both")) return;

        RegisterManager regist = RegisterManager.getInstance();

        if(regist.isRegistering(p, "first")){

            regist.saveFirstPosition(p, entity);

        } else if(regist.isRegistering(p, "second")){

            regist.saveSecondPosition(p, entity);
        }
    }
}
