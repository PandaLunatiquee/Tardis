package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.item.TardisItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class ItemCraftListener implements Listener {

    @EventHandler
    public void onPrepareItemCraftEvent(PrepareItemCraftEvent e) {
        if(e.getRecipe() == null) return;

        Recipe r = e.getRecipe();

        if(r.getResult().getType() == Material.SUGAR || r.getResult().getType() == Material.HONEY_BLOCK) {
            if(e.getInventory().containsAtLeast(TardisItem.ARTRON_BOTTLE.getItemStack(), 1)) {
                e.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }

    }

}
