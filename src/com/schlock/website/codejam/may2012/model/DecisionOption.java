package com.schlock.website.codejam.may2012.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum DecisionOption
{
    WORK,
    SICK,

    MUSIC_STORE,
    GROCERY_STORE,
    FRIENDS,
    HOME,

    BAR,
    CLUB,
    BED,
    PARTY;




    public static List<DecisionOption> values(TimeOption time)
    {
        if(TimeOption.DAY.equals(time))
        {
            return Arrays.asList(WORK, SICK);
        }
        if (TimeOption.EVENING.equals(time))
        {
            return Arrays.asList(MUSIC_STORE, GROCERY_STORE, FRIENDS, HOME);
        }
        if (TimeOption.NIGHT.equals(time))
        {
            return Arrays.asList(BAR, CLUB, BED, PARTY);
        }
        return Collections.EMPTY_LIST;
    }
}
