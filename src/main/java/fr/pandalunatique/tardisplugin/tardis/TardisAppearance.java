package fr.pandalunatique.tardisplugin.tardis;

// Aesthetic Skin List
public enum TardisAppearance {

    DEFAULT(0),
    DOCTOR_VARIANT1(1),
    DOCTOR_VARIANT2(2),
    DOCTOR_VARIANT3(3),
    DOCTOR_VARIANT4(4),
    DOCTOR_VARIANT5(5),
    DOCTOR_VARIANT6(6),
    DOCTOR_VARIANT7(7),
    DOCTOR_VARIANT8(8),
    DOCTOR_VARIANT9(9),
    HALLOWEEN(10),
    XMAS(11);

    private final int bit;

    private TardisAppearance(int id) {
        this.bit = id;
    }

    public int getBit() {
        return this.bit;
    }

    public static TardisAppearance fromBit(int bit) {

        for(TardisAppearance appearance : values()) {
            if(appearance.getBit() == bit) {
                return appearance;
            }
        }
        return null;

    }

}
