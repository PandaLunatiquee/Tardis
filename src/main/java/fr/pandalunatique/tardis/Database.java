package fr.pandalunatique.tardis;


import fr.pandalunatique.tardis.tardis.TardisPlayer;
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

        if (new File("./plugins/Tardis/db.db").exists()) return;

        try {

            Statement stmt1 = this.getConnection().createStatement();
            stmt1.execute("CREATE TABLE IF NOT EXISTS Tardis (Id INTEGER PRIMARY KEY AUTOINCREMENT)");
            stmt1.execute("CREATE TABLE IF NOT EXISTS Player (Id INTEGER PRIMARY KEY AUTOINCREMENT, Uuid VARCHAR(36) NOT NULL, Level INT DEFAULT 0, Experience INT DEFAULT 0)");

        } catch (SQLException e) {
            System.out.println(ChatColor.RED + "[Tardis] Unable to initialise database!");
            e.printStackTrace(); //REMOVE: This is for debug
        }

    }

    public static Set<TardisPlayer> getPlayers() {
        return Database.getPlayers(0, 20);
    }

    public static Set<TardisPlayer> getPlayers(int limit, int offset) {

        Set<TardisPlayer> set = new HashSet<>();

        try {
            Statement stmt = Database.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Player LIMIT " + limit + " OFFSET " + offset + ";");

            while (rs.next()) {
                set.add(new TardisPlayer(UUID.fromString(rs.getString("Uuid")), rs.getInt("Level"), rs.getInt("Experience")));
            }

        } catch (SQLException e) {
            System.out.println(ChatColor.RED + "[Tardis] Unable to recover players from database ");
        }

        return set;

    }

    public static TardisPlayer getPlayer(UUID uuid) {

        TardisPlayer tp = null;

        try {
            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement("SELECT * FROM Player WHERE Uuid = ?;");
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.first()) {
                tp = new TardisPlayer(UUID.fromString(rs.getString("Uuid")), rs.getInt("Level"), rs.getInt("Experience"));
            }

        } catch (SQLException e) {
            // TODO: Handle exception
        }

        return tp;

    }

    public static void removePlayer(TardisPlayer p) {

        Database.removePlayer(p.getUuid());

    }

    public static void removePlayer(UUID uuid) {
        try {

            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement("DELETE FROM Player WHERE Uuid = ?;");
            stmt.setString(1, uuid.toString());
            stmt.execute();

        } catch (SQLException e) {
            // TODO: Handle exception
        }
    }

    public static boolean playerExists(UUID uuid) {

        return Database.getPlayer(uuid) != null;

    }

    public static void addPlayer(TardisPlayer p) {

        try {

            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement("INSERT INTO Player (Uuid, Level, Experience) VALUES (?, ?, ?)");
            stmt.setString(1, p.getUuid().toString());
            stmt.setInt(2, p.getLevel());
            stmt.setInt(3, p.getExperience());
            stmt.execute();

        } catch (SQLException e) {
            // TODO: Handle exception
        }

    }

    public static void resetPlayer(TardisPlayer p) {

        try {

            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement("UPDATE Player SET Level = ?, Experience = ? WHERE Uuid = ?;");
            stmt.setInt(1, 0);
            stmt.setInt(2, 0);
            stmt.setString(3, p.getUuid().toString());
            stmt.execute();

        } catch (SQLException e) {
            // TODO: Handle exception
        }

    }

    public static void updatePlayer(TardisPlayer p) {

        try {

            PreparedStatement stmt = Database.getInstance().getConnection().prepareStatement("UPDATE Player SET Level=?, Experience=? WHERE Uuid=?;");
            stmt.setInt(1, p.getLevel());
            stmt.setInt(2, p.getExperience());
            stmt.setString(2, p.getUuid().toString());
            stmt.execute();

        } catch (SQLException e) {
            // TODO: Handle exception
        }

    }
}
