package com.schlock.website.codejam.may2012.model;

import java.util.HashMap;
import java.util.Map;

public class DecisionController
{
    private DayOption currentDay;
    private TimeOption currentTime;

    private Map<String, DecisionOption> decisions;
    private Map<DayOption, SwitchOption> switches;

    public DecisionController()
    {
        reset();
    }

    public void reset()
    {
        currentDay = DayOption.SUNDAY;
        currentTime = TimeOption.DREAM;

        decisions = new HashMap<String, DecisionOption>();

        switches = new HashMap<DayOption, SwitchOption>();
        switches.put(DayOption.TUESDAY, SwitchOption.WINTER);
        switches.put(DayOption.WEDNESDAY, SwitchOption.MALE);
    }

    public boolean isRecorded(DayOption day)
    {
        //day >= currentDay

        return currentDay.compareTo(day) >= 0;
    }

    public boolean isRecorded(DayOption day, TimeOption time)
    {
        if (currentDay.equals(day))
        {
            return currentTime.compareTo(time) >= 0;
        }
        return currentDay.compareTo(day) > 0;
    }


    public void completeDay(DayOption day, TimeOption time)
    {
        currentTime = TimeOption.next(day, time);
        if (currentTime == null)
        {
            currentTime = TimeOption.DAY;
            currentDay = DayOption.next(day);
        }
    }

    public DecisionOption getDecision(DayOption day, TimeOption time)
    {
        String key = key(day, time);

        return decisions.get(key);
    }

    public void makeDecision(DayOption day, TimeOption time, DecisionOption decision)
    {
        String key = key(day, time);

        decisions.put(key, decision);
    }

    private String key(DayOption day, TimeOption time)
    {
        return day.name() + time.name();
    }

    public SwitchOption getSwitch(DayOption day)
    {
        return switches.get(day);
    }

    public void makeSwitch(DayOption day, SwitchOption switchOption)
    {
        switches.put(day, switchOption);
    }
}
