package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.Database;
import fr.pandalunatique.tardisplugin.player.TardisPlayer;
import fr.pandalunatique.tardisplugin.player.TardisPlayerRegistry;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import fr.pandalunatique.tardisplugin.tardis.TardisRegistry;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.xml.crypto.Data;

public class ConnectionHandleListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        p.setResourcePack("https://www.dropbox.com/s/ypi72utybchjuf5/pack.zip?dl=1", DigestUtils.sha1("force"));

        TardisPlayer tardisPlayer = Database.getPlayer(p.getUniqueId());
        if(tardisPlayer == null) {

            tardisPlayer = new TardisPlayer(p);
            Database.addPlayer(tardisPlayer);

            p.sendMessage("[DEBUG] Database@PlayerAdded: You've been registered in database!"); //REMOVE

        } else {

            p.sendMessage("[DEBUG] Database@PlayerLoaded: You've been loaded from database!"); //REMOVE

            Tardis tardis = Database.getTardis(tardisPlayer);
            if(tardis != null) {

                tardis.dump();

                TardisRegistry.getRegistry().registerTardis(tardis);
                p.sendMessage("[DEBUG] Database@TardisLoaded: Your tardis has been loaded from database!");

            }

        }

        TardisPlayerRegistry.getRegistry().registerPlayer(tardisPlayer);

    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent e) {

        TardisPlayer tp = TardisPlayerRegistry.getRegistry().getPlayer(e.getPlayer().getUniqueId());

        Database.updatePlayer(tp);
        if(tp.getTardis() != null) {
            Database.updateTardis(tp.getTardis());
        }

        TardisRegistry.getRegistry().unregisterTardis(e.getPlayer().getUniqueId());
        TardisPlayerRegistry.getRegistry().unregisterPlayer(e.getPlayer().getUniqueId());

    }

}
