package fr.pandalunatique.tardis.tardis;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class Tardis {

    private Player owner;

    private Map<Player, TardisRole> tardisRoles;
    private Set<Player> visitors; // List of players currently visiting tardis
    private Map<Player, String> banList;

    private boolean isPublic;
    private boolean isDoorOpen;

    private TardisSkin tardisSkin;

    public Tardis(Player p) {

    }

}
