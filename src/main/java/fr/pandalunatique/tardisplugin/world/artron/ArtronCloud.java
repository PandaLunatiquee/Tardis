package fr.pandalunatique.tardisplugin.world.artron;

import fr.pandalunatique.tardisplugin.util.ChanceLib;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtronCloud {

    public static final int MAX_CLOUDS = 30;
    public static final int SPAWN_CHANCE = 1; // IN PERCENT
    public static final int VISUAL_REFRESH_RATE = 8; // Ticks
    public static final int MIN_LIFETIME = 600; // 30 seconds in ticks
    public static final int MAX_LIFETIME = 2400; // 2 minutes in ticks
    public static final double COLLECT_REACH = 4;
    public static final double DANGER_REACH = 2;

    @Getter private final Location location;
    @Getter private final Chunk chunk;
    @Getter @Setter private int count;
    @Getter @Setter private int lifetime;

    private static Map<Chunk, ArtronCloud> clouds = new HashMap<>();

    public ArtronCloud(Location location) {
        this(location, ChanceLib.randomInteger(1, 5));

    }

    public ArtronCloud(Location location, int count) {

        this.location = location;
        this.chunk = location.getChunk();
        this.count = count;
        this.lifetime = ChanceLib.randomInteger(MIN_LIFETIME / VISUAL_REFRESH_RATE, MAX_LIFETIME / VISUAL_REFRESH_RATE);

    }

    public static void addArtronCloud(ArtronCloud cloud) {

        if(getArtronCloudCount() < MAX_CLOUDS) {
            clouds.put(cloud.getChunk(), cloud);
        }

    }

    public static void removeArtronCloud(ArtronCloud cloud) {

        removeInChunk(cloud.getChunk());

    }

    public static void purge() {

        Set<Chunk> deadClouds = new HashSet<>();
        clouds.forEach((chunk, cloud) -> {
            if(cloud.getLifetime() <= 0) {
                deadClouds.add(chunk);
            }
        });

        deadClouds.forEach(clouds::remove);

    }

    public void updateLifetime() {

        this.lifetime -= 1;

    }

    public static void removeInChunk(Chunk chunk) {

        clouds.remove(chunk);

    }

    public void collectArtron() {

        this.count -= 1;
        if(this.count == 0) {
            removeArtronCloud(this);
        }

    }

    public static Set<ArtronCloud> getReachableArtronCloud(Player p) {

        Set<ArtronCloud> result = new HashSet<>();

        clouds.forEach((__, cl) -> {

            if(p.getLocation().distance(cl.getLocation()) <= COLLECT_REACH) {

                result.add(cl);

            }

        });

        return result;

    }

    public static ArtronCloud getPlayerLookedArtronCloud(Player p) {

        Chunk c = p.getLocation().getChunk();
        Set<ArtronCloud> reachable = getReachableArtronCloud(p);

        if(reachable.size() > 0) {

            Location init = p.getEyeLocation();
            Vector dir = init.getDirection();

            for(ArtronCloud artron : reachable) {

                Location current = init.clone();

                for(int i = 0; i < (int) COLLECT_REACH; i++) {

                    if(current.distance(artron.getLocation()) < 1) {

                        return artron;

                    }

                    current.add(dir);

                }

            }

            return null;

        } else return null;

    }

    public static Map<Chunk, ArtronCloud> getArtronClouds() {

        return clouds;

    }

    public static int getArtronCloudCount() {
        return clouds.size();
    }

}
