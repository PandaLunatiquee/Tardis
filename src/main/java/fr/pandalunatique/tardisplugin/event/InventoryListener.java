package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceFieldGenerator;
import fr.pandalunatique.tardisplugin.util.ItemLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if (e.getView().getTitle().contains("Champ de force")) {
            if (e.getRawSlot() >= 0 && e.getRawSlot() <= 29 || e.getRawSlot() >= 33 && e.getRawSlot() <= 54)
                e.setCancelled(true);
            if (e.getRawSlot() == 13) {
                ForceFieldGenerator fsg = (ForceFieldGenerator) ForceField.inventoryAccess.get(p);
                fsg.setStateShield(!fsg.stateShield);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        if (inv.getType() == InventoryType.CHEST) {
            if (ForceField.inventoryAccess.containsKey(p)) {
                ForceFieldGenerator fsg = (ForceFieldGenerator) ForceField.inventoryAccess.get(p);
                for (int i = 30; i <= 32; i++) {
                    ItemStack is = inv.getItem(i);
                    if (is != null) {
                        if (!(ItemLib.isEqual(is, TardisItem.ARTRON_BOTTLE.getItemStack()))) {
                            inv.setItem(i, new ItemStack(Material.AIR));
                            Location loc = fsg.loc;
                            loc.getWorld().dropItem(loc, is);
                        }
                    }
                }
                ForceField.inventoryAccess.remove(p);
            }
        }
    }

}
