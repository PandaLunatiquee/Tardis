package fr.pandalunatique.tardis.tardis;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class Tardis {

    private Player owner;

    private Map<Player, TardisRole> tardisRoles;
    private Set<Player> visitors; // List of players currently visiting tardis
    private Map<Player, String> banList;

    private boolean isPublic;
    private boolean isDoorOpen;
    private boolean isLanding; // Landing during teleportation

    private boolean isFreeFlying;
    private boolean isLanded; // Specify whether the tardis is landing or flying

    private boolean chameleonCircuitEnabled;

    private ObjectUtils.Null tardisRooms;

    private TardisSkin tardisSkin;
    private TardisCamouflage tardisCamouflage;

    private int tardisLevel;
    private int tardisCurrentXp;

    public Tardis(Player p) {
        this.owner = p;
        this.isPublic = false;
        this.isDoorOpen = false;
        this.isLanded = true;
        this.isLanding = false;
        this.isFreeFlying = false;
        this.chameleonCircuitEnabled = false;
        this.tardisSkin = TardisSkin.DEFAULT;
        this.tardisCamouflage = TardisCamouflage.NONE;
        this.tardisLevel = 0;
        this.tardisCurrentXp = 0;
    }

}
