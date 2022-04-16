package fr.pandalunatique.tardisplugin.visual;

import fr.pandalunatique.tardisplugin.player.tool.forcefield.ForceField;
import fr.pandalunatique.tardisplugin.world.artron.ArtronCloud;
import org.bukkit.Particle;

public class ForceFieldVisual {

    public static int VISUAL_DELAY = 0;
    public static int VISUAL_REFRESH_RATE = 1;

    public static Runnable getVisualRunnable() {
        return () -> {
            ForceField.shieldsList.keySet().forEach((shield) -> {
                shield.getWorld().spawnParticle(Particle.REVERSE_PORTAL, shield, 1, 0, 0, 0, 0.01);
            });
        };

    }

}
