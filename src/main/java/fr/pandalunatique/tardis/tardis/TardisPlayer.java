package fr.pandalunatique.tardis.tardis;

import fr.pandalunatique.tardis.Database;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.util.UUID;

@RequiredArgsConstructor
public class TardisPlayer implements Listener {

    private final UUID uuid;
    private final int level;
    private final int experience;

    //private Set<TardisBlueprint> unlockedBlueprints;

    public static TardisPlayer fromDatabase(UUID uuid) {

        return Database.getInstance().getPlayer(uuid);

    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExperience() {
        return this.experience;
    }

    public Player getPlayer() {

        return Bukkit.getServer().getPlayer(this.uuid);

    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {



    }
}
