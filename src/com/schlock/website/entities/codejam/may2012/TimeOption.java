package com.schlock.website.entities.codejam.may2012;

import java.util.Arrays;
import java.util.List;

public enum TimeOption
{
    DAY,
    EVENING,
    NIGHT,
    DREAM;




    public static List<TimeOption> values(DayOption day)
    {
        if (DayOption.SUNDAY.equals(day))
        {
            return Arrays.asList(DREAM);
        }
        return Arrays.asList(values());
    }

    public static TimeOption previous(DayOption day, TimeOption time)
    {
        List<TimeOption> values = values(day);
        int previous = values.indexOf(time) -1;

        if (previous == -1)
        {
            return null;
        }
        return values.get(previous);
    }

    public static TimeOption next(DayOption day, TimeOption time)
    {
        List<TimeOption> values = values(day);
        int next = values.indexOf(time) +1;

        if (next == values.size())
        {
            return null;
        }
        return values.get(next);
    }
}
