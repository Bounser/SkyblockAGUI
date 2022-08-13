package me.bounser.skyblockagui.commands;

import me.leoko.advancedgui.manager.LayoutManager;
import me.leoko.advancedgui.utils.Layout;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetTabComplete implements TabCompleter {

    static private List<String> layouts = getLayoutNames();

    static public List<String> getLayoutNames(){
        List<String> layouts = new ArrayList<>();
        for(Layout l : LayoutManager.getInstance().getLayouts()){
            layouts.add(l.getName());
        }
        return layouts;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(!sender.hasPermission("skyblockagui.admin")) return null;

        final List<String> completes = new ArrayList<>();

        StringUtil.copyPartialMatches(args[0], layouts, completes);
        Collections.sort(completes);

        return completes;
    }

}
