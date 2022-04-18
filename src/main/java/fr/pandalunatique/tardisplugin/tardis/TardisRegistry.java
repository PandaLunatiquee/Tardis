package fr.pandalunatique.tardisplugin.tardis;

import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.player.TardisPlayerRegistry;
import org.bukkit.Location;

import java.util.*;

public class TardisRegistry {

    private static TardisRegistry instance;

    private final Map<UUID, Tardis> registry;

    private TardisRegistry() {

        this.registry = new HashMap<>();

    }

    public static TardisRegistry getRegistry() {

        if (instance == null) {
            instance = new TardisRegistry();
        }
        return instance;

    }

    public Set<Tardis> getNearbyTardis(Location location, double d) {

        Set<Tardis> nearby = new HashSet<>();

        this.registry.forEach((__, tardis) -> {

            if(tardis.getTardisLocation().distance(location) <= d) {
                nearby.add(tardis);
            }
        });

        return nearby;

    }

    public void registerTardis(Tardis tardis) {

        if(this.registry.containsKey(tardis.getOwner())) {
            this.updateTardis(tardis);
        } else {
            this.registry.put(tardis.getOwner(), tardis);
        }

    }

    public void updateTardis(Tardis tardis) {

        if(this.registry.containsKey(tardis.getOwner())) {
            this.registry.replace(tardis.getOwner(), tardis);
        } else {
            this.registerTardis(tardis);
        }

    }

    public void unregisterTardis(UUID uuid) {

        this.registry.remove(uuid);

    }

    public void unregisterTardis(TardisPlayer tardisPlayer) {

        this.unregisterTardis(tardisPlayer.getUuid());

    }

    public Tardis getTardis(UUID uuid) {

        return this.registry.get(uuid);

    }

    public Tardis getTardis(TardisPlayer tardisPlayer) {

        return this.registry.get(tardisPlayer.getUuid());

    }

    public boolean isRegistered(UUID uuid) {
        return this.getTardis(uuid) != null;
    }

    public boolean isRegistered(TardisPlayer tardisPlayer) {
        return this.getTardis(tardisPlayer.getUuid()) != null;
    }

}
