package fr.pandalunatique.tardis;

import fr.pandalunatique.tardis.artron.ArtronCloudLoop;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import fr.pandalunatique.tardis.artron.ArtronCloud;

public class TardisPlugin extends JavaPlugin {

    private static TardisPlugin instance;


    @Override
    public void onEnable() {

//        WorldCreator wc = new WorldCreator("Tardis");
//        wc.generator(new TardisWorldGenerator());
//        wc.createWorld();

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
        instance = this;

        WorldCreator wc = new WorldCreator("Tardis");
        wc.generator(new fr.pandalunatique.tardis.TardisWorldGenerator());
        wc.createWorld();

        //Arton system
        getServer().getPluginManager().registerEvents(new ArtronCloud(), this);
        ArtronCloudLoop acl = new ArtronCloudLoop();
        acl.start();

    }


    @Override
    public void onDisable() {



    }

    public static TardisPlugin getInstance() {
        return instance;
    }
}
