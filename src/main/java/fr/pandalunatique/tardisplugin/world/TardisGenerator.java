package fr.pandalunatique.tardisplugin.world;

import fr.pandalunatique.tardisplugin.Database;
import fr.pandalunatique.tardisplugin.schematic.SchematicManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class TardisGenerator {

    private static TardisGenerator instance;

    @Getter @Setter private Offset offset;

    public static class Offset {

        public static int STEP = 1000;

        public static int LEAST_X = -25000000;
        public static int LEAST_Z = -25000000;
        public static int MOST_X = 25000000;
        public static int MOST_Z = 25000000;

        public static int CONSTANT_Y = 64;

        @Getter @Setter private int x;
        @Getter @Setter private int z;

        public Offset(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public Offset next() {

            this.x += STEP;

            if (this.x > MOST_X) {
                this.x = LEAST_X;
                this.z += STEP;
            }

            return this;

            //TODO: An other world??? well...

        }

    }

    private TardisGenerator() {

        this.offset = Database.getPlotOffset();

    }

    public void forceLoadArea(Location location) {

        Chunk chunk = location.getChunk();

        for (int x = chunk.getX() - 1; x <= chunk.getX() + 1; x++) {
            for (int z = chunk.getZ() - 1; z <= chunk.getZ() + 1; z++) {
                chunk.getWorld().loadChunk(x, z, true);
            }
        }

    }

    public static TardisGenerator getInstance() {
        if (instance == null) {
            instance = new TardisGenerator();
        }
        return instance;
    }

    public Location getCurrentLocation() {
        return new Location(Bukkit.getWorld(TardisWorldManager.World.TARDIS.getName()), this.getOffset().getX(), Offset.CONSTANT_Y, this.getOffset().getZ());
    }

    public Location generateNewPlot() {

        Location plotLocation = this.getCurrentLocation();

        this.forceLoadArea(plotLocation);

        SchematicManager.Schematic.TEST.paste(plotLocation);
        Database.setPlotOffset(this.getOffset().next());

        return plotLocation;

    }



}
