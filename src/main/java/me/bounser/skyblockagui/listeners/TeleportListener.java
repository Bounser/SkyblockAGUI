package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(SuperiorSkyblockAPI.getIslandAt(e.getTo()) == null) return;

        Island is = SuperiorSkyblockAPI.getIslandAt(e.getTo());
        Data data = Data.getInstance();

        String type = data.getType(e.getTo());

        if(InstancesManager.getInstance().GUIset(Data.getInstance().getLocation(is, type))) return;

        switch(Data.getInstance().getMode()){
            case 1: if(is.getIslandMembers().contains(e.getPlayer())){

                InstancesManager.getInstance().placeGUI(
                        data.getLocation(is, type),
                        data.getDirection(data.getSchematic(is), type),
                        data.getLayout(data.getSchematic(is), type),
                        false);
                break;
            }
            case 2: if(is.getOwner().getName().equals(e.getPlayer().getName())){

                InstancesManager.getInstance().placeGUI(
                        data.getLocation(is, type),
                        data.getDirection(data.getSchematic(is), type),
                        data.getLayout(data.getSchematic(is), type),
                        false);
                break;
            }
        }
    }
}
