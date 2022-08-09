package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.SkyblockAGUI;
import me.leoko.advancedgui.utils.Direction;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Data {

    private static Data instance;
    private static SkyblockAGUI main;

    private Data(){
        main = SkyblockAGUI.getInstance();
    }

    public static Data getInstance(){
        if(instance != null) return instance;

        Data.instance = new Data();
        return instance;
    }

    public void save(){ main.saveConfig(); }

    // OPTIONS

    public boolean dynamicPlacing(){ return main.getConfig().getBoolean("Options.dynamicPlacing.enabled"); }

    // SETTERS

    public void setValue(String type, String schem, String path, String value){
        main.getConfig().set("Islands." + type + "." + schem + "." + path, value);
    }

    public void setLayout(String LayoutName, String type, String schem){
        main.getConfig().set("Islands." + type + ".layout", LayoutName);
        main.saveConfig();
    }

    // GETTERS

    public int getRadius(){ return main.getConfig().getInt("activation_radius"); }

    public String getSchemFromPlayer(Player player){ return SuperiorSkyblockAPI.getIslandAt(p.getLocation()).getSchematicName(); }

    public int[] getOffset(String type){
        int i[] = new int[3];
        i[0] = main.getConfig().getInt("Islands." + type + ".offset.x");
        i[1] = main.getConfig().getInt("Islands." + type + ".offset.y");
        i[2] = main.getConfig().getInt("Islands." + type + ".offset.z");
        return i;
    }

    public boolean valuesSet(String type){
        if(main.getConfig().getString("Islands." + type + ".layout") == null) return false;
        return true;
    }

    public Direction getDirection(String type){
        Direction direction = null;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("west")) direction = Direction.WEST;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("north")) direction = Direction.NORTH;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("east")) direction = Direction.EAST;
        if(main.getConfig().getString("Islands." + type + ".facing").equalsIgnoreCase("south")) direction = Direction.SOUTH;
        return direction;
    }

    public String getLayout(String type){
        return main.getConfig().getString("Islands." + type + ".layout");
    }

    public World.Environment getEnviroment(Player nick) {

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

    public String getType(Player player) {
        switch(player.getWorld().getEnvironment()){
            case NORMAL: return "overworld";
            case NETHER: return "nether";
            case THE_END: return "the_end";
            default: return null;
        }
    }

    public int getWidth(String type){ return main.getConfig().getInt("Islands." + type + ".wide"); }

    public int getHeight(String type){ return main.getConfig().getInt("Islands." + type + ".height"); }

    public Location getLocation(Island island, String type) {
        return island.getCenter(Data.getEnviromentFromType(type)).add(getOffset(type)[0],getOffset(type)[1],getOffset(type)[2]);
    }

    public Location getCenterLocation(Player player, String type) {
        if(!(SuperiorSkyblockAPI.getPlayer(player).getIsland() == null))
            return SuperiorSkyblockAPI.getPlayer(player).getIsland().getCenter(Data.getEnviromentFromType(type)).add(getOffset(type)[0],getOffset(type)[1],getOffset(type)[2]);
        return null;
    }

}
