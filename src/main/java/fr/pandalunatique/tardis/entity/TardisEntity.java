package fr.pandalunatique.tardis.entity;

import java.util.UUID;

public class TardisEntity {

    private TardisEntityType type = TardisEntityType.DALEK;

    public UUID getModelUUID() { return UUID.randomUUID(); }
    public UUID getEntityUUID() { return UUID.randomUUID(); }
    public TardisEntityType getType() { return type; }
}
