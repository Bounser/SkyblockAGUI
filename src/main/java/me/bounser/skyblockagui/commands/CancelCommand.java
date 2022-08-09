package me.bounser.skyblockagui.commands;

import me.bounser.skyblockagui.tools.RegisterManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CancelCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.printf(ChatColor.RED + "Command is not available for console.");
            return false;
        }

        String phase = null;

        for(String auxPhase  : new ArrayList<String>(Arrays.asList("first", "second")) ){

            if(RegisterManager.getInstance().isRegistering((Player) sender, auxPhase)){

                RegisterManager.getInstance().removeRegister((Player) sender, auxPhase);
                phase = auxPhase;

            }
        }
        sender.sendMessage(ChatColor.RED +"You are no longer registering the "+ ChatColor.GREEN + phase + ChatColor.RED +" position.");

        return false;
    }
}
