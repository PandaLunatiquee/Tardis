package fr.pandalunatique.tardisplugin.tardis;

import lombok.Getter;

public enum TardisFacing {

    SOUTH(0),
    WEST(90),
    NORTH(180),
    EAST(270);

    @Getter
    private final int baseAngle;

    TardisFacing(int baseAngle) {
        this.baseAngle = baseAngle;
    }

}