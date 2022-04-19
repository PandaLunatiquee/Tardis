package fr.pandalunatique.tardisplugin.visual;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class VisualManager {

    private static final Set<BukkitTask> visuals = new HashSet<>();

    public static void startAll() {

        stopAll();

        for(Visual v : Visual.values()) {
            BukkitTask task = v.startTask();
            if(task != null) {
                visuals.add(task);
            }
        }

    }

    public static void stopAll() {
        visuals.forEach(BukkitTask::cancel);
    }

    public static void start(Visual v) {
        BukkitTask task = v.startTask();
        if(task != null) {
            visuals.add(v.startTask());
        }
    }

}
