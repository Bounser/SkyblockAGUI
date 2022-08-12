package me.bounser.skyblockagui.commands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import me.bounser.skyblockagui.tools.Data;
import me.bounser.skyblockagui.tools.RegisterManager;
import me.leoko.advancedgui.manager.LayoutManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            if(RegisterManager.getInstance().isRegistering((Player) sender, "checkBoth")){
                sender.sendMessage(ChatColor.RED + "You are already placing a GUI! cancel with /cancel"); return false;
            }

            if(SuperiorSkyblockAPI.getIslandAt(((Player) sender).getLocation())==null){
                sender.sendMessage(ChatColor.RED + "You are not on a valid island!"); return false;
            }

            if(args.length != 1){
                sender.sendMessage(ChatColor.RED + "Invalid usage of the command. Use: /setlocation <layoutName>"); return false;
            }

            if(LayoutManager.getInstance().getLayout(args[0]) == (null)){
                sender.sendMessage(ChatColor.RED + "That layout is not valid.");
                return false;
            }

            sender.sendMessage(ChatColor.GREEN + "You started the registering mode! Right-click in the top left corner of the desired location.");
            RegisterManager.getInstance().setPlayerFirstRegister((Player) sender);

            Data data = Data.getInstance();

            data.setString(data.getSchemFromPlayer((Player) sender), data.getType(((Player) sender).getLocation()), "layout", args[0]);

        } else {
            System.out.printf("Command is not available for console.");
        }
        return false;
    }
}
