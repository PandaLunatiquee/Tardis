package fr.pandalunatique.tardisplugin;


import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisAppearance;
import fr.pandalunatique.tardisplugin.tardis.TardisChameleon;
import fr.pandalunatique.tardisplugin.tardis.TardisFacing;
import fr.pandalunatique.tardisplugin.util.BooleanStorable;
import fr.pandalunatique.tardisplugin.util.LocationLib;
import org.bukkit.ChatColor;

import java.io.File;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Database {

    private static Database instance;

    private final String path;


    private Database() {

        this.path = "jdbc:sqlite:plugins/Tardis/tardis.db";

        this.initialize();

    }

    public static boolean canConnect() {
        try {
            Database.getInstance().getConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;

    }

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(this.path);

    }

    private void initialize() {

        if (new File(this.path).exists()) return;

        try {

            Connection con = this.getConnection();
            Statement stmt = con.createStatement();

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
            stmt.execute("CREATE TABLE IF NOT EXISTS Player (Id INTEGER PRIMARY KEY AUTOINCREMENT, Uuid VARCHAR(36) UNIQUE NOT NULL, Level INT DEFAULT 0, Experience INT DEFAULT 0)");
            stmt.execute("CREATE TABLE IF NOT EXISTS TardisPlotOffset (Id INTEGER PRIMARY KEY AUTOINCREMENT, CurrentX INTEGER NOT NULL, CurrentZ INTEGER NOT NULL)");

            stmt.execute("INSERT INTO TardisPlotOffset (CurrentX, CurrentZ) VALUES (-25000000, -25000000)");

            con.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(ChatColor.RED + "[Tardis] Unable to initialise database!");
            e.printStackTrace(); //REMOVE: This is for debug
        }

    }

    // Tardis-related methods.
    public static Set<TardisPlayer> getPlayers() {
        return Database.getPlayers(20, 0);
    }

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
            System.out.println(ChatColor.RED + "[Tardis] Unable to recover players from database ");
        }

        return set;

    }

    public static TardisPlayer getPlayer(UUID uuid) {

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
            // TODO: Handle exception
            e.printStackTrace();
        }

        return tp;

    }

    public static void removePlayer(TardisPlayer p) {

        Database.removePlayer(p.getUuid());

    }

    public static void removePlayer(UUID uuid) {
        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Player WHERE Uuid = ?;");
            stmt.setString(1, uuid.toString());
            stmt.execute();

            con.close();
            stmt.close();

        } catch (SQLException e) {
            // TODO: Handle exception
        }
    }

    public static boolean playerExists(UUID uuid) {

        return Database.getPlayer(uuid) != null;

    }

    public static void addPlayer(TardisPlayer p) {

        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Player (Uuid, Level, Experience) VALUES (?, ?, ?)");
            stmt.setString(1, p.getUuid().toString());
            stmt.setInt(2, p.getLevel());
            stmt.setInt(3, p.getExperience());
            stmt.execute();

            con.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void resetPlayer(UUID uuid) {

        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Player SET Level = ?, Experience = ? WHERE Uuid = ?;");
            stmt.setInt(1, 0);
            stmt.setInt(2, 0);
            stmt.setString(3, uuid.toString());
            stmt.execute();
            con.close();
            stmt.close();

        } catch (SQLException e) {
            // TODO: Handle exception
        }

    }

    public static void resetPlayer(TardisPlayer p) {

        Database.resetPlayer(p.getUuid());

    }

    public static void updatePlayer(TardisPlayer p) {

        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Player SET Level=?, Experience=? WHERE Uuid=?;");
            stmt.setInt(1, p.getLevel());
            stmt.setInt(2, p.getExperience());
            stmt.setString(2, p.getUuid().toString());
            stmt.execute();
            con.close();
            stmt.close();

        } catch (SQLException e) {
            // TODO: Handle exception
        }

    }

    // TODO: Per value update


    public static Set<Tardis> getTardis() {
        return Database.getTardis(20, 0);
    }

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
            //TODO: Handle exception
        }

        return set;

    }

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
            //TODO: Handle exception
        }

        return t;

    }

    public static Tardis getTardis(TardisPlayer p) {

        return Database.getTardis(p.getUuid());

    }

    public static boolean hasTardis(UUID uuid) {

        return Database.getTardis(uuid) != null;

    }

    public static boolean hasTardis(TardisPlayer p) {
        return Database.hasTardis(p.getUuid());
    }

    public static void removeTardis(UUID uuid) {

        try {
            Connection con = Database.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Tardis WHERE Owner = ?;");
            stmt.setString(1, uuid.toString());

            stmt.execute();

            con.close();
            stmt.close();

        } catch(SQLException e) {
            //TODO: Handle exceptiond
        }

    }

    public static void removeTardis(Tardis t) {

        Database.removeTardis(t.getOwner());

    }

    public static void addTardis(Tardis t) {

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

        } catch(SQLException e) {
            //TODO: Handle exception
        }

    }

    public static void resetTardis(UUID uuid) {

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

        } catch(SQLException e) {
            //TODO: Handle exception
        }

    }

    public static void resetTardis(Tardis t) {

        Database.resetTardis(t.getOwner());

    }

    public static void updateTardis(Tardis t) {

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

        } catch(SQLException e) {
            //TODO: Handle exception
        }

        //TODO: Per value update

    }

}
