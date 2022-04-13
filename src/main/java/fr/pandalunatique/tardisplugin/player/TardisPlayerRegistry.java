package fr.pandalunatique.tardisplugin.player;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TardisPlayerRegistry {

    private static TardisPlayerRegistry instance;

    private final Map<UUID, TardisPlayer> registry;

    private TardisPlayerRegistry() {

        this.registry = new HashMap<>();

    }

    public static TardisPlayerRegistry getRegistry() {

        if (instance == null) {
            instance = new TardisPlayerRegistry();
        }
        return instance;

    }

    public void registerPlayer(TardisPlayer tardisPlayer) {

        if(this.registry.containsKey(tardisPlayer.getUuid())) {
            this.updatePlayer(tardisPlayer);
        } else {
            this.registry.put(tardisPlayer.getUuid(), tardisPlayer);
        }

    }

    public void updatePlayer(TardisPlayer tardisPlayer) {

        if(this.registry.containsKey(tardisPlayer.getUuid())) {
            this.registry.replace(tardisPlayer.getUuid(), tardisPlayer);
        } else {
            this.registerPlayer(tardisPlayer);
        }

    }

    public void unregisterPlayer(UUID uuid) {

        this.registry.remove(uuid);

    }

    public void unregisterPlayer(TardisPlayer tardisPlayer) {

       this.unregisterPlayer(tardisPlayer.getUuid());

    }

    public TardisPlayer getPlayer(UUID uuid) {

        return this.registry.get(uuid);

    }

    public boolean isRegistered(UUID uuid) {
        return this.getPlayer(uuid) != null;
    }

}
