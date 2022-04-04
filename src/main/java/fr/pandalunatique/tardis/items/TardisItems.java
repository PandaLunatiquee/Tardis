package fr.pandalunatique.tardis.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TardisItems {

    enum Items {

        ARTRON_BOTTLE,
        SONIC_SCREWDRIVER

    }

    public static ItemStack getArtronBottle() {

        ItemStack item = new ItemStack(Material.HONEY_BOTTLE, 1);

        return item;

    }

}
