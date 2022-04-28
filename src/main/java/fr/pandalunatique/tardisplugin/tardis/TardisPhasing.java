package fr.pandalunatique.tardisplugin.tardis;

import fr.pandalunatique.tardisplugin.TardisPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TardisPhasing {

    private static TardisPhasing instance;

    private Map<UUID, BukkitTask> phasing;
    private Map<UUID, BukkitTask> unphasing;

    private TardisPhasing() {
        this.phasing = new HashMap<>();
        this.unphasing = new HashMap<>();
    }

    public static TardisPhasing getInstance() {
        if (instance == null) {
            instance = new TardisPhasing();
        }
        return instance;
    }

    public void phaseAt(Location location, Tardis tardis) {

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(TardisPlugin.getInstance(), () -> {


        }, 0, 1);

        this.phasing.put(tardis.getOwner(), task);

    }

    public void unphaseAt(Location location, Tardis tardis) {

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(TardisPlugin.getInstance(), () -> {



        }, 0, 1);

        this.unphasing.put(tardis.getOwner(), task);

    }

}
