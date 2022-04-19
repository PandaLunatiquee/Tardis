package fr.pandalunatique.tardisplugin;

import fr.pandalunatique.tardisplugin.event.*;
import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.item.TardisCraft;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisAppearance;
import fr.pandalunatique.tardisplugin.util.BooleanSet;
import fr.pandalunatique.tardisplugin.visual.VisualManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

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

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            onlinePlayer.getInventory().addItem(TardisItem.TARDIS_KEY.getItemStack());

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


    }


    @Override
    public void onDisable() {



    }

}
