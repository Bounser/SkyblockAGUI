package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandListener implements Listener {

    @EventHandler
    public void onIslandCreate(IslandCreateEvent e){

        Data data = Data.getInstance();
        String type = data.getType(e.getPlayer().asPlayer());

        if(!data.dynamicPlacing()){
            InstancesManager.getInstance().placeGUI(data.getLocation(e.getIsland(), type), data.getDirection(type), data.getLayout(type), true );
        }


    }



}
