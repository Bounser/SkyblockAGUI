package me.bounser.skyblockagui.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(Data.getInstance().getMode() == 3 && SuperiorSkyblockAPI.getPlayer(e.getPlayer()).hasIsland()){

            Island is = SuperiorSkyblockAPI.getPlayer(e.getPlayer()).getIsland();
            Data data = Data.getInstance();
            String schem = data.getSchematic(is);

            for(String type : new ArrayList<String>(Arrays.asList("overworld", "nether", "the_end")) ) {
                Location guiLoc = data.getLocation(is, type);
                if (!InstancesManager.getInstance().GUIset(data.getLocation(is, type))) {
                    for (Chunk c : is.getAllChunks()) { if (!c.isLoaded()) c.load(); }
                    InstancesManager.getInstance().placeGUI(data.getLocation(is, type), data.getDirection(schem, type), data.getLayout(schem, type), false);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){



    }

}
