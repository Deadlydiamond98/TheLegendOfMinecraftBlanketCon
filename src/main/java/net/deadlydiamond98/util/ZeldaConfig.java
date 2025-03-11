package net.deadlydiamond98.util;

import eu.midnightdust.lib.config.MidnightConfig;

public class ZeldaConfig extends MidnightConfig {
    @Comment(category = "text") public static Comment spacer1;
    @Entry(category = "text") public static boolean mobsDropShards = true;

    @Comment(category = "text") public static Comment spacer2;
    @Entry(category = "text") public static boolean hookShotAnything = false;

    @Comment(category = "text") public static Comment spacer3;
    @Entry(category = "text") public static boolean healpgoodcompat = true;

    @Comment(category = "text") public static Comment spacer4;
    @Entry(category = "text") public static boolean advancementfanfare = true;

    @Comment(category = "text") public static Comment spacer5;
    @Entry(category = "text") public static boolean doMeteorShowerWeatherEvent = true;

    @Comment(category = "text") public static Comment spacer6;
    @Entry(category = "text") public static boolean shootingStars = true;
}