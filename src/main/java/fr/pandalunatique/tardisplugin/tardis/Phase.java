package fr.pandalunatique.tardisplugin.tardis;

public class Phase {

    public static int TARDIS_PHASE_MODEL_COUNT = 6;
    public static int[] PHASE_SEQUENCE = {0, 1, 2, 3, 4, 5};

    public static enum Type {

        PHASE,
        UNPHASE;

    }

    private Type type;
    private int model;

    public Phase(Type type) {

        this.type = type;

    }

}
