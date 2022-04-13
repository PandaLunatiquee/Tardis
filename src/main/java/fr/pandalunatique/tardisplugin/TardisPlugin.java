package fr.pandalunatique.tardisplugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.pandalunatique.tardisplugin.artron.ArtronCloudLoop;
import fr.pandalunatique.tardisplugin.event.ConnectionHandleListener;
import fr.pandalunatique.tardisplugin.levelling.TardisLevelling;
import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.world.TardisWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import fr.pandalunatique.tardisplugin.artron.ArtronCloud;

import java.util.Map;

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

    }


    @Override
    public void onDisable() {



    }

}
