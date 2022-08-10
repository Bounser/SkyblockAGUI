package me.bounser.skyblockagui;

import me.bounser.skyblockagui.commands.CancelCommand;
import me.bounser.skyblockagui.commands.SetCommand;
import me.bounser.skyblockagui.listeners.ConnectionListener;
import me.bounser.skyblockagui.listeners.InteractionListener;
import me.bounser.skyblockagui.listeners.IslandListener;
import me.bounser.skyblockagui.listeners.TeleportListener;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import me.leoko.advancedgui.manager.GuiWallManager;
import me.leoko.advancedgui.utils.GuiWallInstance;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public final class SkyblockAGUI extends JavaPlugin {

    private static SkyblockAGUI instance;

    public static SkyblockAGUI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Data data = Data.getInstance();

        // GUI creation/disband
        Bukkit.getPluginManager().registerEvents(new IslandListener(), this);

        // Resources regarding dynamic placing
        if (data.dynamicPlacing()) {
            Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
            Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);

            // Creates GUIs in case there is any player online (Forcing reload)
            InstancesManager.getInstance().checkForPlayers();

            Bukkit.broadcastMessage("Loaded dynamic");
            Bukkit.getLogger().info("Dynamic placing module loaded.");
        }

        // REGISTRATION
        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);

        getCommand("setguilocation").setExecutor(new SetCommand());
        getCommand("cancel").setExecutor(new CancelCommand());

        Bukkit.getLogger().info("Plugin successfully loaded.");
    }

    @Override
    public void onDisable() {
        // If the dynamic placing and the force delete is enabled, on shutdown every GUI from the islands will be deleted
        // (In case of switch between dynamic and non dynamic)
        if (Data.getInstance().dynamicPlacing() && Data.getInstance().forceDelete()) {

            for (GuiWallInstance gwi : GuiWallManager.getInstance().getActiveInstances()) {
                for (String schem : Data.getInstance().getSchematics()) {
                    for (String type : new ArrayList<String>(Arrays.asList("overworld", "nether", "the_end"))) {
                        if (gwi.getLayout().getName().equals(Data.getInstance().getLayout(schem, type)))
                            GuiWallManager.getInstance().unregisterInstance(gwi, true);
                    }
                }
            }
        }
    }
}
