package com.schlock.website.codejam.may2012.services;

import com.schlock.website.codejam.may2012.model.DayOption;
import com.schlock.website.codejam.may2012.model.DecisionOption;
import com.schlock.website.codejam.may2012.model.TimeOption;

import java.util.List;

public interface DecisionManagement
{
    boolean isAvailable(DayOption day);

    boolean isAvailable(DayOption day, TimeOption time);

    boolean isAvailable(DayOption day, TimeOption time, DecisionOption decisions);

    List<DecisionOption> getDecisions(DayOption day, TimeOption time);
    
    DecisionOption getDecision(DayOption day, TimeOption time);
    
    void makeDecision(DayOption day, TimeOption time, DecisionOption decision);
    
    boolean isGameOver(DayOption day, TimeOption time);
}
