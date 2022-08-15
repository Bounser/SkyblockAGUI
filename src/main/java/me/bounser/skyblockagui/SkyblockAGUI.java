package me.bounser.skyblockagui;

import me.bounser.skyblockagui.commands.CancelCommand;
import me.bounser.skyblockagui.commands.RemoveGUIs;
import me.bounser.skyblockagui.commands.SetCommand;
import me.bounser.skyblockagui.commands.SetTabComplete;
import me.bounser.skyblockagui.listeners.ConnectionListener;
import me.bounser.skyblockagui.listeners.InteractionListener;
import me.bounser.skyblockagui.listeners.IslandListener;
import me.bounser.skyblockagui.listeners.TeleportListener;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.InstancesManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SkyblockAGUI extends JavaPlugin {

    private static SkyblockAGUI instance;

    public static SkyblockAGUI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        Data data = Data.getInstance();

        // Island creation/disband
        Bukkit.getPluginManager().registerEvents(new IslandListener(), this);

        // Resources regarding dynamic placing
        if (data.dynamicPlacing()) {
            Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
            Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);

            InstancesManager.getInstance().checkForPlayers();

            Bukkit.getLogger().info("Dynamic placing module loaded.");
        }

        // Registration
        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);

        // Commands
        getCommand("setguilocation").setExecutor(new SetCommand());
        getCommand("cancel").setExecutor(new CancelCommand());
        getCommand("removeguis").setExecutor(new RemoveGUIs());

        getCommand("setguilocation").setTabCompleter(new SetTabComplete());

        Bukkit.getLogger().info("Plugin successfully loaded.");

        File Layout = new File(this.getDataFolder().getParent() + "/AdvancedGUI/layout/DIO.yml");
        FileConfiguration LayoutConfig = YamlConfiguration.loadConfiguration(Layout);
        LayoutConfig.set("test","asd");
    }
}
