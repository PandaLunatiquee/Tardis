package fr.pandalunatique.tardisplugin.player;

import fr.pandalunatique.tardisplugin.Database;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class TardisPlayer implements Listener {

    @Getter private final UUID uuid;
    @Getter @Setter private int level;
    @Getter @Setter private int experience;

    private Tardis tardis;

    public TardisPlayer(Player p) {
        this(p.getUniqueId(), 0, 0, null);
    }

    public TardisPlayer(UUID uuid) {
        this(uuid, 0, 0, null);
    }

    public TardisPlayer(UUID uuid, int level, int experience) {
        this(uuid, level, experience, null);
    }

    public TardisPlayer(UUID uuid, int level, int experience, Tardis tardis) {
        this.uuid = uuid;
        this.level = level;
        this.experience = experience;
        this.tardis = tardis;
    }

    public static TardisPlayer fromDatabase(UUID uuid) {

        return Database.getInstance().getPlayer(uuid);

    }

    public Player getPlayer() {

        return Bukkit.getServer().getPlayer(this.uuid);

    }

    public boolean hasTardis() {
        return this.tardis != null;
    }

    public Tardis getTardis() {
        return this.tardis;
    }

    public void setTardis(Tardis tardis) {
        this.tardis = tardis;
    }

}
