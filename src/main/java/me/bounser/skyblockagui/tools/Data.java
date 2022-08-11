package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.SkyblockAGUI;
import me.leoko.advancedgui.utils.Direction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class Data {

    private static Data instance;
    private static SkyblockAGUI main;

    private Data(){
        main = SkyblockAGUI.getInstance();
        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    public static Data getInstance(){
        if(instance != null) return instance;

        Data.instance = new Data();
        return instance;
    }

    public void save(){ main.saveConfig(); }

    // OPTIONS

    public boolean dynamicPlacing(){ return main.getConfig().getBoolean("Options.dynamicPlacing.enabled"); }

    public int getMode(){ return main.getConfig().getInt("Options.dynamicPlacing.mode"); }

    public boolean getSetAir(){ return main.getConfig().getBoolean("Options.remove_screen_blocks"); }

    public boolean getReplace(){ return main.getConfig().getBoolean("Options.replace_blocks.enabled"); }

    public String getMat(){ return main.getConfig().getString("Options.replace_blocks.material"); }

    // SETTERS

    public void setValue(String schem, String type, String path, int value){
        main.getConfig().set("GUIs." + schem + "." + type + "." + path, value);
        save();
    }

    public void setString(String schem, String type, String path, String value){
        main.getConfig().set("GUIs." + schem + "." + type + "." + path, value);
        save();
    }

    // GETTERS

    public boolean getEnabledGUI(String schem, String type){ return main.getConfig().getBoolean("GUIs." + schem + "." + type + ".enabled");}

    public int getRadius(){ return main.getConfig().getInt("Options.activation_radius"); }

    public String getFacing(String schem, String type){ return main.getConfig().getString("GUIs." + schem + "." + type + ".facing"); }

    public List<String> getSchematics(){ return (List<String>) main.getConfig().getList("schematics"); }

    public String getSchematic(Island is){
        for(String schem : getSchematics()){
            if(is.getSchematicName().equals(schem)) return schem;
        }
        return null;
    }

    public String getSchemFromPlayer(Player player){ return SuperiorSkyblockAPI.getIslandAt(player.getLocation()).getSchematicName(); }

    public int getOffsetX(String schem, String type){ return main.getConfig().getInt("GUIs." + schem + "." + type + ".offset.x"); }
    public int getOffsetY(String schem, String type){ return main.getConfig().getInt("GUIs." + schem + "." + type + ".offset.y"); }
    public int getOffsetZ(String schem, String type){ return main.getConfig().getInt("GUIs." + schem + "." + type + ".offset.z"); }

    public Direction getDirection(String schem, String type){
        Direction direction = null;
        if(main.getConfig().getString("GUIs." + schem + "." + type + ".facing").equalsIgnoreCase("west")) direction = Direction.WEST;
        if(main.getConfig().getString("GUIs." + schem + "." + type + ".facing").equalsIgnoreCase("north")) direction = Direction.NORTH;
        if(main.getConfig().getString("GUIs." + schem + "." + type + ".facing").equalsIgnoreCase("east")) direction = Direction.EAST;
        if(main.getConfig().getString("GUIs." + schem + "." + type + ".facing").equalsIgnoreCase("south")) direction = Direction.SOUTH;
        return direction;
    }

    public String getLayout(String schem, String type){
        return main.getConfig().getString("GUIs." + schem + "." + type + ".layout");
    }

    public World.Environment getEnviromentFromType(String type) {

        World.Environment environment = null;
        switch(type){
            case "overworld": environment = World.Environment.NORMAL; break;
            case "nether": environment = World.Environment.NETHER; break;
            case "the_end": environment = World.Environment.THE_END; break;
        }
        return environment;
    }

    public String getType(Location loc) {
        switch(loc.getWorld().getEnvironment()){
            case NORMAL: return "overworld";
            case NETHER: return "nether";
            case THE_END: return "the_end";
            default: return null;
        }
    }

    public int getWidth(String schem, String type){ return main.getConfig().getInt("GUIs." + schem + "." + type + ".width"); }

    public int getHeight(String schem, String type){ return main.getConfig().getInt("GUIs." + schem + "." + type + ".height"); }

    public Location getPlacingLocation(Island island, String type) {
        String schem = island.getSchematicName();
        return getCenterLocation(island, type).add(getOffsetX(schem, type),getOffsetY(schem, type),getOffsetZ(schem, type));
    }

    public Location getCenterLocation(Island is, String type) {
        if(is == null) return null;

        return is.getCenter(getEnviromentFromType(type));
    }
}
