package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.SkyblockAGUI;
import me.bounser.skyblockagui.listeners.InteractionListener;
import me.leoko.advancedgui.utils.Direction;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Data {

    private static Data instance;
    public static SkyblockAGUI main = SkyblockAGUI.getInstance();

    public static Data getInstance(){
        if(instance != null) return instance;

        Data.instance = new Data();
        return instance;
    }

    static public int[] getOffset(String type){
        int i[] = new int[3];
        i[0] = main.getConfig().getInt("Islands." + type + ".offset.x");
        i[1] = main.getConfig().getInt("Islands." + type + ".offset.y");
        i[2] = main.getConfig().getInt("Islands." + type + ".offset.z");
        return i;
    }

    static public boolean valuesSet(String type){
        if(main.getConfig().getString("Islands." + type + ".layout") == null) return false;
        return true;
    }

    static public Direction getDirection(String type){
        Direction direction = null;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("west")) direction = Direction.WEST;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("north")) direction = Direction.NORTH;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("east")) direction = Direction.EAST;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("south")) direction = Direction.SOUTH;
        return direction;
    }

    static public String getLayout(String type){
        return main.getConfig().getString("Islands." + type + ".layout");
    }

    public static void setLayout(String LayoutName, String type){
        main.getConfig().set("Islands." + type + ".layout", LayoutName);
        main.saveConfig();
    }

    static public World.Environment getEnviroment(Player nick) {

        World.Environment environment = World.Environment.NORMAL;

        if (nick.getWorld().getName().contains("_nether")) {
            environment = World.Environment.NETHER;
        } else if (nick.getWorld().getName().contains("_the_end")) {
            environment = World.Environment.THE_END;
        }

        return environment;
    }

    static public World.Environment getEnviromentFromType(String type) {

        World.Environment environment = World.Environment.NORMAL;

        if (type == "nether") environment = World.Environment.NETHER;
        if (type == "the_end") environment = World.Environment.THE_END;

        return environment;
    }

    static public String getType(Player player) {

        String type = null;

        if (player.getWorld().getName().contains("nether")) type = "nether"; else
        if (player.getWorld().getName().contains("the_end")) type = "the_end"; else
            type = "overworld";

        return type;
    }

    static public int getWidth(String type){ return main.getConfig().getInt("Islands." + type + ".wide"); }

    static public int getHeight(String type){ return main.getConfig().getInt("Islands." + type + ".height"); }

    public static Location getLocation(Island island, String type) {
        return island.getCenter(Data.getEnviromentFromType(type)).add(Data.getOffset(type)[0],Data.getOffset(type)[1],Data.getOffset(type)[2]);
    }

    public static Location getLocation(String nick, String type) {
        if(!(SuperiorSkyblockAPI.getPlayer(nick).getIsland() == null))
            return SuperiorSkyblockAPI.getPlayer(nick).getIsland().getCenter(Data.getEnviromentFromType(type)).add(Data.getOffset(type)[0],Data.getOffset(type)[1],Data.getOffset(type)[2]);
        return null;
    }

}
