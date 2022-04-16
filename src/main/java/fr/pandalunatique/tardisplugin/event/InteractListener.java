package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceFieldGenerator;
import fr.pandalunatique.tardisplugin.util.ItemLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack is = e.getItem();
        if (is == null || is.getType() == Material.AIR) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ItemLib.isEqual(is, TardisItem.FORCE_FIELD.getItemStack())) {
                if (e.getClickedBlock().getType() != Material.WATER && e.getClickedBlock().getType() != Material.LAVA) {
                    e.setCancelled(true);
                    Location center = e.getClickedBlock().getLocation();
                    center.add(0, 1, 0);
                    ArmorStand as = (ArmorStand) center.getWorld().spawnEntity(center, EntityType.ARMOR_STAND);
                    as.setSmall(true);
                    as.setCustomName("ForceField");
                    as.setGravity(false);
                    as.setCanPickupItems(false);
                    Arrays.stream(EquipmentSlot.values()).forEach(slot -> {
                        as.addEquipmentLock(slot, ArmorStand.LockType.REMOVING_OR_CHANGING);
                    });
                    as.getEquipment().setItem(EquipmentSlot.HEAD, TardisItem.FORCE_FIELD.getItemStack());
                    p.getInventory().remove(e.getItem());
                    ForceFieldGenerator fsg = new ForceFieldGenerator();
                    fsg.init(center);
                    ForceField.shieldsList.put(center, fsg);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();
        if (entity instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) entity;
            if (as.getCustomName() == null) return;
            if (as.getEquipment().getHelmet() == null) return;
            if (ItemLib.isEqual(as.getEquipment().getHelmet(), TardisItem.FORCE_FIELD.getItemStack())) {
                ForceFieldGenerator fsg = (ForceFieldGenerator) ForceField.shieldsList.get(as.getLocation());
                if (!ForceField.inventoryAccess.containsValue(fsg)) {
                    Inventory inv = fsg.inv;
                    ForceField.inventoryAccess.put(p, fsg);
                    p.openInventory(inv);
                } else {
                    p.sendMessage("Il y a déjà quelqu'un !");
                }
            }
        }
    }

}
