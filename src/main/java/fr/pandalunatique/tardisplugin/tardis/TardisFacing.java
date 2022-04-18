package fr.pandalunatique.tardisplugin.tardis;

public enum TardisFacing {

    EAST(0),
    SOUTH(1),
    WEST(2),
    NORTH(3);

    private final int id;

    private TardisFacing(int id) {
        this.id = id;
    }

    public int getBit() {
        return id;
    }

    public static TardisFacing valueOf(int id) {
        for (TardisFacing facing : values()) {
            if (facing.getBit() == id) {
                return facing;
            }
        }
        return null;
    }
}