package fr.pandalunatique.tardisplugin.visual;

import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisFacing;
import fr.pandalunatique.tardisplugin.tardis.TardisRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import java.util.Iterator;

public class TardisCollisionVisual implements VisualTask {

    public static int VISUAL_DELAY = 0;
    public static int VISUAL_REFRESH_RATE = 5;

    public static boolean coordinateEquals(Location loc1, Location loc2) {
        return loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockZ() == loc2.getBlockZ();
    }

    public static Runnable getVisualRunnable() {

        return () -> {

            Iterator<Tardis> iterator = TardisRegistry.getRegistry().getRegistered().iterator();

            iterator.forEachRemaining(tardis -> {

                if(!tardis.isNew() && tardis.isLanded() && tardis.isDoorOpen() && tardis.getTardisLocation() != null) {


                    Location tloc = tardis.getTardisLocation();
                    BlockFace relativeFace = BlockFace.valueOf(tardis.getFacing().name());

                    Location r1 = tloc.getBlock().getRelative(relativeFace).getLocation();
                    Location r2 = r1.getBlock().getRelative(relativeFace).getLocation();

                    Bukkit.getOnlinePlayers().forEach(player -> {

                        Location ploc = player.getLocation();

                        if(ploc != null && tloc.getWorld().equals(ploc.getWorld()) && ploc.distance(tloc) <= 10) {

                            boolean valid = coordinateEquals(ploc, tloc) || coordinateEquals(ploc, r1) || coordinateEquals(ploc, r2);

                            if (valid) {

                                player.sendBlockChange(tardis.getTardisLocation(), Material.AIR.createBlockData());
                                player.sendBlockChange(tardis.getTardisLocation().getBlock().getRelative(BlockFace.UP).getLocation(), Material.AIR.createBlockData());

                            } else {
                                player.sendBlockChange(tardis.getTardisLocation(), Material.BARRIER.createBlockData());
                                player.sendBlockChange(tardis.getTardisLocation().getBlock().getRelative(BlockFace.UP).getLocation(), Material.AIR.createBlockData());

                            }

                        }

                    });

                }

            });

        };

    }

}
