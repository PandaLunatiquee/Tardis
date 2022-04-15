package fr.pandalunatique.tardis.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.pandalunatique.tardis.item.ModelID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import static org.bukkit.craftbukkit.libs.org.apache.commons.lang3.reflect.FieldUtils.getField;

public class ItemBuilder {

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
