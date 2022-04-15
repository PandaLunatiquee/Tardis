package fr.pandalunatique.tardis.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TardisEntityRegistry {

    private static TardisEntityRegistry instance;

    private final Map<UUID, TardisEntity> registry;

    private TardisEntityRegistry() {
        this.registry = new HashMap<>();
    }

    public static TardisEntityRegistry getRegistry() {

        if (instance == null) {
            instance = new TardisEntityRegistry();
        }
        return instance;

    }

    public void registerEntity(TardisEntity tardisEntity) {
        this.registry.put(tardisEntity.getModelUUID(), tardisEntity);
    }

    public void unregisterPlayer(TardisEntity tardisEntity) {
        if(this.registry.containsKey(tardisEntity.getModelUUID())) {
            this.registry.remove(tardisEntity.getEntityUUID());
        }

    }

    public boolean isRegistered(UUID entityUUID) {
        //TODO: make method to return if the entity is registered
        return true;
    }

    public TardisEntity getEntity(UUID entityUUID) {
        return this.registry.get(entityUUID);
    }
}