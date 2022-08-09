package me.bounser.skyblockagui;

import me.bounser.skyblockagui.commands.CancelCommand;
import me.bounser.skyblockagui.commands.SetCommand;
import me.bounser.skyblockagui.listeners.ConnectionListener;
import me.bounser.skyblockagui.listeners.InteractionListener;
import me.bounser.skyblockagui.listeners.IslandListener;
import me.bounser.skyblockagui.listeners.TeleportListener;
import me.bounser.skyblockagui.tools.Data;
import me.leoko.advancedgui.manager.GuiWallManager;
import me.leoko.advancedgui.utils.GuiWallInstance;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyblockAGUI extends JavaPlugin {

    private static SkyblockAGUI instance;
    public static SkyblockAGUI getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Data data = Data.getInstance();

        // GUI creation/disband
        Bukkit.getPluginManager().registerEvents(new IslandListener(), this);

        // Resources regarding dynamic placing
        if(data.dynamicPlacing()){
            Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
            Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
        }

        // REGISTRATION
        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);

        getCommand("setlocation").setExecutor(new SetCommand());
        getCommand("cancel").setExecutor(new CancelCommand());
    }

    @Override
    public void onDisable() {
        // If the dynamic placing is enabled, on shutdown every GUI from the islands will be deleted
        if(Data.getInstance().dynamicPlacing()){

            for(GuiWallInstance gwi : GuiWallManager.getInstance().getActiveInstances()){

                for(String Layout : Data.getInstance().getSchematics())
                if(gwi.getLayout().getName().equals(Layout)) GuiWallManager.getInstance().unregisterInstance(gwi, true);
            }
        }
    }
}
