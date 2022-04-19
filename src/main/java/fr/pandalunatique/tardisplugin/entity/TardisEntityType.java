package fr.pandalunatique.tardisplugin.entity;

import fr.pandalunatique.tardisplugin.tardis.Tardis;
import org.bukkit.entity.EntityType;

public enum TardisEntityType {

    DALEK(0),
    THE_SILENCE(1),
    CYBERMAN(2),
    CYBERSHADE(3),
    CYBERMITE(4),
    THE_ENTITY(5),
    VASHTA_NERADA(6),
    SYCORAX(7),
    STENZA(8),
    TIME_LORD(9),
    WEEPING_ANGEL(10),
    SONTARAN(11),
    JUDOON(12);

    private int bit;

    TardisEntityType(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return this.bit;
    }

    public static TardisEntityType fromBit(int bit) {
        for (TardisEntityType type : values()) {
            if (type.getBit() == bit) {
                return type;
            }
        }
        return null;
    }

}