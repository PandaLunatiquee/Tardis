package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceFieldGenerator;
import fr.pandalunatique.tardisplugin.util.ItemLib;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof ArmorStand) {
            if (e.getDamager() instanceof Player) {
                Player p = (Player) e.getDamager();
                ArmorStand as = (ArmorStand) entity;
                if (as.getEquipment().getHelmet() == null) return;
                if (ItemLib.isEqual(as.getEquipment().getHelmet(), TardisItem.FORCE_FIELD.getItemStack())) {
                    e.setCancelled(true);
                    if (p.isSneaking()) {
                        ForceFieldGenerator fsg = (ForceFieldGenerator) ForceField.shieldsList.get(as.getLocation());
                        if (!fsg.stateShield) {
                            as.remove();
                            p.getInventory().addItem(TardisItem.FORCE_FIELD.getItemStack());
                            ForceField.shieldsList.remove(as.getLocation());
                        } else {
                            p.sendMessage("Veuillez désactiver le champ de force !");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {

    }

}
