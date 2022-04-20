package fr.pandalunatique.tardisplugin.tardis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Banlist {

    @Getter @Setter private Map<UUID, String> banned;

    public Banlist() {
        this(new HashMap<>());
    }

    public Banlist(Map<UUID, String> banned) {
        this.banned = banned;
    }

    public void ban(UUID uuid, String reason) {
        banned.put(uuid, reason);
    }

    public void unban(UUID uuid) {
        banned.remove(uuid);
    }

    public boolean isBanned(UUID uuid) {
        return banned.containsKey(uuid);
    }

    public String getReason(UUID uuid) {
        return banned.get(uuid);
    }

    public String serialize() {

        Gson gson = new Gson();

        return gson.toJson(this.banned);

    }

    public static Banlist deserialize(String json) {

        Gson gson = new Gson();

        Type type = new TypeToken<Map<UUID, String>>() {}.getType();

        Map<UUID, String> deserialized = gson.fromJson(json, type);

        return new Banlist(deserialized);

    }

}
