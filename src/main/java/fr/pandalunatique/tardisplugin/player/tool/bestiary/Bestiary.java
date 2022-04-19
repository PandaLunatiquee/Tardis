package fr.pandalunatique.tardisplugin.player.tool.bestiary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.pandalunatique.tardisplugin.entity.EntityEnum;
import fr.pandalunatique.tardisplugin.entity.TardisEntityType;
import fr.pandalunatique.tardisplugin.util.BooleanStorableSet;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * The class Bestiary.
 *
 * @author PandaLunatique
 * @version 1.0
 * @see BooleanStorableSet
 */
public class Bestiary {

    @Getter @Setter private BooleanStorableSet<EntityEnum.Aggressive> aggressive;
    @Getter @Setter private BooleanStorableSet<EntityEnum.Neutral> neutral;
    @Getter @Setter private BooleanStorableSet<EntityEnum.Passive> passive;
    @Getter @Setter private BooleanStorableSet<TardisEntityType> custom;

    /**
     * Instantiates a new Bestiary.
     *
     * @constructor
     * @param aggressive Aggressive entities
     * @param neutral Neutral entities
     * @param passive Passive entities
     * @param custom Custom entities
     */
    public Bestiary(
            BooleanStorableSet<EntityEnum.Aggressive> aggressive,
            BooleanStorableSet<EntityEnum.Neutral> neutral,
            BooleanStorableSet<EntityEnum.Passive> passive,
            BooleanStorableSet<TardisEntityType> custom
    ) {
        this.aggressive = aggressive;
        this.neutral = neutral;
        this.passive = passive;
        this.custom = custom;
    }

    /**
     * Instantiates a new Bestiary.
     *
     * {@code aggressive, neutral, passive and custom} default to {@link BooleanStorableSet}
     * @see Bestiary#Bestiary(BooleanStorableSet, BooleanStorableSet, BooleanStorableSet, BooleanStorableSet)
     */
    public Bestiary() {
        this(new BooleanStorableSet<>(), new BooleanStorableSet<>(), new BooleanStorableSet<>(), new BooleanStorableSet<>());
    }

    public String serialize() {

        Map<String, Integer> serialized = new HashMap<>();
        Gson gson = new Gson();

//        serialized.put("aggressive", this.aggressive == null ? 0 : this.aggressive.toInteger());
//        serialized.put("neutral", this.neutral == null ? 0 : this.neutral.toInteger());
//        serialized.put("passive", this.passive == null ? 0 : this.passive.toInteger());
//        serialized.put("custom", this.custom == null ? 0 : this.custom.toInteger());

        serialized.put("aggressive", this.aggressive.toInteger());
        serialized.put("neutral", this.neutral.toInteger());
        serialized.put("passive", this.passive.toInteger());
        serialized.put("custom", this.custom.toInteger());

        return gson.toJson(serialized);

    }

    public static Bestiary deserialize(String json) {

        Gson gson = new Gson();

        Type type = new TypeToken<Map<String, Integer>>() {}.getType();

        Map<String, Integer> deserialized = gson.fromJson(json, type);

        BooleanStorableSet<EntityEnum.Aggressive> aggressive = BooleanStorableSet.fromInteger(deserialized.get("aggressive"), EntityEnum.Aggressive.class);
        BooleanStorableSet<EntityEnum.Neutral> neutral = BooleanStorableSet.fromInteger(deserialized.get("neutral"), EntityEnum.Neutral.class);
        BooleanStorableSet<EntityEnum.Passive> passive = BooleanStorableSet.fromInteger(deserialized.get("passive"), EntityEnum.Passive.class);
        BooleanStorableSet<TardisEntityType> custom = BooleanStorableSet.fromInteger(deserialized.get("custom"), TardisEntityType.class);

        return new Bestiary(aggressive,neutral, passive, custom);

    }

    public void add(EntityEnum.Aggressive entity) {
        this.aggressive.add(entity);
    }

    public void add(EntityEnum.Neutral entity) {
        this.neutral.add(entity);
    }

    public void add(EntityEnum.Passive entity) {
        this.passive.add(entity);
    }

    public void add(TardisEntityType entity) {
        this.custom.add(entity);
    }

    public void remove(EntityEnum.Aggressive entity) {
        this.aggressive.remove(entity);
    }

    public void remove(EntityEnum.Neutral entity) {
        this.neutral.remove(entity);
    }

    public void remove(EntityEnum.Passive entity) {
        this.passive.remove(entity);
    }

    public void remove(TardisEntityType entity) {
        this.custom.remove(entity);
    }

    public boolean contains(EntityEnum.Aggressive entity) {
        return this.aggressive.contains(entity);
    }

    public boolean contains(EntityEnum.Neutral entity) {
        return this.neutral.contains(entity);
    }



}
