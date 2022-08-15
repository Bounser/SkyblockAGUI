package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.SkyblockAGUI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;
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
    List<Player> firstR = new ArrayList<Player>();
    List<Player> secondR = new ArrayList<Player>();

    public void setPlayerFirstRegister(Player p){ firstR.add(p); }
    public void setPlayerSecondRegister(Player p){ secondR.add(p); }

    public void removeRegister(Player p) {
        firstR.remove(p);
        secondR.remove(p);
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

        Location center = data.getCenterLocation(is, type);
        Location interact = entity.getLocation();

        // Gets offset
        data.setValue(schem, type, "offset.x", interact.getBlockX() - center.getBlockX());
        data.setValue(schem, type, "offset.y", interact.getBlockY() - center.getBlockY());
        data.setValue(schem, type, "offset.z", interact.getBlockZ() - center.getBlockZ());

        // Gets the direction from the entity.
        data.setString(schem, type, "facing", ((Directional) entity).getFacing().toString().toLowerCase());

        // Feedback
        removeRegister(player);
        player.sendMessage(ChatColor.GREEN + "First position set correctly! Click on the bottom right item frame to finish registering the GUI");

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
        Location first = data.getPlacingLocation(is, type);

        if(!type.equals(data.getType(first))) return;

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
        data.setValue(schem, type, "height", height);
        data.setValue(schem, type, "width", width);

        data.setBoolean(schem, type, true);

        // Feedback.
        player.sendMessage(ChatColor.GREEN + "Second position set correctly");

        removeRegister(player);
        InstancesManager.getInstance().placeGUI(
                data.getPlacingLocation(is, type),
                data.getDirection(schem, type),
                data.getLayout(schem, type),
                false);

        player.sendMessage(ChatColor.GREEN + "GUI registered correctly!");

    }
}
