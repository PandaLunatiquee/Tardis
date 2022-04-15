package fr.pandalunatique.tardisplugin.event;

import fr.pandalunatique.tardisplugin.tardis.tool.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TemporaryEvents implements Listener {

    final private static Location pos = new Location(Bukkit.getWorld("world"), 55, 69, 0);
    final private static Location holo = new Location(Bukkit.getWorld("world"), 32.5, 65, -4.5);

    @EventHandler
    public void onPlayerClick(final PlayerInteractEvent e) {
        try {
            if (e.getItem().getType().equals(Material.BLAZE_ROD)) {
                new Scanner().scanZone(pos, holo, 8);

            }
        } catch (final NullPointerException ignored) {
        }
    }

}
