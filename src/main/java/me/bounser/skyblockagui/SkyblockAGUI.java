package me.bounser.skyblockagui;

import me.bounser.skyblockagui.commands.CancelCommand;
import me.bounser.skyblockagui.commands.SetCommand;
import me.bounser.skyblockagui.listeners.InteractionListener;
import me.bounser.skyblockagui.tools.Data;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyblockAGUI extends JavaPlugin {

    private static SkyblockAGUI instance;
    public static SkyblockAGUI getInstance(){
        if(instance == null) instance = new SkyblockAGUI();
        return instance;
    }

    @Override
    public void onEnable() {



        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);

        getCommand("setlocation").setExecutor(new SetCommand());
        getCommand("cancel").setExecutor(new CancelCommand());
    }

    @Override
    public void onDisable() {

    }
}
