package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.leoko.advancedgui.manager.GuiWallManager;
import me.leoko.advancedgui.manager.LayoutManager;
import me.leoko.advancedgui.utils.Direction;
import me.leoko.advancedgui.utils.GuiLocation;
import me.leoko.advancedgui.utils.GuiWallInstance;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class InstancesManager {

    private static InstancesManager instance;

    public static InstancesManager getInstance(){
        if(instance == null) instance = new InstancesManager();

        return instance;
    }

    public void setupGUIs(Island is, Boolean persistant){

        Data data = Data.getInstance();
        String schem = data.getSchematic(is);

        for(String type : new ArrayList<String>(Arrays.asList("overworld", "nether", "the_end")) ) {
            Bukkit.broadcastMessage("2");

            if((type.equals("nether") && is.isNetherEnabled()) || (type.equals("the_end") && is.isEndEnabled()) || type.equals("overworld")) {
                Location guiLoc = data.getLocation(is, type);
                if (!InstancesManager.getInstance().checkGUI(data.getLocation(is, type))) {
                    for (Chunk c : is.getAllChunks(data.getEnviromentFromType(type))) {
                        if (!c.isLoaded()) c.load();
                        Bukkit.broadcastMessage("3");
                    }
                    InstancesManager.getInstance().placeGUI(
                            data.getLocation(is, type),
                            data.getDirection(schem, type),
                            data.getLayout(schem, type),
                            persistant);
                }
            }
        }
    }

    // Method to place GUIs
    public void placeGUI(Location loc, Direction dir, String Layout, boolean persistant){

        Bukkit.broadcastMessage("1");
        Data data = Data.getInstance();

        if(data.getSetAir()) SetupManager.getInstance().clearArea(SuperiorSkyblockAPI.getIslandAt(loc), data.getType(loc));
        if(data.getReplace()) SetupManager.getInstance().setBackground(SuperiorSkyblockAPI.getIslandAt(loc), data.getType(loc));

        SetupManager.getInstance().setupItemFrames(SuperiorSkyblockAPI.getIslandAt(loc), data.getType(loc));

        GuiWallManager.getInstance().registerInstance(
                new GuiWallInstance(
                        GuiWallManager.getInstance().getNextId(),
                        LayoutManager.getInstance().getLayout(Layout), Data.getInstance().getRadius(),
                        new GuiLocation(loc, dir)), persistant
        );
    }

    // Method to remove GUIs (Opposed of placeGUI)
    public boolean removeGUI(Location loc){
        GuiWallInstance gwi = InstancesManager.getInstance().getGUI(loc);
        if(gwi == null) return false;

        GuiWallManager.getInstance().unregisterInstance(gwi, true);
        return true;
    }

    // Method to remove ALL GUIs (Opposed to setupGUIs)
    public void removeAllIslandGUIs(Island is){
        Data data = Data.getInstance();
        for(String type : new ArrayList<String>(Arrays.asList("overworld", "nether", "the_end")) ) {

            if((type.equals("nether") && is.isNetherEnabled()) || (type.equals("the_end") && is.isEndEnabled()) || type.equals("overworld")) {
                Location guiLoc = data.getLocation(is, type);
                if (InstancesManager.getInstance().checkGUI(data.getLocation(is, type)))
                    removeGUI(data.getLocation(is, type));
            }

        }
    }

    public GuiWallInstance getGUI(Location loc){ return GuiWallManager.getInstance().getActiveInstance(loc); }

    public boolean checkGUI(Location loc){ if(getGUI(loc) != null) return true; return false; }

    public void executeDynamicRemoval(Location loc){
        Island is = SuperiorSkyblockAPI.getIslandAt(loc);
        if(is == null) return;

        switch(Data.getInstance().getMode()){
            // Remove GUIs if there isn't any member on the island.
            case 1:

                boolean none = true;
                for(SuperiorPlayer p : is.getAllPlayersInside()){
                    if(is.getIslandMembers().contains(p)){
                        none = false;
                    }
                }
                if(none){
                    InstancesManager.getInstance().removeAllIslandGUIs(is);
                }
                break;
            // Remove GUIs if the owner isn't on the island.
            case 2:
                boolean owner = true;
                for(SuperiorPlayer p : is.getAllPlayersInside()){
                    if(is.getOwner().equals(p)){
                        owner = false;
                    }
                }
                if(owner){
                    InstancesManager.getInstance().removeAllIslandGUIs(is);
                }

                break;
            // Remove GUIs if any member isn't online.
            case 3:
                boolean online = true;
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(is.getIslandMembers().contains(p)){
                        online = false;
                    }
                }
                if(online){
                    InstancesManager.getInstance().removeAllIslandGUIs(is);
                }
                break;
        }
    }
    public void executeDynamicPlacement(Player player, Boolean at){
        Island is = null;
        if(at){ is = SuperiorSkyblockAPI.getIslandAt(player.getLocation()); }
        else { is = SuperiorSkyblockAPI.getPlayer(player).getIsland(); }
        if((is != null) && Data.getInstance().getMode() != 3){
            switch(Data.getInstance().getMode()) {
                case 1:
                    if (is.getIslandMembers().contains(player)) {

                        InstancesManager.getInstance().setupGUIs(is, false);
                        break;
                    }
                case 2:
                    if (is.getOwner().getName().equals(player.getName())) {

                        InstancesManager.getInstance().setupGUIs(is, false);
                        break;
                    }
            }
        } else {
            for(Island island : SuperiorSkyblockAPI.getGrid().getIslands())
                if(island.isMember(SuperiorSkyblockAPI.getPlayer(player))){
                    InstancesManager.getInstance().setupGUIs(island, false);
                }
            }
        }


    public void checkForPlayers(){
        for(Player p : Bukkit.getServer().getOnlinePlayers()){

            if(SuperiorSkyblockAPI.getPlayer(p).getIsland()!=null) executeDynamicPlacement(p, false);

        }
    }

}
