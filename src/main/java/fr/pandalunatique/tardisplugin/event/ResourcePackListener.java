package fr.pandalunatique.tardisplugin.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourcePackListener implements Listener {

    @EventHandler
    public void onResourcePackEvent(PlayerResourcePackStatusEvent e) {

        if(e.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {

            e.getPlayer().sendMessage(ChatColor.RED + "We're sorry, you need to accept the resource pack!");

        }

    }


}
