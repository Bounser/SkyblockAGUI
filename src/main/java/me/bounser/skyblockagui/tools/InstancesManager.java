package me.bounser.skyblockagui.tools;

import me.leoko.advancedgui.manager.GuiWallManager;
import me.leoko.advancedgui.manager.LayoutManager;
import me.leoko.advancedgui.utils.Direction;
import me.leoko.advancedgui.utils.GuiLocation;
import me.leoko.advancedgui.utils.GuiWallInstance;
import org.bukkit.Location;

public class InstancesManager {

    private static InstancesManager instance;

    public static InstancesManager getInstance(){
        if(instance != null) return instance;

        InstancesManager.instance = new InstancesManager();
        return instance;
    }

    public void placeGUI(Location loc, Direction dir, String Layout, boolean persistant){
        GuiWallManager.getInstance().registerInstance(
                new GuiWallInstance(
                        GuiWallManager.getInstance().getNextId(),
                        LayoutManager.getInstance().getLayout(Layout), Data.getInstance().getRadius(),
                        new GuiLocation(loc, dir)), persistant
        );
    }


}
