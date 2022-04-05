package fr.pandalunatique.tardis.tardis;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Tardis {

    private UUID owner;

    private Map<UUID, TardisRole> tardisRoles;
    private Set<UUID> visitors; // List of players currently visiting tardis
    private Map<UUID, String> banList;

    private boolean isPublic;
    private boolean isDoorOpen;
    private boolean isLanding; // Landing during teleportation

    private boolean isFreeFlying;
    private boolean isLanded; // Specify whether the tardis is landing or flying

    private boolean chameleonCircuitEnabled;

    private TardisSkin tardisSkin; // Pure decorative skin
    private TardisChameleon tardisChameleon; // Camouflage used by the chameleon circuit

    private int tardisLevel;
    private int tardisTotalXp;

    private ObjectUtils.Null tardisRooms;

    public Tardis(Player p) {
        this.owner = p.getUniqueId();
        this.isPublic = false;
        this.isDoorOpen = false;
        this.isLanded = true;
        this.isLanding = false;
        this.isFreeFlying = false;
        this.chameleonCircuitEnabled = false;
        this.tardisSkin = TardisSkin.DEFAULT;
        this.tardisChameleon = TardisChameleon.NONE;
        this.tardisLevel = 0;
        this.tardisTotalXp = 0;
    }

    public boolean getPublicState() { return isPublic; }
    public boolean getDoorState() { return isDoorOpen; }
    public boolean isLanded() { return isLanded; }
    public boolean isLanding() { return isLanding; }
    public boolean isFreeFlying() { return isFreeFlying; }
    public boolean chameleonCircuitEnabled() { return chameleonCircuitEnabled; }
    public TardisSkin getTardisSkin() { return tardisSkin; }
    public TardisChameleon getTardisCamo() { return tardisChameleon; }
    public int getTardisLevel() { return tardisLevel; }
    public int getTardisXp() { return tardisTotalXp; }

    public void togglePublicState() { setPublicState(!isPublic); }
    public void setPublicState(final boolean state) { isPublic = state; }
    public void toggleDoor() { setDoorState(!isDoorOpen); }
    public void setDoorState(final boolean state) { isDoorOpen = state; }

    public boolean landTardisAt(final Location loc) {
        isLanded = false; isLanding = true;
        final boolean previousDoorState = isDoorOpen; isDoorOpen = false;
        //check if landing is possible
        //true : ptite anim
        //       tp tardis at loc
        //       isLanding = false;
        //       isLanded = true;
        //       isDoorOpen = previousDoorState;
        //       return true;
        //false:
        //       isLanding = false;
        //       isLanded = true;
        //       isDoorOpen = previousDoorState;
        //       return false;
        return false;
    }

    public void resetTardisSkin() { tardisSkin = TardisSkin.DEFAULT; }
    public void changeTardisSkin(TardisSkin ts) { tardisSkin = ts; }
    public void resetTardisCamo() { tardisChameleon = TardisChameleon.NONE; }
    public void changeTardisCamo(TardisChameleon tc) { tardisChameleon = tc; }

    public void setTardisLevel(int lvl) { tardisLevel = lvl; }
    public void setTardisXp(int xp) { tardisTotalXp = xp; updateTardisLvl(); }
    public void addTardisXp(int xp) { tardisTotalXp += xp; updateTardisLvl(); }
    public void updateTardisLvl() {/*update lvl based on xp */}


}
