package fr.pandalunatique.tardisplugin.tardis;

// Chameleon morph List
public enum TardisChameleon {

    DEFAULT((short) 0),
    POLICE_BOX((short) 1),
    POLICE_BOX_VARIANT_1((short) 2);

    private final short id;

    private TardisChameleon(short id) {
        this.id = id;
    }

    public short getId() {
        return this.id;
    }

    public static TardisChameleon valueOf(short id) {
        for(TardisChameleon chameleon : values()) {
            if(chameleon.getId() == id) {
                return chameleon;
            }
        }
        return null;
    }

}
