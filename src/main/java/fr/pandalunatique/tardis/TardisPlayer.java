package fr.pandalunatique.tardis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class TardisPlayer implements Listener {

    private UUID uuid;
    private int level;
    private int experience;

    //private Set<TardisBlueprint> unlockedBlueprints;

    public TardisPlayer(UUID uuid, int lvl, int xp) {
        this.uuid = uuid;
        this.level = lvl;
        this.experience = xp;
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

//    @EventHandler
//    public void onPlayerJoinEvent(PlayerJoinEvent e) {
//        if() {}
//    }
}
