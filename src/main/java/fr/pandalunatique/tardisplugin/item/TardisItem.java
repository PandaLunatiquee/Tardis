package fr.pandalunatique.tardisplugin.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum TardisItem {

    ARTRON_BOTTLE(Material.HONEY_BOTTLE, "&rArtron Bottle"),
    BASIC_KEY(Material.GOLDEN_HOE, "&rBasic Key"),
    TARDIS_KEY(Material.GOLDEN_HOE, "&rTardis Key"),
    FORCE_FIELD(Material.ANVIL, "&rForce Field"),
    SONIC_SCREWDRIVER(Material.BARRIER, "&rSonic Screwdriver"),
    TARDIS_JAMMER(Material.BARRIER, "&rTardis Jammer"); //TODO

    //TODO: Custom model data

    private final Material m;
    private final String n;

    private TardisItem(Material m, String n) {
        this.m = m;
        this.n = n;
    }

    public ItemStack getItemStack() {

        ItemStack item = new ItemStack(this.m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.n));
        item.setItemMeta(meta);

        return item;

    }

}
