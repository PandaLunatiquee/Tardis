package fr.pandalunatique.tardisplugin;


import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisAppearance;
import fr.pandalunatique.tardisplugin.tardis.TardisChameleon;
import fr.pandalunatique.tardisplugin.tardis.TardisFacing;
import fr.pandalunatique.tardisplugin.util.BooleanStorable;
import fr.pandalunatique.tardisplugin.util.LocationLib;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Database class used to store and retrieve data from the database.
 * This is a singleton class.
 *
 * @author PandaLunatique
 * @version 1.0
 * @see Tardis
 * @see TardisPlayer
 * @see TardisAppearance
 * @see TardisChameleon
 * @see TardisFacing
 * @see BooleanStorable
 * @see LocationLib
 */
public class Database {

    private static Database instance;

    private final String path;

    /**
     * Database constructor.
     * Used only by the getInstance() method and to initialize the database.
     *
     * @author PandaLunatique
     */
    private Database() {

        this.path = "jdbc:sqlite:plugins/Tardis/tardis.db";
        this.initialize();

    }

    /**
     * Get current instance of database or create a new one
     *
     * @return Database instance (singleton)
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Check if the database is connectable.
     *
     * @return true if the database is connectable, false otherwise
     */
    public static boolean canConnect() {
        try {
            Database.getInstance().getConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Get a connection to the database.
     *
     * @return A connection to the database
     * @throws SQLException if the connection failed
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.path);
    }

    /**
     * Initialize the database.
     * Create the table if it doesn't exist.
     * Then insert default values.
     */
    private void initialize() {

        if (new File(this.path).exists()) return;

        try {

            Connection con = this.getConnection();
            Statement stmt = con.createStatement();

            // Create tables
            stmt.execute("CREATE TABLE IF NOT EXISTS Tardis (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Owner VARCHAR(36) UNIQUE NOT NULL," +
                    "Level INT DEFAULT 0," +
                    "Experience INT DEFAULT 0," +
                    "TardisLocation VARCHAR(128) DEFAULT 0," +
                    "TardisPlotLocation VARCHAR(128) DEFAULT 0," +
                    "Facing TINYINT DEFAULT 0," +
                    "Chameleon TINYINT DEFAULT 0," +
                    "Appearance TINYINT DEFAULT 0" +
            ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS Player (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Uuid VARCHAR(36) UNIQUE NOT NULL, " +
                    "Level INT DEFAULT 0, " +
                    "Experience INT DEFAULT 0" +
            ");");
            stmt.execute("CREATE TABLE IF NOT EXISTS TardisPlotOffset (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "CurrentX INTEGER NOT NULL, " +
                    "CurrentZ INTEGER NOT NULL" +
            ");");

            // Insert default values
            stmt.execute("INSERT INTO TardisPlotOffset (CurrentX, CurrentZ) VALUES (-25000000, -25000000)");


        } catch (SQLException e) {
            System.out.println(ChatColor.RED + "[Tardis] Unable to initialise database!");
        }

    }


    /**
     * Get a set of the 20 first rows of the TardisPlayer table.
     *
     * {@code limit} defaults to 20.
     * {@code offset} defaults to 0.
     * @see TardisPlayer
     * @see Database#getPlayers(int, int)
     * @return Set of 20 firsts TardisPlayer
     */
    public static Set<TardisPlayer> getPlayers() {
        return Database.getPlayers(20, 0);
    }

    /**
     * Get a set of the rows of the TardisPlayer table.
     *
     * @param limit The maximum number of rows to return
     * @param offset The offset of the rows to return
     * @see TardisPlayer
     * @return Set of TardisPlayer
     */
    public static Set<TardisPlayer> getPlayers(int limit, int offset) {

        Set<TardisPlayer> set = new HashSet<>();

        try {

            Connection con = Database.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Player LIMIT " + limit + " OFFSET " + offset + ";");

            while (rs.next()) {
                set.add(new TardisPlayer(UUID.fromString(rs.getString("Uuid")), rs.getInt("Level"), rs.getInt("Experience")));
            }

            con.close();
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to recover players from database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return set;

    }

    /**
     * Retrieve a TardisPlayer from the database by its UUID.
     *
     * @param uuid The UUID of the TardisPlayer (same as the player's UUID)
     * @return The TardisPlayer with the given UUID (null if not found)
     */
    @Nullable
    public static TardisPlayer getPlayer(@NotNull UUID uuid) {

        TardisPlayer tp = null;

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Player WHERE Uuid = ?;");
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tp = new TardisPlayer(UUID.fromString(rs.getString("Uuid")), rs.getInt("Level"), rs.getInt("Experience"));
            }

            con.close();
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to recover player from database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return tp;

    }

    /**
     * Remove a TardisPlayer from the database.
     *
     * @param tp The TardisPlayer to remove
     * @see Database#removePlayer(UUID)
     * @return True if the TardisPlayer was removed, false otherwise
     */
    public static boolean removePlayer(@NotNull TardisPlayer tp) {

        return Database.removePlayer(tp.getUuid());

    }

    /**
     * Remove a TardisPlayer from the database.
     *
     * @param uuid The UUID of the TardisPlayer to remove
     * @return True if the TardisPlayer was removed, false otherwise
     */
    public static boolean removePlayer(@NotNull UUID uuid) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Player WHERE Uuid = ?;");
            stmt.setString(1, uuid.toString());
            stmt.execute();

            con.close();
            stmt.close();

            return true;

        } catch (SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to remove player from database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }
        return false;
    }

    /**
     * Check if a TardisPlayer exists in the database.
     *
     * @see Database#getPlayer(UUID)
     * @param uuid The UUID of the TardisPlayer
     * @return True if the TardisPlayer exists, false otherwise
     */
    public static boolean playerExists(@NotNull UUID uuid) {

        return Database.getPlayer(uuid) != null;

    }

    /**
     * Add a TardisPlayer to the database.
     *
     * @param tp The TardisPlayer to add
     * @return True if the TardisPlayer was added, false otherwise
     */
    public static boolean addPlayer(@NotNull TardisPlayer tp) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Player (Uuid, Level, Experience) VALUES (?, ?, ?)");

            stmt.setString(1, tp.getUuid().toString());
            stmt.setInt(2, tp.getLevel());
            stmt.setInt(3, tp.getExperience());
            stmt.execute();

            con.close();
            stmt.close();

            return true;

        } catch (SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to add player to database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return false;

    }

    /**
     * Reset a TardisPlayer in the database.
     *
     * @param uuid The TardisPlayer to update
     * @return True if the TardisPlayer was updated, false otherwise
     */
    public static boolean resetPlayer(@NotNull UUID uuid) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Player SET Level = ?, Experience = ? WHERE Uuid = ?;");
            stmt.setInt(1, 0);
            stmt.setInt(2, 0);
            stmt.setString(3, uuid.toString());

            stmt.execute();
            con.close();
            stmt.close();

            return true;

        } catch (SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to reset player from database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return false;

    }

    /**
     * Reset a TardisPlayer in the database.
     *
     * @param tp The TardisPlayer to reset
     * @see Database#resetPlayer(UUID)
     * @return True if the TardisPlayer was reset, false otherwise
     */
    public static boolean resetPlayer(@NotNull TardisPlayer tp) {

        return Database.resetPlayer(tp.getUuid());

    }

    /**
     * Update a TardisPlayer in the database.
     *
     * @param tp The TardisPlayer to update
     * @return True if the TardisPlayer was updated, false otherwise
     */
    public static boolean updatePlayer(@NotNull TardisPlayer tp) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Player SET Level=?, Experience=? WHERE Uuid=?;");
            stmt.setInt(1, tp.getLevel());
            stmt.setInt(2, tp.getExperience());
            stmt.setString(2, tp.getUuid().toString());

            stmt.execute();
            con.close();
            stmt.close();

            return true;

        } catch (SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to update player in database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return false;

    }


    /**
     * Get a set of the 20 first rows of the Tardis table.
     *
     * {@code limit} defaults to 20.
     * {@code offset} defaults to 0.
     * @see Tardis
     * @see Database#getTardis(int, int)
     * @return Set of 20 firsts Tardis
     */
    public static Set<Tardis> getTardis() {
        return Database.getTardis(20, 0);
    }

    /**
     * Get a set of the rows of the Tardis table.
     *
     * @param limit The maximum number of rows to return
     * @param offset The offset of the rows to return
     * @see Tardis
     * @return Set of Tardis
     */
    public static Set<Tardis> getTardis(int limit, int offset) {

        Set<Tardis> set = new HashSet<>();

        try {

            Connection con = Database.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Tardis LIMIT " + limit + " OFFSET " + offset + ";");

            while(rs.next()) {

                Tardis t = new Tardis(UUID.fromString(rs.getString("Owner")));
                t.setPlotLocation(LocationLib.jsonToLocation(rs.getString("TardisPlotLocation")));
                t.setTardisLocation(LocationLib.jsonToLocation(rs.getString("TardisLocation")));
                t.setFacing(TardisFacing.valueOf(rs.getInt("Facing")));
                t.setAppearance(TardisAppearance.fromBit(rs.getInt("Appearance")));

                set.add(t);

            }
            con.close();
            rs.close();
            stmt.close();

        } catch(SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to get tardis from database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return set;

    }

    /**
     * Retrieve a Tardis from the database by its UUID.
     *
     * @see Tardis
     * @param uuid The UUID of the Tardis owner to retrieve
     * @return The Tardis with the given UUID
     */
    @Nullable
    public static Tardis getTardis(UUID uuid) {

        Tardis t = null;

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Tardis WHERE Owner = ?;");
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {

                t = new Tardis(uuid);
                t.setPlotLocation(LocationLib.jsonToLocation(rs.getString("TardisPlotLocation")));
                t.setTardisLocation(LocationLib.jsonToLocation(rs.getString("TardisLocation")));
                t.setFacing(TardisFacing.valueOf(rs.getInt("Facing")));
                t.setAppearance(TardisAppearance.fromBit(rs.getInt("Appearance")));
            }

            con.close();
            rs.close();
            stmt.close();

        } catch(SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to get the tardis from database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return t;

    }

    /**
     * Retrieve a Tardis from the database by the TardisPlayer owner.
     *
     * @param tp The TardisPlayer owning the Tardis
     * @see Database#getTardis(UUID)
     * @return The Tardis with the given TardisPlayer owner
     */
    @Nullable
    public static Tardis getTardis(TardisPlayer tp) {

        return Database.getTardis(tp.getUuid());

    }

    /**
     * Check if a Tardis exists in the database by its UUID.
     *
     * @param uuid The UUID of the Tardis to check
     * @see Database#getTardis(UUID)
     * @return True if the Tardis exists, false otherwise
     */
    public static boolean hasTardis(UUID uuid) {

        return Database.getTardis(uuid) != null;

    }

    /**
     * Check if a Tardis exists in the database by the TardisPlayer owner.
     *
     * @param tp The TardisPlayer owning the Tardis
     * @see Database#getTardis(TardisPlayer)
     * @see Database#hasTardis(UUID)
     * @return True if the Tardis exists, false otherwise
     */
    public static boolean hasTardis(TardisPlayer tp) {
        return Database.hasTardis(tp.getUuid());
    }

    /**
     * Remove a Tardis from the database by its UUID.
     *
     * @param uuid The UUID of the Tardis to remove
     * @return True if the Tardis was removed, false otherwise
     */
    public static boolean removeTardis(UUID uuid) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Tardis WHERE Owner = ?;");
            stmt.setString(1, uuid.toString());
            stmt.execute();

            con.close();
            stmt.close();

            return true;

        } catch(SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to remove tardis from database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return false;

    }

    /**
     * Remove a Tardis from the database by the TardisPlayer owner.
     *
     * @param tp The TardisPlayer owning the Tardis
     * @see Database#removeTardis(UUID)
     * @return True if the Tardis was removed, false otherwise
     */
    public static boolean removeTardis(Tardis tp) {

        return Database.removeTardis(tp.getOwner());

    }

    /**
     * Add a Tardis to the database
     *
     * @param t The Tardis to add
     * @see Tardis
     * @return True if the Tardis was removed, false otherwise
     */
    public static boolean addTardis(Tardis t) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Tardis (Owner, Level, Experience, TardisLocation, TardisPlotLocation, Facing, Chameleon, Appearance) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, t.getOwner().toString());
            stmt.setInt(2, t.getLevel());
            stmt.setInt(3, t.getExperience());
            stmt.setString(4, LocationLib.locationToJson(t.getTardisLocation()));
            stmt.setString(5, LocationLib.locationToJson(t.getPlotLocation()));
            stmt.setInt(6, t.getFacing().getBit());
            stmt.setInt(7, t.getChameleon().getId());
            stmt.setInt(8, t.getAppearance().getBit());

            stmt.execute();

            con.close();
            stmt.close();

            return true;

        } catch(SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to add tardis to database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return false;

    }

    /**
     * Reset a Tardis in the database
     *
     * @param uuid The UUID of the Tardis owner
     * @return True if the Tardis was removed, false otherwise
     */
    public static boolean resetTardis(UUID uuid) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Tardis SET Level = ?, Experience = ?, Chameleon = ?, Appearance = ? WHERE Owner = ?;");
            stmt.setInt(1, 0);
            stmt.setInt(2, 0);
            stmt.setInt(3, TardisChameleon.DEFAULT.getId());
            stmt.setInt(4, TardisAppearance.DEFAULT.getBit());
            stmt.setString(5, uuid.toString());

            stmt.execute();

            con.close();
            stmt.close();

            return false;

        } catch(SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to reset tardis in database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return false;

    }

    /**
     * Reset a Tardis in the database
     *
     * @param t The Tardis to reset
     * @see Tardis
     * @see Database#removeTardis(UUID)
     * @return True if the Tardis was removed, false otherwise
     */
    public static boolean resetTardis(Tardis t) {

        return Database.resetTardis(t.getOwner());

    }

    /**
     * Update a Tardis in the database
     *
     * @param t The Tardis to update
     * @see Tardis
     * @return True if the Tardis was removed, false otherwise
     */
    public static boolean updateTardis(Tardis t) {

        try {

            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Tardis SET Level = ?, Experience = ?, TardisLocation = ?, TardisPlotLocation = ?, Facing = ?, Chameleon = ?, Appearance = ? WHERE Owner = ?");
            stmt.setInt(1, t.getLevel());
            stmt.setInt(2, t.getExperience());
            stmt.setString(3, LocationLib.locationToJson(t.getTardisLocation()));
            stmt.setString(4, LocationLib.locationToJson(t.getPlotLocation()));
            stmt.setInt(5, t.getFacing().getBit());
            stmt.setInt(6, t.getChameleon().getId());
            stmt.setInt(7, t.getAppearance().getBit());
            stmt.setString(8, t.getOwner().toString());

            stmt.execute();
            con.close();
            stmt.close();

            return true;

        } catch(SQLException e) {
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[Tardis] Unable to update tardis in database "));
            System.out.println(ChatColor.translateAlternateColorCodes('&', " &8» &7You can perform a report on the Tardis GitHub page including this file : //TODO//"));
            // TODO: Add stack trace to report file and identify the cause
        }

        return false;

    }

}
