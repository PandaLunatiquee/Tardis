package fr.pandalunatique.tardisplugin.entity;

import org.bukkit.entity.Cod;
import org.bukkit.entity.EntityType;

import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;

public class EntityEnum {


    public enum Aggressive {

        BLAZE(0, EntityType.BAT),
        CREEPER(1, EntityType.CREEPER),
        DROWNED(2, EntityType.DROWNED),
        ELDER_GUARDIAN(3, EntityType.ELDER_GUARDIAN),
        ENDERMITE(4, EntityType.ENDERMITE),
        EVOKER(5, EntityType.EVOKER),
        GHAST(6, EntityType.GHAST),
        GUARDIAN(7, EntityType.GUARDIAN),
        HOGLIN(8, EntityType.HOGLIN),
        HUSK(9, EntityType.HUSK),
        MAGMA_CUBE(10, EntityType.MAGMA_CUBE),
        PHANTOM(11, EntityType.PHANTOM),
        PIGLIN_BRUTE(12, EntityType.PIGLIN_BRUTE),
        PILLAGER(13, EntityType.PILLAGER),
        RAVAGER(14, EntityType.RAVAGER),
        SHULKER(15, EntityType.SHULKER),
        SILVERFISH(16, EntityType.SILVERFISH),
        SKELETON(17, EntityType.SKELETON),
        SLIME(18, EntityType.SLIME),
        STRAY(19, EntityType.STRAY),
        VEX(20, EntityType.VEX),
        VINDICATOR(21, EntityType.VINDICATOR),
        WITCH(22, EntityType.WITCH),
        WITHER_SKELETON(23, EntityType.WITHER_SKELETON),
        ZOMBIFIED_PIGLIN(24, EntityType.ZOMBIFIED_PIGLIN),
        ZOMBIE(25, EntityType.ZOMBIE),
        ZOMBIE_VILLAGER(26, EntityType.ZOMBIE_VILLAGER);

        private int bit;
        private EntityType type;

        Aggressive(int bit, EntityType type) {
            this.bit = bit;
            this.type = type;
        }

        public int getBit() {
            return bit;
        }

        public static Aggressive fromBit(int bit) {
            for (Aggressive a : values()) {
                if (a.getBit() == bit) {
                    return a;
                }
            }
            return null;
        }

    }

    public enum Neutral {

        BEE(0, EntityType.BAT),
        CAVE_SPIDER(1, EntityType.CAVE_SPIDER),
        DOLPHIN(2, EntityType.DOLPHIN),
        ENDERMAN(3, EntityType.ENDERMAN),
        IRON_GOLEM(4, EntityType.IRON_GOLEM),
        LLAMA(5, EntityType.LLAMA),
        PANDA(6, EntityType.PANDA),
        PIGLIN(7, EntityType.PIGLIN),
        POLAR_BEAR(8, EntityType.POLAR_BEAR),
        SPIDER(9, EntityType.SPIDER),
        WOLF(10, EntityType.WOLF),
        ZOMBIFIED_PIGLIN(11, EntityType.ZOMBIFIED_PIGLIN);

        private int bit;
        private EntityType type;

        Neutral(int bit, EntityType type) {
            this.bit = bit;
            this.type = type;
        }

        public int getBit() {
            return this.bit;
        }

        public static Neutral fromBit(int bit) {
            for (Neutral n : Neutral.values()) {
                if (n.getBit() == bit) {
                    return n;
                }
            }
            return null;
        }

    }

    public enum Passive {

        BAT(0, EntityType.BAT),
        CAT(1, EntityType.CAT),
        CHICKEN(2, EntityType.CHICKEN),
        COD(3, EntityType.COD),
        COW(4, EntityType.COW),
        DONKEY(5, EntityType.DONKEY),
        FOX(6, EntityType.FOX),
        HORSE(7, EntityType.HORSE),
        MUSHROOM_COW(8, EntityType.MUSHROOM_COW),
        MULE(9, EntityType.MULE),
        OCELOT(10, EntityType.OCELOT),
        PARROT(11, EntityType.PARROT),
        PIG(12, EntityType.PIG),
        PUFFERFISH(13, EntityType.PUFFERFISH),
        RABBIT(14, EntityType.RABBIT),
        SALMON(15, EntityType.SALMON),
        SHEEP(16, EntityType.SHEEP),
        SKELETON_HORSE(17, EntityType.SKELETON_HORSE),
        SNOWMAN(18, EntityType.SNOWMAN),
        SQUID(19, EntityType.SQUID),
        STRIDER(20, EntityType.STRIDER),
        TROPICAL_FISH(21, EntityType.TROPICAL_FISH),
        TURTLE(22, EntityType.TURTLE),
        VILLAGER(23, EntityType.VILLAGER);

        private int bit;
        private EntityType type;

        Passive(int bit, EntityType type) {
            this.bit = bit;
            this.type = type;
        }

        public int getBit() {
            return this.bit;
        }

        public static Passive fromBit(int bit) {
            for (Passive p : Passive.values()) {
                if (p.getBit() == bit) {
                    return p;
                }
            }
            return null;
        }

    };

}
