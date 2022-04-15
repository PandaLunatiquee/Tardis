package fr.pandalunatique.tardisplugin.util;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import java.util.Random;

public class ChanceLib {

    public static boolean chanceOutOf(int chance, int oufOf) {

        Random r = new Random();
        int value = r.nextInt(oufOf + 1);
        return value < chance;

    }

    public static boolean chanceOutOf(int chance) {

        return chanceOutOf(chance, 100);

    }

    public static int randomInteger(int min, int max) {

        Random r = new Random();

        return r.nextInt(max - min) + min;

    }

}
