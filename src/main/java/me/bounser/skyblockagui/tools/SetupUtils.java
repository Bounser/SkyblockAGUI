package me.bounser.skyblockagui.tools;

import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.ItemFrame;

public class SetupUtils {

    private static SetupUtils instance;

    public static SetupUtils getInstance() {
        if (instance != null) return instance;

        SetupUtils.instance = new SetupUtils();
        return instance;
    }

    public void setupItemFrames(Island is, String type) {

        Data data = Data.getInstance();
        String schem = data.getSchematic(is);
        Location placingLoc = data.getPlacingLocation(is, type);

        int width = data.getWidth(schem, type);
        int height = data.getHeight(schem, type);

        placingLoc.add(0, +1, 0);

        for (int i = 0; i < width; i++) {

            placingLoc.add(0, -height, 0);

            for (int j = 0; j < height; j++) {

                GlowItemFrame itemFrame = (GlowItemFrame) placingLoc.getWorld().spawnEntity(placingLoc, EntityType.GLOW_ITEM_FRAME);
                itemFrame.setVisible(false);

                placingLoc.add(0, +1, 0);
            }

            switch (data.getDirection(schem, type).toString().toLowerCase()) {
                case "west":
                    placingLoc.add(0, 0, +1);
                    break;
                case "east":
                    placingLoc.add(0, 0, -1);
                    break;
                case "north":
                    placingLoc.add(-1, 0, 0);
                    break;
                case "south":
                    placingLoc.add(+1, 0, 0);
                    break;
            }
        }
    }

    public void clearArea(Island is, String type) {

        Data data = Data.getInstance();

        Location loc = data.getPlacingLocation(is, type);
        String schem = data.getSchematic(is);

        int width = data.getWidth(schem, type);
        int height = data.getHeight(schem, type);

        loc.add(0, +1, 0);

        for (int i = 0; i < width; i++) {

            loc.add(0, -height, 0);

            for (int j = 0; j < height; j++) {

                if(data.getSetAir())  loc.getBlock().setType(Material.VOID_AIR);

                for (Entity entity : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {

                    if(entity instanceof ItemFrame) entity.remove();

                }

                loc.add(0, +1, 0);
            }

            switch (data.getDirection(schem, type).toString().toLowerCase()) {
                case "west":
                    loc.add(0, 0, +1);
                    break;
                case "east":
                    loc.add(0, 0, -1);
                    break;
                case "north":
                    loc.add(-1, 0, 0);
                    break;
                case "south":
                    loc.add(+1, 0, 0);
                    break;
            }
        }
    }

    public void setBackground(Island is, String type) {

        Data data = Data.getInstance();

        Location loc = data.getPlacingLocation(is, type);
        String schem = data.getSchematic(is);

        int width = data.getWidth(schem, type);
        int height = data.getHeight(schem, type);

        loc.add(0, +1, 0);

        switch (data.getDirection(schem, type).toString().toLowerCase()) {
            case "west":
                loc.add(+1, 0, 0);
                break;
            case "east":
                loc.add(-1, 0, 0);
                break;
            case "north":
                loc.add(0, 0, +1);
                break;
            case "south":
                loc.add(0, 0, -1);
                break;
        }

        for (int i = 0; i < width; i++) {

            loc.add(0, -height, 0);

            for (int j = 0; j < height; j++) {

                loc.getBlock().setType(Material.valueOf(data.getMat()));
                loc.add(0, +1, 0);
            }

            switch (data.getDirection(schem, type).toString().toLowerCase()) {
                case "west":
                    loc.add(0, 0, +1);
                    break;
                case "east":
                    loc.add(0, 0, -1);
                    break;
                case "north":
                    loc.add(-1, 0, 0);
                    break;
                case "south":
                    loc.add(+1, 0, 0);
                    break;
            }
        }
    }
}
