package fr.pandalunatique.tardis.artron;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ArtronCloud implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {

        Chunk chunk = e.getChunk();

        Random rnd = new Random();
        int r = rnd.nextInt(100);
        if (r <= 1) {
            Location rndLoc = chunk.getBlock(rnd.nextInt(16), 1,rnd.nextInt(16)).getLocation();
            Location loc = findSurface(rndLoc);
            ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            as.setVisible(false);
            as.setCustomNameVisible(false);
            as.setGravity(false);
            as.setCanPickupItems(false);
            as.setSmall(true);
            as.setInvulnerable(true);
            Arrays.stream(EquipmentSlot.values()).forEach(slot -> {
                as.addEquipmentLock(slot, ArmorStand.LockType.REMOVING_OR_CHANGING);
            });
            int r2 = rnd.nextInt(5);
            as.setCustomName("Artron-" + String.valueOf(r2));
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        for (Entity entity : e.getChunk().getEntities()) {
            if (entity.getType() == EntityType.ARMOR_STAND) {
                if (entity.getCustomName().contains("Artron")) {
                    entity.remove();
                }
            }
        }

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

    public Location findSurface(Location loc){
        loc.setY(1);
        while (loc.getBlock().getType() != Material.AIR) {
            loc.setY(loc.getY() + 1);
        }
        return loc;
    }

}
