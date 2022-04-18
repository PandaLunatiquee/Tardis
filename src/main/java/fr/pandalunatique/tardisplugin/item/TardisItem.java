package fr.pandalunatique.tardisplugin.item;

import fr.pandalunatique.tardisplugin.TardisPlugin;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.util.ChanceLib;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import javax.naming.Name;
import javax.xml.transform.SourceLocator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public enum TardisItem {

    ARTRON_BOTTLE(Material.HONEY_BOTTLE, "&rArtron Bottle", null, true),
    BASIC_KEY(Material.GOLDEN_HOE, "&rBasic Key", null, true),
    TARDIS_KEY(Material.GOLDEN_HOE, "&eTardis Key", "\n&fDon't give this key!"),
    FORCE_FIELD(Material.ANVIL, "&rForce Field", null, true),
    SONIC_SCREWDRIVER(Material.BARRIER, "&rSonic Screwdriver", null, false),
    TARDIS_JAMMER(Material.BARRIER, "&rTardis Jammer", null, true); //TODO

    //TODO: Custom model data

    private final Material m; // Material
    private final String n; // Name
    private final String l; // Lore
    private final boolean s; // Stackable
    private final int c; // Custom model data

    TardisItem(Material material, String name) {
        this(material, name, null, false);
    }

    TardisItem(Material material, String name, String lore) {
        this(material, name, lore, false, -1);
    }

    TardisItem(Material material, String name, String lore, boolean stackable) {
        this(material, name, lore, stackable, -1);
    }

    TardisItem(Material material, String name, String lore, boolean stackable, int customModelData) {
        this.m = material;
        this.n = name;
        this.l = lore;
        this.s = stackable;
        this.c = customModelData;
    }

    public boolean isSimilarTo(ItemStack itemStack) {

        ItemStack a = this.getItemStack();

        if(a.getItemMeta() != null && itemStack.getItemMeta() != null) {

            boolean materialEquals = a.getType().equals(itemStack.getType());
            boolean nameEquals = a.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName());

            return materialEquals && nameEquals;

        }

        return false;

    }

    @Nullable
    public static UUID getSoulbinding(ItemStack itemStack) {

        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null) {

            String uuid = meta.getPersistentDataContainer().get(new NamespacedKey(TardisPlugin.getInstance(), "soul"), PersistentDataType.STRING);

            if(uuid != null) return UUID.fromString(uuid);

        }

        return null;

    }

    public static void setSoulbinding(ItemStack itemStack, UUID uuid) {

        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null) {
            meta.getPersistentDataContainer().set(new NamespacedKey(TardisPlugin.getInstance(), "soul"), PersistentDataType.STRING, uuid.toString());
        }
        itemStack.setItemMeta(meta);

    }

    public ItemStack getItemStack() {

        return this.getItemStack(1, null);

    }

    public ItemStack getItemStack(int amount) {

        return this.getItemStack(amount, null);

    }

    public ItemStack getItemStack(UUID soulbind) {

        return this.getItemStack(1, soulbind);

    }

    public ItemStack getItemStack(int amount, UUID soulbind) {

        ItemStack item = new ItemStack(this.m);
        item.setAmount(amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.n));

        if(this.l != null) {


            List<String> lore = Arrays.asList(ChatColor.translateAlternateColorCodes('&', this.l).split("\n"));
            meta.setLore(lore);

        }

        if(this.c != -1) meta.setCustomModelData(this.c);

        if(!this.s) {

            NamespacedKey nk = new NamespacedKey(TardisPlugin.getInstance(), "unstackableid");
            meta.getPersistentDataContainer().set(nk, PersistentDataType.INTEGER, ChanceLib.randomInteger(0, 1000000));

        }

        if(soulbind != null) {

            NamespacedKey nk = new NamespacedKey(TardisPlugin.getInstance(), "soul");
            meta.getPersistentDataContainer().set(nk, PersistentDataType.STRING, soulbind.toString());

        }

        item.setItemMeta(meta);

        return item;

    }




}
