package fr.pandalunatique.tardisplugin.tardis;

public enum TardisFacing {

    EAST((short) 0),
    SOUTH((short) 1),
    WEST((short) 2),
    NORTH((short) 3);

    private final short id;

    private TardisFacing(short id) {
        this.id = id;
    }

    public short getId() {
        return id;
    }

    public static TardisFacing valueOf(short id) {
        for (TardisFacing facing : values()) {
            if (facing.getId() == id) {
                return facing;
            }
        }
        return null;
    }
}