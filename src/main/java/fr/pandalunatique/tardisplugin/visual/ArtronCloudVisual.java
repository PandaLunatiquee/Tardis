package fr.pandalunatique.tardisplugin.visual;

import fr.pandalunatique.tardisplugin.world.artron.ArtronCloud;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArtronCloudVisual {

    public static int VISUAL_DELAY = 0;
    public static int VISUAL_REFRESH_RATE = ArtronCloud.VISUAL_REFRESH_RATE;

    public static Runnable getVisualRunnable() {
        return () -> {

            ArtronCloud.getArtronClouds().forEach((chunk, cloud) -> {
                chunk.getWorld().spawnParticle(Particle.SMOKE_NORMAL, cloud.getLocation(), cloud.getCount() * 10, 0.5, 0.3, 0.5, 0.01, null, true);
                chunk.getWorld().spawnParticle(Particle.CLOUD, cloud.getLocation(), cloud.getCount() * 8, 0.5, 0.3, 0.5, 0.01, null, true);
                chunk.getWorld().spawnParticle(Particle.FLAME, cloud.getLocation(), cloud.getCount() * 6, 0.5, 0.3, 0.5, 0.01, null, true);

                cloud.updateLifetime();

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.getLocation().getWorld().equals(cloud.getLocation().getWorld())) {
                        if (onlinePlayer.getLocation().distance(cloud.getLocation()) < ArtronCloud.DANGER_REACH) {

                            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 10, false, false));
                            onlinePlayer.damage(0.5);

                        }
                    }
                }

            });

        };

    }

}
