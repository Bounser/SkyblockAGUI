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
        if(instance == null) instance = new RegisterManager();

        return instance;
    }

    // Lists containing all the players registering the specific step. (First or second)
    List firstR = new ArrayList<Player>();
    List secondR = new ArrayList<Player>();

    public void setPlayerFirstRegister(Player p){ firstR.add(p); }
    public void setPlayerSecondRegister(Player p){ secondR.add(p); }

    public void removeRegister(Player p, String phase) {
        switch (phase) {
            case "first": firstR.remove(p); break;
            case "second": secondR.remove(p); break;
        }
    }

    public boolean isRegistering(Player p, String phase){
        switch(phase){
            case "first": if(firstR.contains(p)) return true; break;
            case "second": if(secondR.contains(p)) return true; break;
            case "checkBoth": if(firstR.contains(p) || secondR.contains(p)) return true; break;
        }
        return false;
    }

    public void saveFirstPosition(Player player, Entity entity){

        Data data = Data.getInstance();

        Island is = SuperiorSkyblockAPI.getIslandAt(player.getLocation());
        String type = data.getType(entity.getLocation());
        String schem = data.getSchematic(is);

        Location center = data.getCenterLocation(player, type);
        Location interact = entity.getLocation();

        // Offset center-first position
        int[] offset = new int[3];
        offset[0] = interact.getBlockX() - center.getBlockX();
        offset[1] = interact.getBlockY() - center.getBlockY();
        offset[2] = interact.getBlockZ() - center.getBlockZ();
        // Saves values.
        data.setValue(schem, type, "offset.x", String.valueOf(offset[0]));
        data.setValue(schem, type, "offset.y", String.valueOf(offset[1]));
        data.setValue(schem, type, "offset.z", String.valueOf(offset[2]));

        // Gets the direction from the entity.
        switch(entity.getFacing().toString()){
            case "EAST": data.setValue(schem, type, "facing", "EAST");
            case "WEAST": data.setValue(schem, type, "facing", "WEAST");
            case "BNORTH": data.setValue(schem, type, "facing", "NORTH");
            case "SOUTH": data.setValue(schem, type, "facing", "SOUTH");
        }

        // Feedback
        removeRegister(player, "first");
        player.sendMessage(ChatColor.GREEN + "First position set correctly!");

        // Starts registering the second position.
        new BukkitRunnable() {
            @Override
            public void run() {
                setPlayerSecondRegister(player);
            }
        }.runTaskLater(SkyblockAGUI.getInstance(), 10);
    }

    public void saveSecondPosition(Player player, Entity entity){

        Data data = Data.getInstance();

        Island is = SuperiorSkyblockAPI.getIslandAt(player.getLocation());
        Location second = entity.getLocation();
        String schem = data.getSchematic(is);
        String type = data.getType(entity.getLocation());

        // Getting the first entity position.
        Location first = data.getCenterLocation(player, type).add(
                data.getOffset(schem, type)[0],
                data.getOffset(schem, type)[1],
                data.getOffset(schem, type)[2]
        );

        // Calculating the difference between the first and second position.
        int height = first.getBlockY() - second.getBlockY() +1;
        int width;
        if (data.getFacing(schem, type).equalsIgnoreCase("west") ||
            data.getFacing(schem, type).equalsIgnoreCase("east")) {
            width = abs(abs(first.getBlockZ()) - abs(second.getBlockZ())) +1;
        } else {
            width = abs(abs(first.getBlockX()) - abs(second.getBlockX())) +1;
        }

        // Saving results.
        data.setValue(schem, type, "height", String.valueOf(height));
        data.setValue(schem, type, "wide", String.valueOf(width));

        // Feedback.
        player.sendMessage(ChatColor.GREEN + "Second position set correctly");
        player.sendMessage(ChatColor.GREEN + "GUI registered correctly!");

        removeRegister(player, "second");
        InstancesManager.getInstance().placeGUI(
                data.getLocation(is, type),
                data.getDirection(schem, type),
                data.getLayout(schem, type),
                false);
    }
}
