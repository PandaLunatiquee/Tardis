package fr.pandalunatique.tardisplugin.player.tool.forcefield;

import fr.pandalunatique.tardisplugin.item.TardisItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ForceField implements Listener {

    HashMap<Location, Object> shieldsList = new HashMap<>();
    HashMap<Player, Object> inventoryAccess = new HashMap<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack is = e.getItem();
        if (is == null || is.getType() == Material.AIR) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (is.getType() == TardisItem.FORCE_FIELD.getItemStack().getType() && is.getItemMeta().getDisplayName().equals(TardisItem.FORCE_FIELD.getItemStack().getItemMeta().getDisplayName())) {
                if (e.getClickedBlock().getType() != Material.WATER && e.getClickedBlock().getType() != Material.LAVA) {
                    e.setCancelled(true);
                    Location center = e.getClickedBlock().getLocation();
                    center.add(0, 1, 0);
                    ArmorStand as = (ArmorStand) center.getWorld().spawnEntity(center, EntityType.ARMOR_STAND);
                    as.setSmall(true);
                    as.setCustomName("forceshield");
                    as.setGravity(false);
                    as.setInvulnerable(true);
                    as.setCanPickupItems(false);
                    Arrays.stream(EquipmentSlot.values()).forEach(slot -> {
                        as.addEquipmentLock(slot, ArmorStand.LockType.REMOVING_OR_CHANGING);
                    });
                    as.getEquipment().setItem(EquipmentSlot.HEAD, TardisItem.FORCE_FIELD.getItemStack());
                    p.getInventory().remove(e.getItem());
                    ForceFieldGenerator fsg = new ForceFieldGenerator();
                    fsg.init(center);
                    shieldsList.put(center, fsg);
                }
            }
        }
    }

    @EventHandler
    public void onLeftClick(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof ArmorStand) {
            if (e.getDamager() instanceof Player) {
                Player p = (Player) e.getDamager();
                ArmorStand as = (ArmorStand) entity;
                if (Objects.requireNonNull(as.getCustomName()).contains("forceshield")) {
                    e.setCancelled(true);
                    if (p.isSneaking()) {
                        ForceFieldGenerator fsg = (ForceFieldGenerator) shieldsList.get(as.getLocation());
                        if (!fsg.stateShield) {
                            as.remove();
                            p.getInventory().addItem(TardisItem.FORCE_FIELD.getItemStack());
                            shieldsList.remove(as.getLocation());
                        } else {
                            p.sendMessage("Veuillez désactiver le champ de force !");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();
        if (entity instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) entity;
            if (as.getCustomName() == null) return;
            if (as.getCustomName().contains("forceshield")) {
                ForceFieldGenerator fsg = (ForceFieldGenerator) shieldsList.get(as.getLocation());
                if (!inventoryAccess.containsValue(fsg)) {
                    Inventory inv = fsg.inv;
                    inventoryAccess.put(p, fsg);
                    p.openInventory(inv);
                } else {
                    p.sendMessage("Il y a déjà quelqu'un !");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if (e.getView().getTitle().contains("Champ de force")) {
            if (e.getRawSlot() >= 0 && e.getRawSlot() <= 29 || e.getRawSlot() >= 33 && e.getRawSlot() <= 54)
                e.setCancelled(true);
            if (e.getRawSlot() == 13) {
                ForceFieldGenerator fsg = (ForceFieldGenerator) inventoryAccess.get(p);
                if (fsg.stateShield) {
                    fsg.setStateShield(false);
                } else {
                    fsg.setStateShield(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        if (inv.getType() == InventoryType.CHEST) {
            if (inventoryAccess.containsKey(p)) {
                ForceFieldGenerator fsg = (ForceFieldGenerator) inventoryAccess.get(p);
                for (int i = 30; i <= 32; i++) {
                    ItemStack is = inv.getItem(i);
                    if (is != null) {
                        int a = is.getAmount();
                        is.setAmount(1);
                        if (!(is.equals(TardisItem.ARTRON_BOTTLE.getItemStack()))) {
                            is.setAmount(a);
                            inv.setItem(i, new ItemStack(Material.AIR));
                            Location loc = fsg.loc;
                            loc.getWorld().dropItem(loc, is);
                        }
                        is.setAmount(a);
                    }
                }
                inventoryAccess.remove(p);
            }
        }
    }

    private boolean isSimilar(ItemStack is1, ItemStack is2) {
        if (is1.getType() == is2.getType()) {
            if (is1.getItemMeta() == is2.getItemMeta()) {
                if (is1.getData() == is2.getData()) {
                    if (is1.getEnchantments() == is2.getEnchantments()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
