package fr.pandalunatique.tardisplugin.visual;

import fr.pandalunatique.tardisplugin.world.artron.ArtronCloud;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Iterator;
import java.util.Map;

public class ArtronCloudVisual implements VisualTask {

    public static int VISUAL_DELAY = 0;
    public static int VISUAL_REFRESH_RATE = ArtronCloud.VISUAL_REFRESH_RATE;

    public static Runnable getVisualRunnable() {
        return () -> {

            Iterator<Map.Entry<Chunk, ArtronCloud>> iterator = ArtronCloud.getArtronClouds().entrySet().iterator();

            iterator.forEachRemaining(entry -> {

                entry.getKey().getWorld().spawnParticle(Particle.SMOKE_NORMAL, entry.getValue().getLocation(), entry.getValue().getCount() * 10, 0.5, 0.3, 0.5, 0.01, null, true);
                entry.getKey().getWorld().spawnParticle(Particle.CLOUD, entry.getValue().getLocation(), entry.getValue().getCount() * 8, 0.5, 0.3, 0.5, 0.01, null, true);
                entry.getKey().getWorld().spawnParticle(Particle.FLAME, entry.getValue().getLocation(), entry.getValue().getCount() * 6, 0.5, 0.3, 0.5, 0.01, null, true);

                entry.getValue().updateLifetime();

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.getLocation().getWorld().equals(entry.getValue().getLocation().getWorld())) {
                        if (onlinePlayer.getLocation().distance(entry.getValue().getLocation()) < ArtronCloud.DANGER_REACH) {

                            onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 10, false, false));
                            onlinePlayer.damage(0.5);

                        }
                    }
                }

            });

            ArtronCloud.purge();

        };

    }

}
