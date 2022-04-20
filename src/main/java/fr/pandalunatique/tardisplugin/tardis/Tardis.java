package fr.pandalunatique.tardisplugin.tardis;

import com.google.gson.Gson;
import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.util.BooleanStorableSet;
import fr.pandalunatique.tardisplugin.util.LocationLib;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Tardis {

    @Getter private final UUID owner;

    @Getter private final Map<UUID, BooleanStorableSet<RolePermission>> roles;
    @Getter private final Set<UUID> visitors;
    @Getter @Setter private Banlist banlist;

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

    //DEBUG: This is a debug method, it will be removed in the future
    public void dump() {

        System.out.println("Tardis dump for " + Bukkit.getPlayer(this.getOwner()));
        System.out.println();
        System.out.println("Owner: " + this.getOwner().toString());
        System.out.println("Roles: ");
        for(UUID uuid : this.roles.keySet()) {
            System.out.println("\t" + uuid.toString() + ": " + this.roles.get(uuid).getSet().toString());
        }
        System.out.println("Visitors: " + this.visitors.toString());
        System.out.println("Banlist: " + this.getBanlist().serialize());
        System.out.println("Tardis location: " + this.getTardisLocation().toString());
        System.out.println("Tardis facing: " + this.getFacing().toString());
        System.out.println("Plot location: " + this.getPlotLocation().toString());
        System.out.println("Is chameleon circuit enabled: " + this.isChameleonCircuitEnabled);
        System.out.println("Is new: " + this.isNew);
        System.out.println("Level: " + this.getLevel());
        System.out.println("Experience: " + this.getExperience());
        System.out.println("Is public: " + this.isPublic);
        System.out.println("Is door open: " + this.isDoorOpen);
        System.out.println("Is landed: " + this.isLanded);
        System.out.println("Is free flying: " + this.isFreeFlying);
        System.out.println("Is landing: " + this.isLanding);
        System.out.println("Landing floor: " + this.getLandingFloor());
        System.out.println("Door opening state: " + this.getDoorOpeningState());

    }


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
        this.plotLocation = new Location(Bukkit.getWorlds().stream().filter((e) -> e.getName().equals("Tardis")).findFirst().get(), 0, 0, 0); //FIXME: Better line

        this.roles = new HashMap<>();
        this.banlist = new Banlist();
        this.visitors = new HashSet<>();

    }

    public Tardis(Player p) {
        this(p.getUniqueId());
    }

    public Tardis(TardisPlayer p) {
        this(p.getUuid());
    }

    public static Tardis fromResultSet(ResultSet rs) throws SQLException {

        Tardis t = new Tardis(UUID.fromString(rs.getString("Owner")));
        t.setLevel(rs.getInt("Level"));
        t.setExperience(rs.getInt("Experience"));
        t.setTardisLocation(LocationLib.jsonToLocation(rs.getString("TardisLocation")));
        t.setPlotLocation(LocationLib.jsonToLocation(rs.getString("TardisPlotLocation")));
        t.setFacing(TardisFacing.values()[rs.getInt("Facing")]);
        t.setChameleon(TardisChameleon.values()[rs.getInt("Chameleon")]);
        t.setChameleonCircuitEnabled(rs.getBoolean("ChameleonEnabled"));
        t.setAppearance(TardisAppearance.values()[rs.getInt("Appearance")]);
        t.setBanlist(Banlist.deserialize(rs.getString("Banlist")));
        t.setNew(rs.getBoolean("IsNew"));

        return t;

    }

    public static void toPreparedStatement(Tardis t, PreparedStatement stmt) throws SQLException {

        stmt.setInt(1, t.getLevel());
        stmt.setInt(2, t.getExperience());
        stmt.setString(3, LocationLib.locationToJson(t.getTardisLocation()));
        stmt.setString(4, LocationLib.locationToJson(t.getPlotLocation()));
        stmt.setInt(5, t.getFacing().ordinal());
        stmt.setInt(6, t.getChameleon().ordinal());
        stmt.setBoolean(7, t.isChameleonCircuitEnabled());
        stmt.setInt(8, t.getAppearance().ordinal());
        stmt.setString(9, t.getBanlist().serialize());
        stmt.setBoolean(10, t.isNew());
        stmt.setString(11, t.getOwner().toString());

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
