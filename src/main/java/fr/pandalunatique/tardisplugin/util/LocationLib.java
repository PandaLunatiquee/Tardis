package fr.pandalunatique.tardisplugin.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;
import java.util.Map;

public class LocationLib {

    public static String locationToJson(Location location) {

        Gson gson = new Gson();
        Map<String, Object> map = location.serialize();
        return gson.toJson(map);

        //TODO: Check for exceptions

    }

    public static Location jsonToLocation(String json) {

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Location location = Location.deserialize(gson.fromJson(json, type));

        //TODO: Check for exceptions

        return location;

    }

    public static Location reachSurfaceFromTop(Location location) {

        Location l = location.clone();
        l.setY(255);

        while(l.getY() >= 0) {

            if(!l.getBlock().getType().equals(Material.AIR)) {
                l.setY(l.getY() + 1);
                return l;
            }
            l.setY(l.getY() - 1);
        }

        return null;

    }

    public static boolean checkFreeSpace(Location center, int dx, int dz, int dy) {

        Location check = center.getBlock().getLocation();

        for(int y = 0; y < dy; y++) {

            check.setY(center.getY() + y);

            for(int x = 0; x <= dx; x++) {

                check.setX(center.getX() - ((double) dx / 2 - x));

                for(int z = 0; z <= dz; z++) {

                    check.setZ(center.getZ() - ((double) dz / 2 - z));

                    if(!check.getBlock().getType().equals(Material.AIR)) return false;

                }

            }

        }

        return true;

    }

}
