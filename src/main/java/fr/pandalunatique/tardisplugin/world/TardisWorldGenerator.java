package fr.pandalunatique.tardisplugin.world;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TardisWorldGenerator extends ChunkGenerator {

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biomeGrid) {
        return createChunkData(world);
    }

    @Override
    public boolean shouldGenerateMobs() {
        return false;
    }

    @Override
    public boolean canSpawn(@NotNull World world, int x, int z) {
        return false;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return false;
    }
}
