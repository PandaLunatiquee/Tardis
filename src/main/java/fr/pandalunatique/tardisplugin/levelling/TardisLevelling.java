package fr.pandalunatique.tardisplugin.levelling;

public class TardisLevelling {

    public static int levelFromExperience(int experience) {

        return (int) Math.floor(Math.sqrt((float) experience / 100));

    }

    public static int minimumExperienceFromLevel(int level) {

        return (int) 100 * level * level;

    }

}
