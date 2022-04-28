package fr.pandalunatique.tardisplugin.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.pandalunatique.tardisplugin.item.ModelID;
import jdk.internal.icu.text.NormalizerBase;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.maven.model.Model;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class ItemBuilder {

    // CustomModels Methods
    static public ItemStack getModel(@NotNull final ModelID model) { return getModel(model, false); }
    static public ItemStack getModel(@NotNull final ModelID model, final boolean glint) {
        final ItemStack is = new ItemStack(model.getMat());
        final ItemMeta meta = is.getItemMeta();
        if (glint) is.addEnchantment(Enchantment.LURE, 0);
        meta.setCustomModelData(model.getCmd());

        is.setItemMeta(meta);
        return is;
    }




    // CustomSkulls Methods
    static public ItemStack getCustomSkull(final String texture) { return getCustomSkull(texture, 1, false, " ", null); }

    static public ItemStack getCustomSkull(final String texture, final int count) { return getCustomSkull(texture, count, false, " ", null); }

    static public ItemStack getCustomSkull(final String texture, final boolean isSmall) { return getCustomSkull(texture, 1, isSmall, " ", null); }

    static public ItemStack getCustomSkull(final String texture, final String displayName) { return getCustomSkull(texture, 1, false, displayName, null); }

    static public ItemStack getCustomSkull(final String texture, final int count, final boolean isSmall) { return getCustomSkull(texture, count, isSmall, " ", null); }

    static public ItemStack getCustomSkull(final String texture, final int count, final String displayName) { return getCustomSkull(texture, count, false, displayName, null); }

    static public ItemStack getCustomSkull(final String texture, final int count, final boolean isSmall, @NotNull final String displayName, final String[] displayLore) {

        ItemStack finalSkull = new ItemStack(Material.PLAYER_HEAD, count);
        if (texture.isEmpty()) return finalSkull;

        SkullMeta skullMeta = (SkullMeta) finalSkull.getItemMeta();
        final GameProfile profile = new GameProfile(new UUID(texture.hashCode(), texture.hashCode()), null);

        final String encodedData = Base64.getEncoder().encodeToString(String.format("{textures:{SKIN:{url:\"https://textures.minecraft.net/texture/%s\"}}}", texture).getBytes());
        profile.getProperties().put("textures", new Property("textures", encodedData));

        try {
            final Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        skullMeta.setCustomModelData(isSmall ? ModelID.SCAN_ICON.getCmd() : 0);
        skullMeta.setDisplayName(displayName);
        try { skullMeta.setLore(Arrays.asList(displayLore)); } catch (NullPointerException ignored) {}
        finalSkull.setItemMeta(skullMeta);

        return finalSkull;

    }


}