package fr.pandalunatique.tardisplugin.world.artron;

import fr.pandalunatique.tardisplugin.util.ChanceLib;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtronCloud {

    public static final int MAX_CLOUDS = 30;
    public static final int SPAWN_CHANCE = 2; // IN PERCENT
    public static final int VISUAL_REFRESH_RATE = 5; // Ticks
    public static final int MIN_LIFETIME = 600; // 30 seconds in ticks
    public static final int MAX_LIFETIME = 2400; // 2 minutes in ticks

    @Getter private final Location location;
    @Getter private final Chunk chunk;
    @Getter @Setter private int count;
    @Getter @Setter private int lifetime;

    private static Set<ArtronCloud> clouds = new HashSet<>();

    public ArtronCloud(Location location) {
        this.location = location;
        this.chunk = location.getChunk();
        this.count = 1;

    }

    public ArtronCloud(Location location, int count) {

        this.location = location;
        this.chunk = location.getChunk();
        this.count = count;
        this.lifetime = ChanceLib.randomInteger(MIN_LIFETIME / VISUAL_REFRESH_RATE, MAX_LIFETIME / VISUAL_REFRESH_RATE);

    }

    public static boolean addArtronCloud(ArtronCloud cloud) {

        if(getArtronCloudCount() < MAX_CLOUDS) {
            clouds.add(cloud);
            return true;
        }
        return false;

    }

    public static void removeArtronCloud(ArtronCloud cloud) {

        clouds.remove(cloud);

    }

    public static void clearChunk(Chunk chunk) {

        clouds.forEach((cloud) -> {
            if(cloud.getChunk().equals(chunk)) {
                clouds.remove(cloud);
            }
        });

    }

    public static Set<ArtronCloud> getArtronClouds() {

        return clouds;

    }

    public static Set<ArtronCloud> getArtronClouds(Chunk chunk) {

        return clouds.stream()
                     .filter((cloud) -> cloud.getChunk().equals(chunk))
                     .collect(Collectors.toSet());

    }

    public static int getArtronCloudCount() {
        return clouds.size();
    }

    @EventHandler
    public void onClickCloud(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();
        if (entity.getType() == EntityType.ARMOR_STAND) {
            if (entity.getCustomName().contains("Artron")) {
                if (p.getInventory().getItemInMainHand().getType() == Material.GLASS_BOTTLE) {
                    int artronBottle = Integer.valueOf(entity.getCustomName().split("-")[1]);
                    p.getInventory().addItem(new ItemStack(Material.HONEY_BOTTLE));
                    artronBottle -= 1;
                    ItemStack item = p.getInventory().getItemInMainHand();
                    if (item.getAmount() > 1) item.setAmount(item.getAmount() - 1);
                    else item = new ItemStack(Material.AIR);
                    p.getInventory().setItemInMainHand(item);
                    if (artronBottle <= 0) {
                        entity.remove();
                    }else {
                        entity.setCustomName("Artron-" + artronBottle);
                    }
                }
            }
        }
    }

}
