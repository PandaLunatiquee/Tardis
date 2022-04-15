package fr.pandalunatique.tardisplugin.visual;

import fr.pandalunatique.tardisplugin.world.artron.ArtronCloud;

import org.bukkit.Particle;

public class ArtronCloudVisual {

    public static int VISUAL_DELAY = 0;
    public static int VISUAL_REFRESH_RATE = ArtronCloud.VISUAL_REFRESH_RATE;

    public static Runnable getVisualRunnable() {
        return () -> {
            ArtronCloud.getArtronClouds().forEach((cloud) -> {
                cloud.getChunk().getWorld().spawnParticle(Particle.SMOKE_NORMAL, cloud.getLocation(), 50, 0.5, 0.3, 0.5, 0.01, null, true);
                cloud.getChunk().getWorld().spawnParticle(Particle.CLOUD, cloud.getLocation(), 30, 0.5, 0.3, 0.5, 0.01, null, true);
                cloud.getChunk().getWorld().spawnParticle(Particle.FLAME, cloud.getLocation(), 10, 0.5, 0.3, 0.5, 0.01, null, true);
            });
        };

    }

}
