package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import me.bounser.skyblockagui.tools.RegisterManager;
import me.bounser.skyblockagui.tools.SetupUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractionListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e){

        Entity entity = e.getRightClicked();
        Player p = e.getPlayer();
        RegisterManager regist = RegisterManager.getInstance();


        if(!(entity instanceof ItemFrame) ||
              SuperiorSkyblockAPI.getIslandAt(entity.getLocation()) == null ||
              !regist.isRegistering(p, "checkBoth")) return;

        if(regist.isRegistering(p, "first")){

            regist.saveFirstPosition(p, entity);

        } else if (regist.isRegistering(p, "second")){

            regist.saveSecondPosition(p, entity);

        }
    }
}
