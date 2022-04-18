package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.Database;
import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.player.TardisPlayerRegistry;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisRegistry;
import fr.pandalunatique.tardisplugin.world.artron.ArtronCloud;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if(e.getHand().equals(EquipmentSlot.HAND)) {

            ItemStack item = p.getInventory().getItemInMainHand();

            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                if (item.getType().equals(Material.GLASS_BOTTLE)) {

                    ArtronCloud artronCloud = ArtronCloud.getPlayerLookedArtronCloud(p);

                    if (artronCloud != null) {

                        if (item.getAmount() == 1) {

                            p.getInventory().setItemInMainHand(TardisItem.ARTRON_BOTTLE.getItemStack());

                        } else {

                            item.setAmount(item.getAmount() - 1);
                            p.getInventory().setItemInMainHand(item);
                            p.getInventory().addItem(TardisItem.ARTRON_BOTTLE.getItemStack());

                        }

                        artronCloud.collectArtron();
                    }

                } else if(TardisItem.TARDIS_KEY.isSimilarTo(item)) {

                    if(TardisItem.getSoulbinding(item) != null) {

                        // call tardis

                    } else {

                        if(!Database.hasTardis(p.getUniqueId())) {

                            Tardis tardis = new Tardis(p);
                            TardisPlayerRegistry.getRegistry().getPlayer(p.getUniqueId()).setTardis(tardis);
                            TardisRegistry.getRegistry().registerTardis(tardis);

                            Database.addTardis(tardis);

                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lCongrats! &eYou now have a Tardis!"));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Right click on a block with your key to summon your tardis!"));

                            TardisItem.setSoulbinding(item, p.getUniqueId());

                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError! &cYou already have a Tardis!"));
                        }
                    }
                }
            }
        }
    }
}
