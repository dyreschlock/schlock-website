package com.schlock.website.components.codejam.may2012;

import com.schlock.website.entities.codejam.may2012.DayOption;
import com.schlock.website.entities.codejam.may2012.TimeOption;
import com.schlock.website.services.codejam.may2012.DecisionManagement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class DayOfWeek
{
    @Parameter(required = true)
    @Property
    private DayOption day;

    @Property
    private TimeOption currentTime;

    @Inject
    private Messages messages;

    @Inject
    private DecisionManagement decisionManagement;


    public boolean isAvailable()
    {
        return decisionManagement.isAvailable(day);
    }

    public boolean isFinale()
    {
        return DayOption.FRIDAY.equals(day) && TimeOption.DREAM.equals(currentTime);
    }

    public String getDayName()
    {
        return messages.get(day.name());
    }

    public List<TimeOption> getTimes()
    {
        return TimeOption.values(day);
    }
}
