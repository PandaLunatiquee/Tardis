package fr.pandalunatique.tardisplugin.tardis;

// Aesthetic Skin List
public enum TardisAppearance {

    DEFAULT((short) 0),
    DOCTOR_VARIANT1((short) 1),
    DOCTOR_VARIANT2((short) 2),
    DOCTOR_VARIANT3((short) 3),
    DOCTOR_VARIANT4((short) 4),
    DOCTOR_VARIANT5((short) 5),
    DOCTOR_VARIANT6((short) 6),
    DOCTOR_VARIANT7((short) 7),
    DOCTOR_VARIANT8((short) 8),
    DOCTOR_VARIANT9((short) 9),
    HALLOWEEN((short) 10),
    XMAS((short) 11);

    private final short id;

    private TardisAppearance(short id) {
        this.id = id;
    }

    public short getId() {
        return this.id;
    }

    public static TardisAppearance valueOf(short id) {
        for(TardisAppearance appearance : values()) {
            if(appearance.getId() == id) {
                return appearance;
            }
        }
        return null;
    }

}
