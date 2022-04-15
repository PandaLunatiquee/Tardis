package fr.pandalunatique.tardisplugin.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Location;
import org.bukkit.Material;
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

}
