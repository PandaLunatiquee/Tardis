package fr.pandalunatique.tardisplugin.world;

import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

public class TardisWorldManager {

    public enum World {

        TARDIS("Tardis", new TardisWorldGenerator());

        private String name;
        private ChunkGenerator generator;

        World(String name, ChunkGenerator generator) {
            this.name = name;
            this.generator = generator;
        }

        public String getName() {
            return name;
        }

        public ChunkGenerator getGenerator() {
            return generator;
        }
    }

    public static void loadWorld(World world) {

        WorldCreator worldCreator = new WorldCreator(world.getName());
        worldCreator.generator(world.getGenerator());
        worldCreator.generateStructures(false);
        worldCreator.type(WorldType.FLAT);
        worldCreator.createWorld();

    }

    public static void loadWorlds() {

        for(World world : World.values()) {

            TardisWorldManager.loadWorld(world);

        }

    }

}
