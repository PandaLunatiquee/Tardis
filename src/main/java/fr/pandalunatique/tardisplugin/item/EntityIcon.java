package fr.pandalunatique.tardisplugin.item;

import org.bukkit.entity.EntityType;

public enum EntityIcon {
    BAT(EntityType.BAT, "9e99deef919db66ac2bd28d6302756ccd57c7f8b12b9dca8f41c3e0a04ac1cc"),
    CAT(EntityType.CAT, "4fd10c8e75f67398c47587d25fc146f311c053cc5d0aeab8790bce36ee88f5f8"),
    CHICKEN(EntityType.CHICKEN, "1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893"),
    COD(EntityType.COD,"7892d7dd6aadf35f86da27fb63da4edda211df96d2829f691462a4fb1cab0"),
    COW(EntityType.COW, "5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5"),
    DONKEY(EntityType.DONKEY, "399bb50d1a214c394917e25bb3f2e20698bf98ca703e4cc08b42462df309d6e6"),
    FOX(EntityType.FOX, "d8954a42e69e0881ae6d24d4281459c144a0d5a968aed35d6d3d73a3c65d26a"),
    HORSE(EntityType.HORSE, "85ce194a54315acc3bf9db7edf6e7da29f49524b1b8af0ef9e4ac3df2280b0d8"),
    MUSHROOM_COW(EntityType.MUSHROOM_COW, "2b52841f2fd589e0bc84cbabf9e1c27cb70cac98f8d6b3dd065e55a4dcb70d77"),
    MULE(EntityType.MULE, "46dcda265e57e4f51b145aacbf5b59bdc6099ffd3cce0a661b2c0065d80930d8"),
    OCELOT(EntityType.OCELOT, "5657cd5c2989ff97570fec4ddcdc6926a68a3393250c1be1f0b114a1db1"),
    PARROT(EntityType.PARROT, "5df4b3401a4d06ad66ac8b5c4d189618ae617f9c143071c8ac39a563cf4e4208"),
    PIG(EntityType.PIG, "621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4"),
    PUFFERFISH(EntityType.PUFFERFISH, "292350c9f0993ed54db2c7113936325683ffc20104a9b622aa457d37e708d931"),
    RABBIT(EntityType.RABBIT, "c1db38ef3c1a1d59f779a0cd9f9e616de0cc9acc7734b8facc36fc4ea40d0235"),
    SALMON(EntityType.SALMON, "8aeb21a25e46806ce8537fbd6668281cf176ceafe95af90e94a5fd84924878"),
    SHEEP(EntityType.SHEEP, "292df216ecd27624ac771bacfbfe006e1ed84a79e9270be0f88e9c8791d1ece4"),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, "47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a"),
    SNOWMAN(EntityType.SNOWMAN, "ee28a03ed2eb90eaff1a119a5b554452701b97af47bff73ce710849c6b0"),
    SQUID(EntityType.SQUID, "464bdc6f600656511bef596c1a16aab1d3f5dbaae8bee19d5c04de0db21ce92c"),
    STRIDER(EntityType.STRIDER, "65ccbb547820b667cc9d3bc9fff1e3d65da2375655a3427b30e1d009eeb272ce"),
    TROPICAL_FISH(EntityType.TROPICAL_FISH, "179e48d814aa3bc984e8a6fd4fb170ba0bb4893f4bbebde5fdf3f8f871cb292f"),
    TURTLE(EntityType.TURTLE, "0a4050e7aacc4539202658fdc339dd182d7e322f9fbcc4d5f99b5718a"),
    VILLAGER(EntityType.VILLAGER, "41b830eb4082acec836bc835e40a11282bb51193315f91184337e8d3555583"),
    WANDERING_TRADER(EntityType.WANDERING_TRADER, "5f1379a82290d7abe1efaabbc70710ff2ec02dd34ade386bc00c930c461cf932");

    private final EntityType type;
    private final String texture;

    private EntityIcon(EntityType type, String texture) {
        this.type = type;
        this.texture = texture;
    }

    public static String fromEntityType(EntityType type) {
        for(EntityIcon icon : values()) {
            if (icon.type.equals(type)) {
                return icon.texture;
            }
        } return null;
    }

}