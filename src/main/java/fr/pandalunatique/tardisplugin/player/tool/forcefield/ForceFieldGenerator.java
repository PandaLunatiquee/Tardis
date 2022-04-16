package fr.pandalunatique.tardisplugin.player.tool.forcefield;

import fr.pandalunatique.tardisplugin.TardisPlugin;
import fr.pandalunatique.tardisplugin.item.TardisItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ForceFieldGenerator {

    public BukkitTask task = null;
    public Boolean stateShield;
    public Inventory inv;
    public Location loc;
    public int artronEnergy;
    public int timer = 0;

    public void init(Location l) {
        loc = l;
        inv = Bukkit.createInventory(null, 54, "Champ de force");
        artronEnergy = 0;

        ItemStack grayGlass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta grayGlassIM = grayGlass.getItemMeta();
        grayGlassIM.setDisplayName("§1");
        grayGlass.setItemMeta(grayGlassIM);

        ItemStack whiteGlass = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta whiteGlassIM = whiteGlass.getItemMeta();
        whiteGlassIM.setDisplayName("§1");
        whiteGlass.setItemMeta(whiteGlassIM);

        for (int i = 0; i < 54; i++) {
            if (i > 9 && i < 17 || i > 18 && i < 26 || i == 28 || i == 29 || i == 33 || i == 34 || i > 36 && i < 44) {
                inv.setItem(i, whiteGlass);
            } else {
                if (!(i > 28 && i < 34)) {
                    inv.setItem(i, grayGlass);
                }
            }
        }

        setStateShield(false);

    }

    public void setStateShield(boolean b) {

        ItemStack redState = new ItemStack(Material.RED_WOOL);
        ItemMeta redStateIM = redState.getItemMeta();
        redStateIM.setDisplayName("Désactiver");
        redState.setItemMeta(redStateIM);

        ItemStack greenState = new ItemStack(Material.GREEN_WOOL);
        ItemMeta greenStateIM = greenState.getItemMeta();
        greenStateIM.setDisplayName("Activer");
        greenState.setItemMeta(greenStateIM);

        if (b) {
            getArtronEnergy();
            if (artronEnergy > 0) {
                inv.setItem(13, redState);
                stateShield = true;
                removeArtronBottle();
                start(7);
            }
        } else {
            inv.setItem(13, greenState);
            stateShield = false;
            if (task != null) {
                stop();
            }
        }
    }

    public void start(int coef) {

        if (artronEnergy > 0) {

            List<Location> locs = generateSphere(loc, coef);
            List<Location> locs2 = drawLine(loc, loc.clone().add(0, coef, 0), 0.5);
            locs.addAll(locs2);

            for (Location l : locs2) {
                Bukkit.getScheduler().runTaskLater(TardisPlugin.getInstance(), () -> {
                    loc.getWorld().spawnParticle(Particle.REVERSE_PORTAL, l, 1, 0, 0, 0, 0.01);
                }, 10);
            }

            task = Bukkit.getServer().getScheduler().runTaskTimer(TardisPlugin.getInstance(), () -> {

                getArtronEnergy();

                if (artronEnergy == 0) setStateShield(false);
                if (timer == 1200) {
                    timer = 0;
                    removeArtronBottle();
                }

                for (Location l : locs) {
                    loc.getWorld().spawnParticle(Particle.REVERSE_PORTAL, l, 1, 0, 0, 0, 0.01);
                }

                for (Entity e : loc.getWorld().getEntities()) {
                    if (loc.distance(e.getLocation()) <= coef + 1) {
                        if (!(loc.distance(e.getLocation()) <= coef)) {
                            Vector v = e.getLocation().toVector().subtract(loc.clone().toVector()).normalize();
                            e.setVelocity(v);
                            if (e instanceof Damageable) {
                                ((Damageable) e).damage(0.5);
                            }
                        }
                    }
                }

                timer++;

            }, 0, 1);
        }

    }

    public void stop() {
        task.cancel();
        stateShield = false;
    }

    private List<Location> generateSphere(Location loc, int coef) {

        List<Location> locs = new ArrayList<>();
        Location location = loc.clone();

        for (double i = 0; i <= Math.PI; i += Math.PI / 20) {
            double radius = Math.sin(i);
            double y = Math.cos(i) * coef;
            for (double a = 0; a < Math.PI * 2; a += Math.PI / 20) {
                double x = Math.cos(a) * radius * coef;
                double z = Math.sin(a) * radius * coef;
                location.add(x, y, z);
                locs.add(location.clone());
                location.subtract(x, y, z);
            }
        }

        return locs;

    }

    private void getArtronEnergy() {
        int energy = 0;
        for (int i = 32; i > 29; i--) {
            ItemStack is = inv.getItem(i);
            if (is != null) {
                int a = is.getAmount();
                is.setAmount(1);
                if (is.equals(TardisItem.ARTRON_BOTTLE.getItemStack())) {
                    is.setAmount(a);
                    energy += a;
                }
            }
        }
        artronEnergy = energy;
    }

    private void removeArtronBottle() {
        for (int i = 32; i > 29; i--) {
            ItemStack is = inv.getItem(i);
            if (is != null) {
                int a = is.getAmount();
                is.setAmount(1);
                if (is.equals(TardisItem.ARTRON_BOTTLE.getItemStack())) {
                    a--;
                    is.setAmount(a);
                    inv.setItem(i, is);
                    break;
                }
            }
        }
    }

    private List<Location> drawLine(Location p1, Location p2, double space) {
        List<Location> locs = new ArrayList<>();
        double distance = p1.distance(p2);
        Vector v1 = p1.toVector();
        Vector v2 = p2.toVector();
        Vector vector = v2.clone().subtract(v1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; v1.add(vector)) {
            locs.add(new Location(p1.getWorld(), v1.getX(), v1.getY(), v1.getZ()));
            length += space;
        }
        return locs;
    }

}