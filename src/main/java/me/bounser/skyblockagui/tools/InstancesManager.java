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
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class InstancesManager {

    private static InstancesManager instance;

    public static InstancesManager getInstance(){
        if(instance == null) instance = new InstancesManager();

        return instance;
    }

    // Place all the GUIs of an island: overworld, nether and end.
    public void setupGUIs(Island is){

        Data data = Data.getInstance();
        String schem = data.getSchematic(is);

        for(String type : Arrays.asList("overworld", "nether", "the_end") ) {

            if(((type.equals("nether") && is.isNetherEnabled()) ||
               (type.equals("the_end") && is.isEndEnabled()) ||
                type.equals("overworld")) &&
                data.getEnabledGUI(schem, type)) {

                if (!InstancesManager.getInstance().checkGUI(data.getPlacingLocation(is, type))) {

                    for (Chunk c : is.getAllChunks(data.getEnviromentFromType(type))) {
                        if (!c.isLoaded()) c.load();
                    }
                    InstancesManager.getInstance().placeGUI(
                            data.getPlacingLocation(is, type),
                            data.getDirection(schem, type),
                            data.getLayout(schem, type),
                            !data.dynamicPlacing());
                }
            }
        }
    }

    // Method to place GUIs
    public void placeGUI(Location loc, Direction dir, String Layout, boolean persistant){

        Data data = Data.getInstance();
        String type = data.getType(loc);
        Island is = SuperiorSkyblockAPI.getIslandAt(loc);

        if(data.getReplace()) SetupUtils.getInstance().setBackground(is, type);
        SetupUtils.getInstance().clearArea(is, type);

        SetupUtils.getInstance().setupItemFrames(SuperiorSkyblockAPI.getIslandAt(loc), data.getType(loc));

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
        SetupUtils.getInstance().clearArea(SuperiorSkyblockAPI.getIslandAt(loc), Data.getInstance().getType(loc));
        return true;
    }

    // Method to remove ALL GUIs (Opposed to setupGUIs)
    public void removeAllIslandGUIs(Island is){
        Data data = Data.getInstance();
        for(String type : new ArrayList<String>(Arrays.asList("overworld", "nether", "the_end")) ) {

            if((type.equals("nether") && is.isNetherEnabled()) || (type.equals("the_end") && is.isEndEnabled()) || type.equals("overworld")) {
                if (InstancesManager.getInstance().checkGUI(data.getPlacingLocation(is, type))){
                    removeGUI(data.getPlacingLocation(is, type));
                    SetupUtils.getInstance().clearArea(is, type);
                }
            }

        }
    }

    // Get the GUI at the specific location.
    public GuiWallInstance getGUI(Location loc){ return GuiWallManager.getInstance().getActiveInstance(loc); }

    // Check if there's any GUI in the specific location.
    public boolean checkGUI(Location loc){ if(getGUI(loc) != null) return true; return false; }

    // Removes GUIs depending on the mode:
    // 1 - Remove GUIs if there isn't any member on the island.
    // 2 - Remove GUIs if the owner isn't on the island.
    // 3 - Remove GUIs if any member isn't online.
    public void executeDynamicRemoval(Location loc){
        Island is = SuperiorSkyblockAPI.getIslandAt(loc);
        if(is == null) return;

        switch(Data.getInstance().getMode()){

            case 1:
                boolean none = true;
                for(SuperiorPlayer p : is.getAllPlayersInside()){
                    if(is.getIslandMembers().contains(p)){
                        none = false;
                    }
                }
                if(none){
                    InstancesManager.getInstance().removeAllIslandGUIs(is);
                } break;

            case 2:
                boolean owner = true;
                for(SuperiorPlayer p : is.getAllPlayersInside()){
                    if(is.getOwner().equals(p)){
                        owner = false;
                    }
                }
                if(owner){
                    InstancesManager.getInstance().removeAllIslandGUIs(is);
                } break;

            case 3:
                boolean online = true;
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(is.getIslandMembers().contains(p)){
                        online = false;
                    }
                }
                if(online){
                    InstancesManager.getInstance().removeAllIslandGUIs(is);
                } break;
        }
    }
    public void executeDynamicPlacement(Player player, Boolean at){
        Island is = null;
        if(at){ is = SuperiorSkyblockAPI.getIslandAt(player.getLocation()); }
        else { is = SuperiorSkyblockAPI.getPlayer(player).getIsland(); }
        if((is != null) && Data.getInstance().getMode() != 3){
            switch(Data.getInstance().getMode()) {
                case 1:
                    if (is.getIslandMembers(true).contains(SuperiorSkyblockAPI.getPlayer(player))) {
                        InstancesManager.getInstance().setupGUIs(is);
                    }
                    break;
                case 2:
                    if (is.getOwner().getName().equals(player.getName())) {

                        InstancesManager.getInstance().setupGUIs(is);
                    } break;
            }
        } else {
            for(Island island : SuperiorSkyblockAPI.getGrid().getIslands())
                if(island.isMember(SuperiorSkyblockAPI.getPlayer(player))){
                    InstancesManager.getInstance().setupGUIs(island);
                }
            }
        }


    public void checkForPlayers(){
        for(Player p : Bukkit.getServer().getOnlinePlayers()){

            if(SuperiorSkyblockAPI.getPlayer(p).getIsland()!=null) executeDynamicPlacement(p, false);

        }
    }
}
