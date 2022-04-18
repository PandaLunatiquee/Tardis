package fr.pandalunatique.tardisplugin.tardis;

import com.google.gson.Gson;
import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.util.LocationLib;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class Tardis {

    @Getter private final UUID owner;

    @Getter private final Map<UUID, TardisRole> roles;
    @Getter private final Set<UUID> visitors;
    @Getter private final Map<UUID, String> banlist;

    @Getter @Setter private Location tardisLocation;
    @Getter @Setter private TardisFacing facing;
    @Getter @Setter private Location plotLocation;

    @Getter @Setter private boolean isChameleonCircuitEnabled;
    @Getter @Setter private boolean isNew;

    @Getter @Setter private TardisAppearance appearance;
    @Getter @Setter private TardisChameleon chameleon;

    @Getter @Setter private int level;
    @Getter @Setter private int experience;

    private ObjectUtils.Null rooms;

    // Non stored values (in database)
    @Getter @Setter private boolean isPublic;
    @Getter @Setter private boolean isDoorOpen;
    @Getter @Setter private boolean isLanded;
    @Getter @Setter private boolean isFreeFlying;
    @Getter @Setter private boolean isLanding;
    @Getter @Setter private int landingFloor;
    @Getter @Setter private short doorOpeningState; // 0 = fully closed / 90 = fully opened

    public Tardis(UUID uuid) {

        this.owner = uuid;
        this.isPublic = false;
        this.isDoorOpen = false;
        this.isLanded = true;
        this.isLanding = false;
        this.isFreeFlying = false;
        this.isChameleonCircuitEnabled = false;
        this.appearance = TardisAppearance.DEFAULT;
        this.chameleon = TardisChameleon.DEFAULT;
        this.facing = TardisFacing.EAST;
        this.level = 0; // Level 0 before tardis crafting
        this.experience = 0;
        this.doorOpeningState = 0;
        this.tardisLocation = new Location(Bukkit.getWorld("Tardis"), 0, 0, 0);
        this.plotLocation = new Location(Bukkit.getWorlds().stream().filter((e) -> e.getName() != "Tardis").findFirst().get(), 0, 0, 0); //FIXME: Better ligne

        this.roles = new HashMap<>();
        this.banlist = new HashMap<>();
        this.visitors = new HashSet<>();

    }

    public Tardis(Player p) {
        this(p.getUniqueId());
    }

    public Tardis(TardisPlayer p) {
        this(p.getUuid());
    }

    public static boolean canLandAt(Location location) {

        boolean freeSpace = LocationLib.checkFreeSpace(location, 2, 2, 3); // Check for free space
        boolean noNearbyTardis = TardisRegistry.getRegistry().getNearbyTardis(location, 2).size() == 0; // Check nearby tardis
        //TODO: No jammer
        //TODO: Not a protected area
        //TODO: Block blacklist : water, lava, black

        return freeSpace && noNearbyTardis;

    }

    public void togglePublicState() { this.setPublic(!this.isPublic); }

    public void toggleDoor() { this.setDoorOpen(this.isDoorOpen); }

    public boolean landTardisAt(final Location loc) {



        return false;
    }

//    public void resetTardisSkin() { tardisSkin = TardisAppearance.DEFAULT; }
//    public void changeTardisSkin(TardisAppearance ts) { tardisSkin = ts; }
//    public void resetTardisCamo() { tardisChameleon = TardisChameleon.NONE; }
//    public void changeTardisCamo(TardisChameleon tc) { tardisChameleon = tc; }
//
//    public void setTardisLevel(int lvl) { tardisLevel = lvl; }
//    public void setTardisXp(int xp) { tardisTotalXp = xp; updateTardisLvl(); }
//    public void addTardisXp(int xp) { tardisTotalXp += xp; updateTardisLvl(); }
//    public void updateTardisLvl() {/*update lvl based on xp */}


}
