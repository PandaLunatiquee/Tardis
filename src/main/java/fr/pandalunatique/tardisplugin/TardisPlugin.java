package fr.pandalunatique.tardisplugin;

import fr.pandalunatique.tardisplugin.entity.EntityEnum;
import fr.pandalunatique.tardisplugin.event.*;
import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.player.TardisPlayerRegistry;
import fr.pandalunatique.tardisplugin.player.tool.bestiary.Bestiary;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.item.TardisCraft;
import fr.pandalunatique.tardisplugin.schematic.SchematicManager;
import fr.pandalunatique.tardisplugin.tardis.Banlist;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisFacing;
import fr.pandalunatique.tardisplugin.tardis.TardisRegistry;
import fr.pandalunatique.tardisplugin.util.BooleanStorableSet;
import fr.pandalunatique.tardisplugin.visual.VisualManager;
import fr.pandalunatique.tardisplugin.world.TardisWorldGenerator;
import fr.pandalunatique.tardisplugin.world.TardisWorldManager;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

public class TardisPlugin extends JavaPlugin {

    private static TardisPlugin instance;
    private static String nmsVersion;

    public static TardisPlugin getInstance() {
        return instance;
    }

    public static String getNmsVersion() {
        return nmsVersion;
    }

    @Override
    public void onEnable() {

        instance = this;
        nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        System.out.println();
        System.out.println(" #######");
        System.out.println("    #      ##   #####  #####  #  ####");
        System.out.println("    #     #  #  #    # #    # # #");
        System.out.println("    #    #    # #    # #    # #  ####");
        System.out.println("    #    ###### #####  #    # #      #");
        System.out.println("    #    #    # #   #  #    # # #    #");
        System.out.println("    #    #    # #    # #####  #  ####");
        System.out.println();

        // Create plugin folder
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir(); //TODO: Handle exception

            File schematicFolder = new File(this.getDataFolder(), "schematic");
            if(!schematicFolder.exists()) schematicFolder.mkdir();

        }

        SchematicManager.saveSchematics();

        // Connect to database and check if connection if valid, otherwise disable the plugin
        if(!Database.canConnect()) Bukkit.getPluginManager().disablePlugin(this);

        // Registering main events
        Bukkit.getPluginManager().registerEvents(new ConnectionHandleListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemCraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkLoadUnloadListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new ResourcePackListener(), this);

        this.getCommand("tgive").setExecutor(new TemporaryGiveCommand());

        // Registering custom items and recipes
        TardisCraft.registerRecipes();

        // Starting visuals
        VisualManager.startAll();

        TardisWorldManager.loadWorlds();

        // TEMPORARY EVENT REGISTERING
        Bukkit.getPluginManager().registerEvents(new TemporaryEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ForceField(), this);

        Bukkit.getOnlinePlayers().forEach(player -> {

            TardisPlayer tardisPlayer = Database.getPlayer(player.getUniqueId());

            ItemStack i = TardisItem.TARDIS_KEY.getItemStack();
            TardisItem.setSoulbinding(i, player.getUniqueId());
            player.getInventory().addItem(i);
            if(tardisPlayer != null) {

                Tardis tardis = Database.getTardis(player.getUniqueId());

                if(tardis != null) TardisRegistry.getRegistry().registerTardis(tardis);
                TardisPlayerRegistry.getRegistry().registerPlayer(tardisPlayer);

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lProgression! &aYour progression has been loaded successfully!"));
                player.sendMessage(String.valueOf(tardisPlayer.getBestiary().getAggressive()));

            } else {
                player.kickPlayer(ChatColor.RED + "We couldn't retrieve your data! Please try to reconnect!");
            }

        });



        Bukkit.getScheduler().runTaskTimer(this, () -> {

            for (Tardis tardis : TardisRegistry.getRegistry().getRegistered()) {

                Bukkit.getOnlinePlayers().forEach(player -> {

                    Location ploc = player.getLocation();

                    if(ploc.distance(tardis.getTardisLocation()) < 10) {

                        BlockFace face = BlockFace.valueOf(tardis.getFacing().name());
                        Location relative = tardis.getTardisLocation().getBlock().getRelative(face).getLocation();
                        Location relative2 = tardis.getTardisLocation().getBlock().getRelative(face, 2).getLocation();

                       // player.sendMessage("" + (int) ploc.getX() + " " + tardis.getTardisLocation().getX());

                        boolean x = (int) ploc.getX() == (int) relative.getX() || (int) ploc.getX() == (int) tardis.getTardisLocation().getX();
                        boolean y = (int) ploc.getY() == (int) tardis.getTardisLocation().getY();
                        boolean z = (int) ploc.getZ() == (int) relative.getZ() || (int) ploc.getZ() == (int) tardis.getTardisLocation().getZ();

                        x = x || (int) ploc.getX() == (int) relative2.getX();
                        z = z || (int) ploc.getZ() == (int) relative2.getZ();

                        if(x && y && z) {

                            player.sendBlockChange(tardis.getTardisLocation(), Material.AIR.createBlockData());
                            player.sendBlockChange(tardis.getTardisLocation().getBlock().getRelative(BlockFace.UP).getLocation(), Material.AIR.createBlockData());

                        } else {
                            player.sendBlockChange(tardis.getTardisLocation(), Material.REDSTONE_BLOCK.createBlockData());
                            player.sendBlockChange(tardis.getTardisLocation().getBlock().getRelative(BlockFace.UP).getLocation(), Material.REDSTONE_BLOCK.createBlockData());
                        }

                    }

                });

            }

        } , 0, 5);

    }


    @Override
    public void onDisable() {

        TardisPlayerRegistry.getRegistry().saveAll();
        TardisRegistry.getRegistry().saveAll();

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lProgression! &aYour progression has been saved!"));

    }

}

// TEST

//        Player p = Bukkit.getPlayer("PandaLunatique");
//        Location l = p.getLocation();
//        EntityPlayer aaa = ((CraftPlayer) p).getHandle();
//
//        GameProfile g = new GameProfile(UUID.randomUUID(), "TestPlayer");
//        MinecraftServer s = ((CraftServer) Bukkit.getServer()).getServer();
//        WorldServer w = ((CraftWorld) p.getLocation().getWorld()).getHandle();
//        EntityPlayer ep = new EntityPlayer(s, w, g, new PlayerInteractManager(w));
//        ep.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
//
//        PacketPlayOutPlayerInfo p1 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
//        PacketPlayOutNamedEntitySpawn p2 = new PacketPlayOutNamedEntitySpawn(ep);
//
//        aaa.playerConnection.sendPacket(p1);
//        aaa.playerConnection.sendPacket(p2);
//
//        Bukkit.getScheduler().runTaskTimer(instance, () -> {
//
//            l.add(new Vector(1, 0, 0));
//
//            PacketPlayOutPosition paa = new PacketPlayOutPosition(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(), new HashSet<>(), 0);
//            aaa.playerConnection.sendPacket(paa);
//
//        }, 0, 1);