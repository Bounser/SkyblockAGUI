package me.bounser.skyblockagui.tools;

import me.leoko.advancedgui.manager.GuiWallManager;
import me.leoko.advancedgui.manager.LayoutManager;
import me.leoko.advancedgui.utils.Direction;
import me.leoko.advancedgui.utils.GuiLocation;
import me.leoko.advancedgui.utils.GuiWallInstance;
import org.bukkit.Location;

public class InstancesManager {

    private static InstancesManager instance;


    private InstancesManager(){

    }

    public static InstancesManager getInstance(){
        if(instance != null) return instance;

        InstancesManager.instance = new InstancesManager();
        return instance;
    }

    // Method to place GUIs
    public void placeGUI(Location loc, Direction dir, String Layout, boolean persistant){
        if(GUIset(loc)) return;
        GuiWallManager.getInstance().registerInstance(
                new GuiWallInstance(
                        GuiWallManager.getInstance().getNextId(),
                        LayoutManager.getInstance().getLayout(Layout), Data.getInstance().getRadius(),
                        new GuiLocation(loc, dir)), persistant
        );
    }

    // Method to remove GUIs
    public boolean removeGUI(Location loc){
        GuiWallInstance gwi = InstancesManager.getInstance().getGUI(loc);
        if(gwi == null) return false;
        GuiWallManager.getInstance().unregisterInstance(gwi, true);
        return true;
    }

    public GuiWallInstance getGUI(Location loc){ return GuiWallManager.getInstance().getActiveInstance(loc); }

    public boolean GUIset(Location loc){ if(getGUI(loc) != null) return true; return false; }
}
