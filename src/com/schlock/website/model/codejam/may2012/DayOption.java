package com.schlock.website.model.codejam.may2012;

import java.util.Arrays;
import java.util.List;

public enum DayOption
{
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY;


    public static DayOption previous(DayOption day)
    {
        List<DayOption> values = Arrays.asList(values());
        int previous = values.indexOf(day) -1;

        if (previous == -1)
        {
            return null;
        }
        return values.get(previous);
    }
    
    public static DayOption next(DayOption day)
    {
        List<DayOption> values = Arrays.asList(values());
        int next = values.indexOf(day) +1;

        if (next == values.size())
        {
            return null;
        }
        return values.get(next);
    }

}
