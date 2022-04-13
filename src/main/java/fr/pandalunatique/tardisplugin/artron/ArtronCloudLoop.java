package fr.pandalunatique.tardisplugin.artron;

import fr.pandalunatique.tardisplugin.TardisPlugin;
import org.bukkit.Bukkit;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class ArtronCloudLoop {

    public void start() {
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(TardisPlugin.getInstance(), new Runnable() {
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity e : world.getEntities()) {
                        if (e.getType() == EntityType.ARMOR_STAND) {
                            if (e.getCustomName().contains("Artron")) {
                                world.spawnParticle(Particle.SMOKE_NORMAL, e.getLocation(), 50, 0.5, 0.3, 0.5, 0.01);
                                world.spawnParticle(Particle.CLOUD, e.getLocation(), 30, 0.5, 0.3, 0.5, 0.01);
                                world.spawnParticle(Particle.FLAME, e.getLocation(), 10, 0.5, 0.3, 0.5, 0.01);
                            }
                        }
                    }
                }
            }
        }, 20l, 10l);
    }
}
