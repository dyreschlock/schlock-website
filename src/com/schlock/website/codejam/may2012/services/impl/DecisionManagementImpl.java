package com.schlock.website.codejam.may2012.services.impl;

import com.schlock.website.codejam.may2012.model.DayOption;
import com.schlock.website.codejam.may2012.model.DecisionController;
import com.schlock.website.codejam.may2012.model.DecisionOption;
import com.schlock.website.codejam.may2012.model.TimeOption;
import com.schlock.website.codejam.may2012.services.DecisionManagement;
import org.apache.tapestry5.services.ApplicationStateManager;

import java.util.ArrayList;
import java.util.List;

public class DecisionManagementImpl implements DecisionManagement
{
    private final ApplicationStateManager applicationStateManager;

    public DecisionManagementImpl(ApplicationStateManager applicationStateManager)
    {
        this.applicationStateManager = applicationStateManager;
    }

    private DecisionController getController()
    {
        return applicationStateManager.get(DecisionController.class);
    }

    public boolean isAvailable(DayOption day)
    {
        return getController().isRecorded(day);
    }

    public boolean isAvailable(DayOption day, TimeOption time)
    {
        if (!isAvailable(day))
        {
            return false;
        }
        List<TimeOption> times = TimeOption.values(day);
        if (!times.contains(time))
        {
            return false;
        }
        return getController().isRecorded(day, time);
    }

    public boolean isAvailable(DayOption day, TimeOption time, DecisionOption decision)
    {
        if (!isAvailable(day, time))
        {
            return false;
        }

        List<DecisionOption> decisions = DecisionOption.values(time);
        if (!decisions.contains(decision))
        {
            return false;
        }

        if (DecisionOption.SICK.equals(decision))
        {
            DayOption previous = DayOption.previous(day);
            if(previous == null)
            {
                return false;
            }

            DecisionOption lastNight = getDecision(previous, TimeOption.NIGHT);

            if (DecisionOption.BAR.equals(lastNight) ||
                DecisionOption.CLUB.equals(lastNight))
            {
                return true;
            }
            return false;
        }
        if (DecisionOption.CLUB.equals(decision))
        {
            if (DayOption.MONDAY.equals(day))
            {
                return false;
            }
            return true;
        }


        return true;
    }
    
    public List<DecisionOption> getDecisions(DayOption day, TimeOption time)
    {
        List<DecisionOption> decisions = new ArrayList<DecisionOption>();

        List<DecisionOption> options = DecisionOption.values(time);
        for (DecisionOption option : options)
        {
            if (DecisionOption.HOME.equals(option))
            {
                if (!DayOption.MONDAY.equals(day))
                {
                    decisions.add(option);
                }
            }
            else if (DecisionOption.PARTY.equals(option))
            {
                DecisionOption wedNight = getDecision(DayOption.WEDNESDAY, TimeOption.NIGHT);
                if (DecisionOption.CLUB.equals(wedNight) &&
                        DayOption.FRIDAY.equals(day))
                {
                    decisions.add(option);
                }
            }
            else
            {
                decisions.add(option);
            }
        }
        return decisions;
    }

    public DecisionOption getDecision(DayOption day, TimeOption time)
    {
        return getController().getDecision(day, time);
    }

    public void makeDecision(DayOption day, TimeOption time, DecisionOption decision)
    {
        getController().makeDecision(day, time, decision);
    }

    public boolean isGameOver(DayOption day, TimeOption time)
    {
        if (DayOption.MONDAY.equals(day) && TimeOption.DREAM.equals(time))
        {
            DecisionOption monEvening = getDecision(DayOption.MONDAY, TimeOption.EVENING);
            if(monEvening == null)
            {
                return true;
            }
            if (!DecisionOption.GROCERY_STORE.equals(monEvening))
            {
                return true;
            }

        }
        if (DayOption.WEDNESDAY.equals(day) && TimeOption.DREAM.equals(time))
        {
            DecisionOption tueEvening = getDecision(DayOption.TUESDAY, TimeOption.EVENING);
            if(tueEvening == null)
            {
                return true;
            }
            if (!DecisionOption.MUSIC_STORE.equals(tueEvening))
            {
                return true;
            }
        }

        return false;
    }
}
