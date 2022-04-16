package fr.pandalunatique.tardisplugin.util;

import org.bukkit.inventory.ItemStack;

public class ItemLib {

    public static boolean isEqual(ItemStack is1, ItemStack is2) {
        ItemStack i1 = is1.clone();
        i1.setAmount(1);
        ItemStack i2 = is2.clone();
        i2.setAmount(1);
        return i1.equals(i2);
    }

}
