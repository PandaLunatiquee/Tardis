package fr.pandalunatique.tardisplugin.visual;

import fr.pandalunatique.tardisplugin.TardisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public enum Visual {

    ARTRON_CLOUD_VISUAL(ArtronCloudVisual.class);

    private final Class<?> clazz;
    private final Method getVisualRunnable;
    private final Field visualDelay;
    private final Field visualRefreshRate;

    Visual(Class<?> clazz) {

        Method getVisualRunnable;
        Field visualDelay;
        Field visualRefreshRate;

        this.clazz = clazz;

        try {

            getVisualRunnable = clazz.getMethod("getVisualRunnable");
            visualDelay = clazz.getField("VISUAL_DELAY");
            visualRefreshRate = clazz.getField("VISUAL_REFRESH_RATE");

        } catch(NoSuchMethodException | NoSuchFieldException e) {
            //TODO: Handle exception

            getVisualRunnable = null;
            visualDelay = null;
            visualRefreshRate = null;

        }

        this.getVisualRunnable = getVisualRunnable;
        this.visualDelay = visualDelay;
        this.visualRefreshRate = visualRefreshRate;

    }

    public BukkitTask startTask() {

        try {

            return Bukkit.getScheduler().runTaskTimer(TardisPlugin.getInstance(), (Runnable) this.getVisualRunnable.invoke(null), (int) this.visualDelay.get(null), (int) this.visualRefreshRate.get(null));

        } catch(InvocationTargetException | IllegalAccessException e) {

            System.out.println(ChatColor.RED + "[TardisVisuals] Unable to start visual " + this.clazz.getName());

        }
        return null;

    }

}
