package fr.pandalunatique.tardisplugin;

import fr.pandalunatique.tardisplugin.entity.EntityEnum;
import fr.pandalunatique.tardisplugin.event.*;
import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.player.TardisPlayerRegistry;
import fr.pandalunatique.tardisplugin.player.tool.bestiary.Bestiary;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.item.TardisCraft;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisRegistry;
import fr.pandalunatique.tardisplugin.util.BooleanStorableSet;
import fr.pandalunatique.tardisplugin.visual.VisualManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
        if(!getDataFolder().exists()) getDataFolder().mkdir(); //TODO: Handle exception

        // Connect to database and check if connection if valid, otherwise disable the plugin
        if(!Database.getInstance().canConnect()) Bukkit.getPluginManager().disablePlugin(this);

        // Registering main events
        Bukkit.getPluginManager().registerEvents(new ConnectionHandleListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemCraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkLoadUnloadListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new ResourcePackListener(), this);

        this.getCommand("tgive").setExecutor(new TemporaryGiveCommand());

        // Registering custom items and recipes
        for(TardisCraft craft : TardisCraft.values()) {

            craft.registerRecipe();

        }

        // Starting visuals
        VisualManager.startAll();


        // TEMPORARY EVENT REGISTERING
        Bukkit.getPluginManager().registerEvents(new TemporaryEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ForceField(), this);


        Bukkit.getOnlinePlayers().forEach(player -> {

            TardisPlayer tardisPlayer = Database.getPlayer(player.getUniqueId());

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