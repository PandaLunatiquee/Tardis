package fr.pandalunatique.tardis;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import java.util.Random;

public class TardisWorldGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return createChunkData(world);
    }

}
