package fr.pandalunatique.tardisplugin.player;

import fr.pandalunatique.tardisplugin.Database;
import fr.pandalunatique.tardisplugin.entity.TardisEntityType;
import fr.pandalunatique.tardisplugin.player.tool.bestiary.Bestiary;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisRegistry;
import fr.pandalunatique.tardisplugin.util.BooleanStorableSet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TardisPlayer {

    @Getter private final UUID uuid;
    @Getter @Setter private int level;
    @Getter @Setter private int experience;

    @Getter @Setter private BooleanStorableSet<TardisEntityType> blueprintStorage;
    @Getter @Setter private BooleanStorableSet<TardisEntityType> blueprintBattery;
    @Getter @Setter private BooleanStorableSet<TardisEntityType> blueprintMiscellaneous;

    @Getter @Setter private Bestiary bestiary;

    public TardisPlayer(Player p) {
        this(p.getUniqueId());
    }

    public TardisPlayer(UUID uuid) {
        this.uuid = uuid;
        this.level = 0;
        this.experience = 0;
        this.bestiary = new Bestiary();
    }

    public static TardisPlayer fromResultSet(ResultSet rs) throws SQLException {

        TardisPlayer tp = new TardisPlayer(UUID.fromString(rs.getString("Uuid")));
        tp.setLevel(rs.getInt("Level"));
        tp.setExperience(rs.getInt("Experience"));
        tp.setBestiary(Bestiary.deserialize(rs.getString("Bestiary")));

        return tp;

    }

    public static void toPreparedStatement(TardisPlayer tp, PreparedStatement stmt) throws SQLException {

        stmt.setInt(1, tp.getLevel());
        stmt.setInt(2, tp.getExperience());
        stmt.setString(3, tp.getBestiary().serialize());
        stmt.setString(4, tp.getUuid().toString());

    }

    public static TardisPlayer fromDatabase(UUID uuid) {

        return Database.getPlayer(uuid);

    }


    public Player getPlayer() {

        return Bukkit.getServer().getPlayer(this.uuid);

    }

    public void save() {
        Database.updatePlayer(this);
    }

    public boolean hasTardis() {
        return TardisRegistry.getRegistry().isRegistered(this.uuid);
    }

    public Tardis getTardis() {
        return TardisRegistry.getRegistry().getTardis(this.uuid);
    }

}
