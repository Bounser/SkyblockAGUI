package me.bounser.skyblockagui.commands;

import me.bounser.skyblockagui.tools.Data;
import me.leoko.advancedgui.manager.GuiWallManager;
import me.leoko.advancedgui.utils.GuiWallInstance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoveGUIs implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (!sender.hasPermission("skyblockagui.admin")) {
                sender.sendMessage(ChatColor.RED + "You don't have the required permission! (skyblockagui.admin)");
                return false;
            }
        }
        if(args.length != 1){
            sender.sendMessage(ChatColor.RED + "This action will delete ALL island GUIs. If you are sure execute: /removeguis confirm");
            return false;
        }

        if(args[0].equalsIgnoreCase("confirm")){
            int i = 0;
            for (GuiWallInstance gwi : GuiWallManager.getInstance().getActiveInstances()) {
                for (String schem : Data.getInstance().getSchematics()) {
                    for (String type : new ArrayList<String>(Arrays.asList("overworld", "nether", "the_end"))) {
                        if (gwi.getLayout().getName().equals(Data.getInstance().getLayout(schem, type))){
                            Bukkit.broadcastMessage(gwi.getLocation().getDirection().toString());
                            GuiWallManager.getInstance().unregisterInstance(gwi, true);
                            i++;
                        }
                    }
                }
            }
            sender.sendMessage(ChatColor.RED + "You deleted: " + i + " GUIs.");
        }

            return false;

    }
}
