package fr.pandalunatique.tardisplugin;

import fr.pandalunatique.tardisplugin.event.*;
import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.visual.ArtronCloudVisual;
import fr.pandalunatique.tardisplugin.item.TardisCraft;
import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.visual.VisualManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TardisPlugin extends JavaPlugin {

    private static TardisPlugin instance;

    public static TardisPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

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
        if(!getDataFolder().exists()) getDataFolder().mkdir();

        // Connect to database and check if connection if valid, otherwise disable the plugin
        if(!Database.getInstance().canConnect()) Bukkit.getPluginManager().disablePlugin(this);

        // Registering main events
        Bukkit.getPluginManager().registerEvents(new ConnectionHandleListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemCraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkLoadUnloadListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);

        // Registering custom items and recipes
        for(TardisCraft craft : TardisCraft.values()) {

            craft.registerRecipe();

        }

        // Starting visuals
        VisualManager.startAll();


        // TEMPORARY EVENT REGISTERING
        Bukkit.getPluginManager().registerEvents(new TemporaryEvents(), this);

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getInventory().addItem(TardisItem.FORCE_FIELD.getItemStack());
            player.getInventory().addItem(TardisItem.ARTRON_BOTTLE.getItemStack());


        });

    }


    @Override
    public void onDisable() {



    }

}
