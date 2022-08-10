package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.bounser.skyblockagui.SkyblockAGUI;
import me.leoko.advancedgui.utils.Direction;
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

    public boolean forceDelete(){ return main.getConfig().getBoolean("Options.dynamicPlacing.force_remove"); }

    public int getMode(){ return main.getConfig().getInt("Options.dynamicPlacing.mode"); }

    public boolean getSetAir(){ return main.getConfig().getBoolean("Options.remove_screen_blocks"); }

    public boolean getReplace(){ return main.getConfig().getBoolean("Options.replace_blocks.enabled"); }

    public String getMat(){ return main.getConfig().getString("Options.replace_blocks.material"); }

    // SETTERS

    public void setValue(String schem, String type, String path, String value){
        main.getConfig().set("GUIs." + schem + "." + type + "." + path, value);
        save();
    }

    public void setLayout(String LayoutName, String schem, String type){
        main.getConfig().set("GUIs." + schem + "." + type + ".layout", LayoutName);
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

    public int[] getOffset(String schem, String type){
        int i[] = new int[3];
        i[0] = main.getConfig().getInt("GUIs." + schem + "." + type + ".offset.x");
        i[1] = main.getConfig().getInt("GUIs." + schem + "." + type + ".offset.y");
        i[2] = main.getConfig().getInt("GUIs." + schem + "." + type + ".offset.z");
        return i;
    }

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

    static public World.Environment getEnviromentFromType(String type) {

        World.Environment environment = null;
        switch(type){
            case "overworld": environment = World.Environment.NORMAL; break;
            case "nether": environment = World.Environment.NETHER; break;
            case "the_end": environment =World.Environment.THE_END; break;
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

    public int getWidth(String schem, String type){ return main.getConfig().getInt("GUIs." + schem + "." + type + ".wide"); }

    public int getHeight(String schem, String type){ return main.getConfig().getInt("GUIs." + schem + "." + type + ".height"); }

    public Location getLocation(Island island, String type) {
        String schem = island.getSchematicName();
        return island.getCenter(Data.getEnviromentFromType(type)).add(getOffset(schem, type)[0],getOffset(schem, type)[1],getOffset(schem, type)[2]);
    }

    public Location getCenterLocation(Player player, String type) {
        if((SuperiorSkyblockAPI.getPlayer(player).getIsland() == null)) return null;

        String schem = SuperiorSkyblockAPI.getPlayer(player).getIsland().getSchematicName();
        return SuperiorSkyblockAPI.getPlayer(player).getIsland().getCenter(Data.getEnviromentFromType(type)).add(getOffset(schem, type)[0],getOffset(schem, type)[1],getOffset(schem, type)[2]);
    }
}
