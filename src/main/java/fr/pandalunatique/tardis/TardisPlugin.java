package fr.pandalunatique.tardis;

import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.generator.ChunkGenerator;

public class TardisPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        WorldCreator wc = new WorldCreator("Tardis");
        wc.generator(new TardisWorldGenerator());
        wc.createWorld();

    }


    @Override
    public void onDisable() {



    }

}
