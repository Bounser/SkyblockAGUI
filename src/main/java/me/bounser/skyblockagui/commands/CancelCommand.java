package me.bounser.skyblockagui.commands;

import me.bounser.skyblockagui.tools.RegisterManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CancelCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {

            Bukkit.getLogger().info(ChatColor.RED + "Command is not available for console.");
            return false;
        }

        if (!sender.hasPermission("skyblockagui.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have the required permission! (skyblockagui.admin)");
            return false;
        }

        String phase = null;

        for(String auxPhase  : Arrays.asList("first", "second") ){

            if(RegisterManager.getInstance().isRegistering((Player) sender, auxPhase)){

                RegisterManager.getInstance().removeRegister((Player) sender);
                phase = auxPhase;

            }
        }

        if(phase == null) {
            sender.sendMessage(ChatColor.BLUE + "There is nothing to cancel."); }
        else {
            sender.sendMessage(ChatColor.RED +"You are no longer registering the "+ ChatColor.GREEN + phase + ChatColor.RED +" position.");
        }

        return false;
    }
}
