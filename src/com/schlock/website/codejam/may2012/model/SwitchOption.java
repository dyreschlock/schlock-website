package com.schlock.website.codejam.may2012.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SwitchOption
{
    WINTER,
    SUMMER,

    MALE,
    FEMALE,

    WAXING_CRESCENT,
    WAXING_HALF,
    WAXING_GIBBOUS,
    FULL,
    WANING_GIBBOUS,
    
    SEWERS,
    GATE,
    WALL;


    public static List<SwitchOption> values(DayOption day, TimeOption time)
    {
        if (TimeOption.DREAM.equals(time))
        {
            if (DayOption.TUESDAY.equals(day))
            {
                return Arrays.asList(WINTER, SUMMER);
            }
            if (DayOption.WEDNESDAY.equals(day))
            {
                return Arrays.asList(MALE, FEMALE);
            }
            if (DayOption.THURSDAY.equals(day))
            {
                return Arrays.asList(WAXING_CRESCENT, WAXING_HALF, WAXING_GIBBOUS, FULL, WANING_GIBBOUS);
            }
            if (DayOption.FRIDAY.equals(day))
            {
                return Arrays.asList(SEWERS, GATE, WALL);
            }
        }
        return Collections.EMPTY_LIST;
    }
}
