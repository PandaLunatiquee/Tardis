package fr.pandalunatique.tardisplugin;

import fr.pandalunatique.tardisplugin.item.TardisItem;
import fr.pandalunatique.tardisplugin.tardis.Tardis;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

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
