package fr.pandalunatique.tardisplugin.player.tool.forcefield;

import fr.pandalunatique.tardisplugin.item.TardisItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ForceField implements Listener {

    public static HashMap<Location, Object> shieldsList = new HashMap<>();
    public static HashMap<Player, Object> inventoryAccess = new HashMap<>();

    public static Set<Location> getShieldField() {
        return shieldsList.k
    }

}
