package fr.pandalunatique.tardisplugin;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.schematic.SchematicManager;
import fr.pandalunatique.tardisplugin.tardis.TardisFacing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class TemporaryGiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {

            Player p = (Player) commandSender;

            if(strings.length == 1) {

                TardisItem item = null;
                for(TardisItem i : TardisItem.values()) {

                    if(i.name().equalsIgnoreCase(strings[0])) {
                        item = i;
                        break;
                    }

                }

                if(item != null) {
                    p.getInventory().addItem(item.getItemStack());
                } else {
                    p.sendMessage(ChatColor.RED + "Invalid enum name!");
                }

            } else {

                p.sendMessage(ChatColor.RED + "Invalid command! Use /tgive ENUM_NAME");

            }

        }

        return true;
    }
}
