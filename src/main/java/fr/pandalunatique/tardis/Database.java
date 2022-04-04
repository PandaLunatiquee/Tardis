package fr.pandalunatique.tardis;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Set;

public class Database {

    private static Database instance;
    private Connection conn;
    private String path;

    private Database() {

        this.path = "jdbc:sqlite:plugins/Tardis/tardis.db";

        System.out.println(ChatColor.YELLOW + "[Tardis] Connecting to database...");

        if(!new File("./plugins/Tardis/db.db").exists()) {
            this.initialize();
        }

    }

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(this.path);

    }

    public void initialize() {

        try {

            Statement stmt1 = this.getConnection().createStatement();
            stmt1.execute("CREATE TABLE IF NOT EXISTS Tardis (Id INTEGER PRIMARY KEY AUTOINCREMENT)");
            stmt1.execute("CREATE TABLE IF NOT EXISTS Players (Id INTEGER PRIMARY KEY AUTOINCREMENT, Uuid VARCHAR(36) NOT NULL)");
            stmt1.execute("INSERT INTO Players (Uuid) VALUES ('111011111111111111111111111111111111')");
            this.getConnection().commit();

        } catch(SQLException e) {
            System.out.println(ChatColor.RED + "[Tardis] Unable to initialise database!");
            e.printStackTrace();
        }

    }

    public boolean canConnect() {
        try {
            this.getConnection();
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    public Set<Player> getPlayers() {
        return null;
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

}
