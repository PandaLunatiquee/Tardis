package fr.pandalunatique.tardisplugin.visual;

import fr.pandalunatique.tardisplugin.world.artron.ArtronCloud;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * Interface for the visual task.
 *
 * @author PandaLunatique
 * @version 1.0
 * @since 1.0
 * @see Runnable
 */
public interface VisualTask {

    int VISUAL_DELAY = 0;
    int VISUAL_REFRESH_RATE = 10;

    /**
     * Method used to get the content of the task to update.
     *
     * @return Runnable The content of the visual task to run
     */
    static Runnable getVisualRunnable() {
        return () -> {
            Bukkit.broadcastMessage("This is a visual task example.");
        };
    }

}
