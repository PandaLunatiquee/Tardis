package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.Database;
import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.player.TardisPlayerRegistry;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionHandleListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        TardisPlayer tardisPlayer;
        if(!Database.playerExists(p.getUniqueId())) {

            tardisPlayer = new TardisPlayer(p.getUniqueId());
            Database.addPlayer(tardisPlayer);

            p.sendMessage("You don't exist so you've been created!"); //REMOVE

        } else {
            tardisPlayer = Database.getPlayer(p.getUniqueId());
            p.sendMessage("I know you buddy!"); //REMOVE

            if(Database.hasTardis(tardisPlayer)) {

                Tardis tardis = Database.getTardis(tardisPlayer);
                TardisRegistry.getRegistry().registerTardis(tardis);
                p.sendMessage("I see you're a man of culture");

            } else {
                p.sendMessage("You don't have a tardis but now u doo!");
                Tardis tardis = new Tardis(tardisPlayer);
                Database.addTardis(tardis);
            }

        }

        TardisPlayerRegistry.getRegistry().registerPlayer(tardisPlayer);

    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent e) {

        TardisRegistry.getRegistry().unregisterTardis(e.getPlayer().getUniqueId());
        TardisPlayerRegistry.getRegistry().unregisterPlayer(e.getPlayer().getUniqueId());

    }


}
