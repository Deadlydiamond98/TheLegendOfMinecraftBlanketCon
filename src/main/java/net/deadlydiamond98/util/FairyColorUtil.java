package net.deadlydiamond98.util;

import java.util.List;

public class FairyColorUtil {

    //Moved Fairy Colors here due to frequency of use!

    //null never occurs in naturally. It only exists because the first fairy color when spawned by a bottle sometimes is the wrong color
    //This is a band-aid solution and I have no clue why this happens
    public static final List<String> colors = List.of("null", "purple", "blue", "yellow", "green", "pink", "red");
}
