package com.schlock.website.services.codejam.may2012;

import com.schlock.website.model.codejam.may2012.DayOption;
import com.schlock.website.model.codejam.may2012.DecisionOption;
import com.schlock.website.model.codejam.may2012.SwitchOption;
import com.schlock.website.model.codejam.may2012.TimeOption;
import org.apache.tapestry5.ioc.Messages;

import java.util.List;

public interface DecisionManagement
{
    boolean isAvailable(DayOption day);

    boolean isAvailable(DayOption day, TimeOption time);

    boolean isAvailable(DayOption day, TimeOption time, DecisionOption decisions);

    boolean isValid(DayOption day, TimeOption time);

    boolean isCurrent(DayOption day, TimeOption time);

    List<DecisionOption> getDecisions(DayOption day, TimeOption time);
    
    DecisionOption getDecision(DayOption day, TimeOption time);

    boolean isDecisionMade(DayOption day, TimeOption time);

    void makeDecision(DayOption day, TimeOption time, DecisionOption decision);

    String getIntroductionKey(DayOption day, TimeOption time);

    String getDecisionResult(DayOption day, TimeOption time, Messages messages);
    
    boolean isDecisionSuccess(DayOption day, TimeOption time);
    
    SwitchOption getSwitch(DayOption day);

    boolean isSwitchMade(DayOption day, TimeOption time);

    void makeSwitch(DayOption day, SwitchOption switchOption);
    
    String getSwitchResults(DayOption day, Messages messages);
    
    boolean isGameOver(DayOption day, TimeOption time);
}
