package fr.pandalunatique.tardisplugin.tardis.blueprint;

import lombok.Getter;

public enum StorageBlueprint {

    SMALL_STORAGE(0, 0, 0),
    MEDIUM_STORAGE(1, 0, 0),
    LARGE_STORAGE(2, 0, 0),
    VAULT(3, 0, 0),
    ARMORY(4, 0, 0),
    WORKBENCH(5, 0, 0),
    LIBRARY(6, 0, 0),
    LABORATORY(7, 0, 0),
    CHEMISTRY(8, 0, 0),
    CHAMBER(9, 0, 0),
    DOUBLE_CHAMBER(10, 0, 0),
    KITCHEN(11, 0, 0);

    @Getter
    private final int bit;
    private final int creation;
    private final int consumption;

    /**
     * Represent a storage blueprint associated with its energy consumption, creation, etc...
     * @param bit Position of bit in boolean storage system
     * @param creation Energy needed for creation
     * @param consumption Energy consumption per minute
     */
    private StorageBlueprint(int bit, int creation, int consumption) {
        this.bit = bit;
        this.creation = creation;
        this.consumption = consumption;
    }

    public int toInt() {

        return (int) Math.pow(2, this.bit);

    }

}
