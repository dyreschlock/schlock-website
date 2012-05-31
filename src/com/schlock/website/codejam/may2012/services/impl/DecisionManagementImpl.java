package com.schlock.website.codejam.may2012.services.impl;

import com.schlock.website.codejam.may2012.model.*;
import com.schlock.website.codejam.may2012.services.DecisionManagement;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.ApplicationStateManager;

import java.util.ArrayList;
import java.util.Arrays;
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
        if (DecisionOption.MUSIC_STORE.equals(decision) ||
                DecisionOption.GROCERY_STORE.equals(decision))
        {
            if (DayOption.TUESDAY.equals(day))
            {
                SwitchOption switchOption = getController().getSwitch(day);
                if (SwitchOption.WINTER.equals(switchOption))
                {
                    return false;
                }
            }
            return true;
        }
        if (DecisionOption.FRIENDS.equals(decision))
        {
            if (DayOption.WEDNESDAY.equals(day) ||
                    DayOption.FRIDAY.equals(day))
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

    public boolean isDecisionMade(DayOption day, TimeOption time)
    {
        DecisionOption decision = getDecision(day, time);

        return decision != null;
    }

    public void makeDecision(DayOption day, TimeOption time, DecisionOption decision)
    {
        getController().makeDecision(day, time, decision);
    }

    public String getIntroductionKey(DayOption day, TimeOption time)
    {
        String key =
                day.name().toLowerCase() +
                        "-" +
                        time.name().toLowerCase() +
                        "-" +
                        "introduction";

        if ((DayOption.TUESDAY.equals(day) || DayOption.WEDNESDAY.equals(day)) &&
                (!TimeOption.DREAM.equals(time)))
        {
            SwitchOption option = getSwitch(DayOption.TUESDAY);

            key += "-" + option.name().toLowerCase();
        }
        return key;
    }
    
    public String getDecisionResult(DayOption day, TimeOption time, Messages messages)
    {
        DecisionOption decision = getDecision(day, time);

        String key =
                day.name().toLowerCase() +
                        "-" +
                        time.name().toLowerCase() +
                        "-" +
                        decision.name().toLowerCase();

        if (DecisionOption.MUSIC_STORE.equals(decision) &&
                DayOption.WEDNESDAY.equals(day))
        {
            DecisionOption tuesday = getDecision(DayOption.TUESDAY, TimeOption.EVENING);
            SwitchOption option = getSwitch(DayOption.TUESDAY);
            if (DecisionOption.MUSIC_STORE.equals(tuesday) && SwitchOption.SUMMER.equals(option))
            {
                key += "-yes";
            }
            else
            {
                key += "-no";
            }
        }

        if ((DayOption.TUESDAY.equals(day) || DayOption.WEDNESDAY.equals(day)) &&
                (!TimeOption.DREAM.equals(time)))
        {
            SwitchOption option = getSwitch(DayOption.TUESDAY);

            key += "-" + option.name().toLowerCase();
        }

        if (DecisionOption.CLUB.equals(decision))
        {
            SwitchOption option = getSwitch(DayOption.WEDNESDAY);
            String paramKey = day.name().toLowerCase() +
                    "-" +
                    time.name().toLowerCase() +
                    "-" +
                    decision.name().toLowerCase() +
                    "-" +
                    option.name().toLowerCase();

            return messages.format(key, messages.get(paramKey));
        }
        return messages.get(key);
    }

    public boolean isDecisionSuccess(DayOption day, TimeOption time)
    {
        DecisionOption decision = getDecision(day, time);

        if ((DayOption.MONDAY.equals(day) && TimeOption.EVENING.equals(time) && DecisionOption.GROCERY_STORE.equals(decision)) ||
                (DayOption.TUESDAY.equals(day) && TimeOption.EVENING.equals(time) && DecisionOption.MUSIC_STORE.equals(decision)) ||
                (DayOption.WEDNESDAY.equals(day) && TimeOption.NIGHT.equals(time) && DecisionOption.CLUB.equals(decision)) ||
                (DayOption.THURSDAY.equals(day) && TimeOption.EVENING.equals(time) && DecisionOption.FRIENDS.equals(decision)))
        {

            return true;
        }

        if (DayOption.FRIDAY.equals(day) && TimeOption.DAY.equals(day))
        {
            int daysWorked = 0;
            
            List<DayOption> days = Arrays.asList(DayOption.MONDAY, DayOption.TUESDAY, DayOption.WEDNESDAY, DayOption.THURSDAY);
            for(DayOption previousDay : days)
            {
                if (DecisionOption.WORK.equals(getDecision(previousDay, TimeOption.DAY)))
                {
                    daysWorked++;
                }
            }
            
            SwitchOption option = getSwitch(DayOption.THURSDAY);
            if (daysWorked < 3 && SwitchOption.WAXING_GIBBOUS.equals(option))
            {
                return true;
            }
        }
        
        return false;
    }

    public SwitchOption getSwitch(DayOption day)
    {
        return getController().getSwitch(day);
    }

    public boolean isSwitchMade(DayOption day, TimeOption time)
    {
        if (TimeOption.DREAM.equals(time))
        {
            if (DayOption.SUNDAY.equals(day) ||
                    DayOption.MONDAY.equals(day))
            {
                return true;
            }

            SwitchOption option = getController().getSwitch(day);
            return option != null;
        }
        return false;
    }
    
    public void makeSwitch(DayOption day, SwitchOption switchOption)
    {
        getController().makeSwitch(day, switchOption);
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
