package com.schlock.website.components.codejam.may2012;

import com.schlock.website.codejam.may2012.model.DayOption;
import com.schlock.website.codejam.may2012.model.TimeOption;
import com.schlock.website.codejam.may2012.services.DecisionManagement;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Finale
{

    private DayOption day;
    private TimeOption time;

    @Inject
    private DecisionManagement decisionManagement;

    void setupRender()
    {
        day = DayOption.FRIDAY;
        time = TimeOption.DREAM;
    }

    public boolean isAvailable()
    {
        return decisionManagement.isAvailable(day, time);
    }
}
