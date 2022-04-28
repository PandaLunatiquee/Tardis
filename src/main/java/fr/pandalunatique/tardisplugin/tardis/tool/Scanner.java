package fr.pandalunatique.tardisplugin.tardis.tool;

import com.mojang.datafixers.util.Pair;
import fr.pandalunatique.tardisplugin.TardisPlugin;
import fr.pandalunatique.tardisplugin.entity.TardisEntityType;
import fr.pandalunatique.tardisplugin.item.EntityIcon;
import fr.pandalunatique.tardisplugin.util.ItemBuilder;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;


public class Scanner implements Listener {

    final private Set<Material> itemList = new HashSet<>(
            Arrays.asList(
                    Material.OAK_SAPLING, Material.SPRUCE_SAPLING, Material.BIRCH_SAPLING, Material.JUNGLE_SAPLING, Material.ACACIA_SAPLING, Material.DARK_OAK_SAPLING,
                    Material.COBWEB, Material.GRASS, Material.FERN, Material.DEAD_BUSH, Material.SEAGRASS, Material.SEA_PICKLE,
                    Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY, Material.WITHER_ROSE,
                    Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS, Material.CRIMSON_ROOTS, Material.WARPED_ROOTS, Material.NETHER_SPROUTS, Material.WEEPING_VINES, Material.TWISTING_VINES,
                    Material.SUGAR_CANE, Material.KELP, Material.BAMBOO,
                    Material.TORCH, Material.LEVER, Material.REDSTONE_TORCH, Material.SOUL_TORCH,
                    Material.IRON_BARS, Material.CHAIN, Material.GLASS_PANE, Material.HOPPER,
                    Material.SUNFLOWER, Material.LILAC, Material.ROSE_BUSH, Material.PEONY, Material.TALL_GRASS, Material.LARGE_FERN,
                    Material.WHITE_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE, Material.PURPLE_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE,
                    Material.TURTLE_EGG, Material.TUBE_CORAL, Material.BRAIN_CORAL, Material.BUBBLE_CORAL, Material.FIRE_CORAL, Material.HORN_CORAL, Material.DEAD_BRAIN_CORAL, Material.DEAD_BUBBLE_CORAL, Material.DEAD_FIRE_CORAL, Material.DEAD_HORN_CORAL, Material.DEAD_TUBE_CORAL, Material.TUBE_CORAL_FAN, Material.BRAIN_CORAL_FAN, Material.BUBBLE_CORAL_FAN, Material.FIRE_CORAL_FAN, Material.HORN_CORAL_FAN, Material.DEAD_TUBE_CORAL_FAN, Material.DEAD_BRAIN_CORAL_FAN, Material.DEAD_BUBBLE_CORAL_FAN, Material.DEAD_FIRE_CORAL_FAN, Material.DEAD_HORN_CORAL_FAN,
                    Material.OAK_SIGN, Material.SPRUCE_SIGN, Material.BIRCH_SIGN, Material.JUNGLE_SIGN, Material.ACACIA_SIGN, Material.DARK_OAK_SIGN, Material.CRIMSON_SIGN, Material.WARPED_SIGN,
                    Material.BREWING_STAND, Material.CAULDRON, Material.FLOWER_POT,
                    Material.WHITE_BANNER, Material.ORANGE_BANNER, Material.MAGENTA_BANNER, Material.LIGHT_BLUE_BANNER, Material.YELLOW_BANNER, Material.LIME_BANNER, Material.PINK_BANNER, Material.GRAY_BANNER, Material.LIGHT_GRAY_BANNER, Material.CYAN_BANNER, Material.PURPLE_BANNER, Material.BLUE_BANNER, Material.BROWN_BANNER, Material.GREEN_BANNER, Material.RED_BANNER, Material.BLACK_BANNER,
                    Material.BELL, Material.LANTERN, Material.SOUL_LANTERN, Material.CAMPFIRE, Material.SOUL_CAMPFIRE
            )
    );

    final private Set<Material> blackList = new HashSet<>(
            Arrays.asList(
                    Material.WALL_TORCH, Material.END_ROD, Material.LADDER, Material.REDSTONE_WALL_TORCH, Material.SOUL_WALL_TORCH, Material.VINE,
                    Material.TRIPWIRE_HOOK, Material.STONE_BUTTON, Material.OAK_BUTTON, Material.SPRUCE_BUTTON, Material.BIRCH_BUTTON, Material.JUNGLE_BUTTON, Material.ACACIA_BUTTON, Material.DARK_OAK_BUTTON, Material.CRIMSON_BUTTON, Material.WARPED_BUTTON, Material.POLISHED_BLACKSTONE_BUTTON,
                    Material.DEAD_TUBE_CORAL_WALL_FAN, Material.DEAD_BRAIN_CORAL_WALL_FAN, Material.DEAD_BUBBLE_CORAL_WALL_FAN, Material.DEAD_FIRE_CORAL_WALL_FAN, Material.DEAD_HORN_CORAL_WALL_FAN,
                    Material.IRON_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.CRIMSON_DOOR, Material.WARPED_DOOR,
                    Material.REDSTONE, Material.REPEATER, Material.COMPARATOR, Material.TRIPWIRE,
                    Material.OAK_WALL_SIGN, Material.SPRUCE_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.ACACIA_WALL_SIGN, Material.DARK_OAK_WALL_SIGN, Material.CRIMSON_WALL_SIGN, Material.WARPED_WALL_SIGN,
                    Material.WHITE_BED, Material.ORANGE_BED, Material.MAGENTA_BED, Material.LIGHT_BLUE_BED, Material.YELLOW_BED, Material.LIME_BED, Material.PINK_BED, Material.GRAY_BED, Material.LIGHT_GRAY_BED, Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED, Material.BROWN_BED, Material.GREEN_BED, Material.RED_BED, Material.BLACK_BED,
                    Material.SKELETON_SKULL, Material.WITHER_SKELETON_SKULL, Material.PLAYER_HEAD, Material.ZOMBIE_HEAD, Material.CREEPER_HEAD, Material.DRAGON_HEAD,
                    Material.SKELETON_WALL_SKULL, Material.WITHER_SKELETON_WALL_SKULL, Material.PLAYER_WALL_HEAD, Material.ZOMBIE_WALL_HEAD, Material.CREEPER_WALL_HEAD, Material.DRAGON_WALL_HEAD,
                    Material.WHITE_WALL_BANNER, Material.ORANGE_WALL_BANNER, Material.MAGENTA_WALL_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.YELLOW_WALL_BANNER, Material.LIME_WALL_BANNER, Material.PINK_WALL_BANNER, Material.GRAY_WALL_BANNER, Material.LIGHT_GRAY_WALL_BANNER, Material.CYAN_WALL_BANNER, Material.PURPLE_WALL_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_WALL_BANNER, Material.GREEN_WALL_BANNER, Material.RED_WALL_BANNER, Material.BLACK_WALL_BANNER,
                    Material.BARRIER, Material.STRUCTURE_VOID, Material.MOVING_PISTON
            )
    );

    final private Map<TardisEntityType, String> tardisEntityIconList = new HashMap<TardisEntityType, String>() {
        {
            put(TardisEntityType.DALEK, "45be4c87347ad1218efd4f0c08c3216a787053735335686559637207e240d5e3");
        }
    };

    public void scanZone(final Location scanCenter, final Location hologramCenter, final int size) {
        final Location scanInit = new Location(scanCenter.getWorld(), scanCenter.getBlockX() - size, scanCenter.getBlockY() - size / 2, scanCenter.getBlockZ() - size);
        final Location holoInit = new Location(hologramCenter.getWorld(), hologramCenter.getX() - 0.136 * size - 0.05, hologramCenter.getY() - 0.33, hologramCenter.getZ() - 0.136 * size - 0.505);

        scanCenter.getWorld().getNearbyEntities(scanCenter, size, size / 2, size).forEach((e) -> {
            if (!e.getType().equals(EntityType.DROPPED_ITEM)) {
                Vector pos = new Vector(e.getLocation().getBlockX() - scanInit.getBlockX(), e.getLocation().getBlockY() - scanInit.getBlockY(), e.getLocation().getBlockZ() - scanInit.getBlockZ());
                pos.multiply(0.186);
                spawnHologramEntity(holoInit.toVector().add(pos).toLocation(holoInit.getWorld()), e);
            }
        });

        Location scanPos = scanInit.clone();
        Location holoPos = holoInit.clone();

        for (int i = 0; i < size + 1; i++) {
            final int finalI = i;
            final Location finalScan = scanPos.clone();
            final Location finalHolo = holoPos.clone();

            Bukkit.getScheduler().runTaskLater(TardisPlugin.getInstance(), () -> {

                for (int j = 0; j < size * 2 + 1; j++) {
                    for (int k = 0; k < size * 2 + 1; k++) {
                        if (isBlockVisible(finalScan) || k == 0 || k == size * 2 || j == 0 || j == size * 2 || finalI == size) {
                            spawnHologramBlock(finalHolo, finalScan);
                        }
                        finalScan.setX(finalScan.getX() + 1);
                        finalHolo.setX(finalHolo.getX() + 0.186);
                    }
                    finalScan.setZ(finalScan.getZ() + 1);
                    finalHolo.setZ(finalHolo.getZ() + 0.186);
                    finalScan.setX(scanInit.getX());
                    finalHolo.setX(holoInit.getX());
                }
            }, i * 5L);
            scanPos.setY(scanPos.getY() + 1);
            holoPos.setY(holoPos.getY() + 0.186);
            scanPos.setZ(scanInit.getZ());
            holoPos.setZ(holoInit.getZ());
        }
    }


    private boolean isBlockVisible(final Location pos) {
        final Block b = pos.getBlock();
        final Material bu = b.getRelative(BlockFace.UP).getType();
        final Material bd = b.getRelative(BlockFace.DOWN).getType();
        final Material bn = b.getRelative(BlockFace.NORTH).getType();
        final Material bs = b.getRelative(BlockFace.SOUTH).getType();
        final Material bw = b.getRelative(BlockFace.WEST).getType();
        final Material be = b.getRelative(BlockFace.EAST).getType();

        return (!b.getType().equals(Material.AIR) &&
                (bu.equals(Material.AIR) || itemList.contains(bu) || blackList.contains(bu)) ||
                (bd.equals(Material.AIR) || itemList.contains(bd) || blackList.contains(bd)) ||
                (bn.equals(Material.AIR) || itemList.contains(bn) || blackList.contains(bn)) ||
                (bs.equals(Material.AIR) || itemList.contains(bs) || blackList.contains(bs)) ||
                (bw.equals(Material.AIR) || itemList.contains(bw) || blackList.contains(bw)) ||
                (be.equals(Material.AIR) || itemList.contains(be) || blackList.contains(be)));
    }

    private void spawnHologramBlock(final Location pos, final Location block) {
        if (!block.getBlock().getType().equals(Material.AIR) && !blackList.contains(block.getBlock().getType())) {
            EntityArmorStand hologram = new EntityArmorStand(((CraftWorld) pos.getWorld()).getHandle(), 0, 0, 0);
            if (itemList.contains(block.getBlock().getType())) {
                hologram.setPositionRotation(pos.getX() - 0.14d, pos.getY() - 0.39d, pos.getZ() - 0.15d, 0, 0);
                hologram.setRightArmPose(new Vector3f(270f, 0f, 0f));
            } else {
                hologram.setPositionRotation(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
                hologram.setRightArmPose(new Vector3f(345f, 45f, 0f));
            }

            hologram.setInvisible(true);
            hologram.setNoGravity(true);
            hologram.setSmall(true);
            hologram.setArms(true);
            hologram.setInvulnerable(true);
            hologram.setMarker(true);
            hologram.setSilent(true);
            hologram.disabledSlots = 4144959;
            hologram.setHeadPose(new Vector3f(0f, 0f, 0f));
            hologram.setBodyPose(new Vector3f(0f, 0f, 0f));


            final ItemStack is = new ItemStack(block.getBlock().getType());
            final net.minecraft.server.v1_16_R3.ItemStack nmsIs = CraftItemStack.asNMSCopy(is);

            hologram.a(EnumHand.MAIN_HAND, nmsIs);

            final Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack> pair = new Pair<>(EnumItemSlot.MAINHAND, nmsIs);
            final ArrayList<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> list = new ArrayList<>();
            list.add(pair);

            final PacketPlayOutSpawnEntity spawnItem = new PacketPlayOutSpawnEntity(hologram);
            final PacketPlayOutEntityEquipment armor = new PacketPlayOutEntityEquipment(hologram.getId(), list);
            final PacketPlayOutEntityMetadata data = new PacketPlayOutEntityMetadata(hologram.getId(), hologram.getDataWatcher(), true);
            final PacketPlayOutEntityVelocity motion = new PacketPlayOutEntityVelocity(hologram.getId(), new Vec3D(0, 0, 0));

            for (Player p : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnItem);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(armor);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(data);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(motion);
            }

        }
    }

    private void spawnHologramEntity(final Location pos, final Entity entity) {
        final Location itemPos = new Location(pos.getWorld(), pos.getX() - 0.338, pos.getY() + 0.246, pos.getZ() + 0.139);

        EntityItem item = new EntityItem(((CraftWorld) pos.getWorld()).getHandle(), 0, 0, 0,
                CraftItemStack.asNMSCopy(ItemBuilder.getCustomSkull(EntityIcon.fromEntityType(entity.getType()), true)));
        item.setPosition(itemPos.getX(), itemPos.getY(), itemPos.getZ());
        item.setNoGravity(true);
        item.setPickupDelay(32767);
        item.setInvulnerable(true);
        item.age = -32768;

        final PacketPlayOutSpawnEntity spawnItem = new PacketPlayOutSpawnEntity(item);
        final PacketPlayOutEntityMetadata data = new PacketPlayOutEntityMetadata(item.getId(), item.getDataWatcher(), true);
        final PacketPlayOutEntityVelocity motion = new PacketPlayOutEntityVelocity(item.getId(), new Vec3D(0, 0, 0));

        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnItem);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(data);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(motion);
        }

//        if(!entity.getType().equals(EntityType.ARMOR_STAND)) {
//            item = (EntityItem) itemPos.getWorld().dropItem(itemPos, ItemBuilder.getCustomSkull(vanillaEntityIconList.get(entity.getType())));
//        } else {
//            //TODO : add custom entity when available
//            item = (EntityItem) itemPos.getWorld().dropItem(itemPos, new ItemStack(Material.BARRIER));
//        }

    }

}
