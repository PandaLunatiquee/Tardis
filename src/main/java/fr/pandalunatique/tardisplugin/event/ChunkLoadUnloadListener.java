package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.world.artron.ArtronCloud;
import fr.pandalunatique.tardisplugin.util.ChanceLib;
import fr.pandalunatique.tardisplugin.util.LocationLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkLoadUnloadListener implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {

        //TODO: Manager different worlds
        if(e.getChunk().getWorld().getName().equalsIgnoreCase("Tardis")) return;

        Chunk chunk = e.getChunk();

        if(ChanceLib.chanceOutOf(ArtronCloud.SPAWN_CHANCE, 100)) {

            Location randomBlock = chunk.getBlock(ChanceLib.randomInteger(0, 16), 255, ChanceLib.randomInteger(0, 16)).getLocation();
            Location location = LocationLib.reachSurfaceFromTop(randomBlock);
            if(location != null) {

                ArtronCloud cloud = new ArtronCloud(location);
                ArtronCloud.addArtronCloud(cloud);

            }

        }

    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        ArtronCloud.removeInChunk(e.getChunk());
    }

}
