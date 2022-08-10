package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.ItemFrame;

import java.util.Collection;

public class SetupUtils {

    private static SetupUtils instance;

    public static SetupUtils getInstance(){
        if(instance != null) return instance;

        SetupUtils.instance = new SetupUtils();
        return instance;
    }

    public void setupItemFrames(Island is, String type) {

        Bukkit.broadcastMessage("setupitemframes");

        Data data = Data.getInstance();
        String schem = data.getSchematic(is);
        Location placingLoc = data.getLocation(is, type);

        int width = data.getWidth(schem, type);
        int height = data.getHeight(schem, type);

        placingLoc.add(0,+1,0);

        for (int i = 0; i < width; i++) {

            placingLoc.add(0, -height, 0);

            for (int j = 0; j < height; j++) {

                if (!checkForItemFrames(placingLoc.getWorld().getNearbyEntities(placingLoc.add(0,+0.2,0),0.5,0.1,0.5), is, type)){

                    GlowItemFrame itemFrame = (GlowItemFrame) placingLoc.getWorld().spawnEntity(placingLoc.add(0,-0.2,0), EntityType.GLOW_ITEM_FRAME);
                    itemFrame.setVisible(false);

                } else placingLoc.add(0,-0.2,0);

                placingLoc.add(0, +1, 0);
            }
            switch (data.getDirection(schem, type).toString()) {
                case "WEST":
                    placingLoc.add(0, 0, +1);
                    break;
                case "EAST":
                    placingLoc.add(0, 0, -1);
                    break;
                case "NORTH":
                    placingLoc.add(-1, 0, 0);
                    break;
                case "SOUTH":
                    placingLoc.add(+1, 0, 0);
                    break;
            }
        }
    }

    private boolean checkForItemFrames(Collection<Entity> entities, Island is, String type){

        Bukkit.broadcastMessage("checkforitemframes");

        Data data = Data.getInstance();
        String schem = data.getSchematic(is);

        for (Entity e : entities) {
            if (e instanceof ItemFrame) {
                switch (data.getDirection(schem, type).toString()) {
                    case "WEST": ((ItemFrame) e).setFacingDirection(BlockFace.WEST); break;
                    case "EAST": ((ItemFrame) e).setFacingDirection(BlockFace.EAST); break;
                    case "NORTH": ((ItemFrame) e).setFacingDirection(BlockFace.NORTH); break;
                    case "SOUTH": ((ItemFrame) e).setFacingDirection(BlockFace.SOUTH); break;
                }

                ((ItemFrame) e).setVisible(false);
                return true;
            }
        }
        return false;
    }

    public void clearArea(Island is, String type) {

        Bukkit.broadcastMessage("cleararea");

        Data data = Data.getInstance();

        Location loc = data.getLocation(is, type);
        String schem = data.getSchematic(is);

        int width = data.getWidth(schem, type);
        int height = data.getHeight(schem, type);

        loc.add(0, +1, 0);

        for (int i = 0; i < width; i++) {

            loc.add(0, -height, 0);

            for (int j = 0; j < height; j++) {

                loc.getBlock().setType(Material.VOID_AIR);
                loc.add(0, +1, 0);
            }

            switch (data.getDirection(schem, type).toString()) {
                case "WEST": loc.add(0, 0, +1); break;
                case "EAST": loc.add(0, 0, -1); break;
                case "NORTH": loc.add(-1, 0, 0); break;
                case "SOUTH": loc.add(+1, 0, 0); break;
            }
        }
    }

    public void setBackground (Island is, String type){

        Bukkit.broadcastMessage("setbackground");

        Data data = Data.getInstance();

        Location loc = data.getLocation(is, type);
        String schem = data.getSchematic(is);

        int width = data.getWidth(schem, type);
        int height = data.getHeight(schem, type);

        loc.add(0, +1, 0);

        switch (data.getDirection(schem, type).toString()) {
            case "WEST": loc.add(1, 0, 0); break;
            case "EAST": loc.add(-1, 0, 0); break;
            case "NORTH": loc.add(0, 0, 1); break;
            case "SOUTH": loc.add(0, 0, -1); break;
        }

        for (int i = 0; i < width; i++) {

            loc.add(0, -height, 0);

            for (int j = 0; j < height; j++) {

                loc.getBlock().setType(Material.valueOf("Material." + data.getMat()));
                Bukkit.broadcastMessage(Material.STONE.toString());
                Bukkit.broadcastMessage("Material." + data.getMat());
                loc.add(0, +1, 0);
            }

            switch (data.getDirection(schem, type).toString()) {
                case "WEST": loc.add(0, 0, +1); break;
                case "EAST": loc.add(0, 0, -1); break;
                case "NORTH": loc.add(-1, 0, 0); break;
                case "SOUTH": loc.add(+1, 0, 0); break;
            }
        }
    }
}
