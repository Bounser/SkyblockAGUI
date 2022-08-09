package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.SkyblockAGUI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class RegisterManager {

    private static RegisterManager instance;

    public static RegisterManager getInstance(){
        if(instance != null) return instance;

        RegisterManager.instance = new RegisterManager();
        return instance;
    }

    List firstR = new ArrayList<Player>();
    List secondR = new ArrayList<Player>();

    public void setPlayerFirstRegister(Player p){ firstR.add(p); }
    public void setPlayerSecondRegister(Player p){ secondR.add(p); }

    public void removeRegister(Player p, String phase) {
        switch (phase) {
            case "first": firstR.remove(p);
            case "second": secondR.remove(p);
        }
    }

    public boolean isRegistering(Player p, String phase){
        switch(phase){
            case "first": if(firstR.contains(p)) return true;
            case "second": if(secondR.contains(p)) return true;
            case "both": if(firstR.contains(p) || secondR.contains(p)) return true;
        }
        return false;
    }

    public void saveFirstPosition(Player player, Entity entity){

        Data data = Data.getInstance();

        Island is = SuperiorSkyblockAPI.getIslandAt(player.getLocation());
        String type = data.getType(player);
        String schem = data.getSchemFromPlayer(player);

        Location center = data.getCenterLocation(player, type);
        Location interact = entity.getLocation();

        int[] offset = new int[3];
        offset[0] = interact.getBlockX() - center.getBlockX();
        offset[1] = interact.getBlockY() - center.getBlockY();
        offset[2] = interact.getBlockZ() - center.getBlockZ();

        data.setValue(type, schem, "offset.x", String.valueOf(offset[0]));
        data.setValue(type, schem, "offset.y", String.valueOf(offset[1]));
        data.setValue(type, schem, "offset.z", String.valueOf(offset[2]));

        data.save();

        switch(entity.getFacing().toString()){
            case "BlockFace.EAST": data.setValue(type, schem, "facing", "east");
            case "BlockFace.WEAST": data.setValue(type, schem, "facing", "weast");
            case "BlockFace.NORTH": data.setValue(type, schem, "facing", "north");
            case "BlockFace.SOUTH": data.setValue(type, schem, "facing", "south");
        }

        data.save();

        RegisterManager.getInstance().removeRegister(player, "first");
        player.sendMessage(ChatColor.GREEN + "First position set correctly!");

        new BukkitRunnable() {
            @Override
            public void run() {
                RegisterManager.getInstance().setPlayerSecondRegister(player);
            }
        }.runTaskLater(SkyblockAGUI.getInstance(), 10);
    }

    public void saveSecondPosition(Player player, Entity entity){

        Data data = Data.getInstance();

        Island is = SuperiorSkyblockAPI.getIslandAt(player.getLocation());
        Location second = entity.getLocation();

        String type = data.getType(player);

        Location first = data.getCenterLocation(player, type).add(
                data.getOffset(type)[0],
                data.getOffset(type)[1],
                data.getOffset(type)[2]
        );

        int height = first.getBlockY() - second.getBlockY() +1;
        int width;
        if (main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("west") ||
                main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("east")) {
            width = abs(abs(first.getBlockZ()) - abs(second.getBlockZ())) +1;
        } else {
            width = abs(abs(first.getBlockX()) - abs(second.getBlockX())) +1;
        }

        main.getConfig().set("Islands." + type + ".height", height);
        main.getConfig().set("Islands." + type + ".wide", width);
        main.saveConfig();

        event.getPlayer().sendMessage(ChatColor.GREEN + "Second position set correctly!");
        event.getPlayer().sendMessage(ChatColor.GREEN + "GUI registered correctly!");
        registering2.remove(event.getPlayer().getName());


    }


}
