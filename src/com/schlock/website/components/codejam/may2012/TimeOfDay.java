package com.schlock.website.components.codejam.may2012;

import com.schlock.website.codejam.may2012.model.*;
import com.schlock.website.codejam.may2012.services.DecisionManagement;
import com.schlock.website.pages.codejam.may2012.Index;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

import java.util.List;

public class TimeOfDay
{
    @Parameter(required = true)
    @Property
    private DayOption day;
    
    @Parameter(required = true)
    @Property
    private TimeOption time;

    @Property
    private DecisionOption currentDecision;
    
    @Property
    private SwitchOption currentSwitch;
    
    @Property
    private int currentIndex;

    @Inject
    private Messages messages;

    @Inject
    private AssetSource assetSource;
    
    @Inject
    private DecisionManagement decisionManagement;

    @InjectPage
    private Index indexPage;

    @SessionState
    private DecisionController controller;


    public boolean isAvailable()
    {
        return decisionManagement.isAvailable(day, time);
    }

    public String getTimeName()
    {
        return messages.get(time.name());
    }

    public boolean isDream()
    {
        return TimeOption.DREAM.equals(time);
    }

    public String getCssClass()
    {
        if (TimeOption.DREAM.equals(time))
        {
            return "dream";
        }
        return "";
    }
    
    public String getIntroduction()
    {
        String key = decisionManagement.getIntroductionKey(day, time);
        return messages.get(key);
    }

    public boolean isHasDecisions()
    {
        return !getDecisions().isEmpty();
    }

    public List<DecisionOption> getDecisions()
    {
        return decisionManagement.getDecisions(day, time);
    }

    public String getDecisionText()
    {
        return (currentIndex + 1) + ". " + messages.get(currentDecision.name());
    }

    public String getUnavailableText()
    {
        return messages.get("unavailable-" + currentDecision.name());
    }
    
    public boolean isDecisionAvailable()
    {
        return decisionManagement.isAvailable(day, time, currentDecision);
    }

    public boolean isSelectedDecision()
    {
        DecisionOption decision = decisionManagement.getDecision(day, time);

        return currentDecision.equals(decision);
    }

    Object onMakeDecision(DayOption day, TimeOption time, DecisionOption decision)
    {
        decisionManagement.makeDecision(day, time, decision);

        return indexPage.getPageZone();
    }

    public boolean isDecisionMade()
    {
        return decisionManagement.isDecisionMade(day, time);
    }

    public String getDecisionResult()
    {
        return decisionManagement.getDecisionResult(day, time, messages);
    }

    public boolean isDecisionSuccess()
    {
        return decisionManagement.isDecisionSuccess(day, time);
    }
    
    public String getDecisionSuccessImageUrl()
    {
        String context = "context:images/codejam/" + day.name().toLowerCase() + ".png";
        return assetSource.getAsset(null, context, null).toClientURL();
    }
    
    public boolean isHasSwitches()
    {
        return !getSwitches().isEmpty();
    }

    public List<SwitchOption> getSwitches()
    {
        return SwitchOption.values(day, time);
    }
    
    public String getSwitchText()
    {
        return messages.get(currentSwitch.name().toLowerCase());
    }

    public boolean isSelectedSwitch()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(day);

        return currentSwitch.equals(switchOption);
    }

    Object onMakeSwitch(DayOption day, TimeOption time, SwitchOption switchOption)
    {
        decisionManagement.makeSwitch(day, switchOption);

        return indexPage.getPageZone();
    }

    public boolean isSwitchMade()
    {
        return decisionManagement.isSwitchMade(day, time);
    }

    public String getSwitchResult()
    {
        return "asdf";
    }
    
    public boolean isTimeComplete()
    {
        return isDecisionMade() || isSwitchMade();
    }

    public boolean isGameOver()
    {
        return decisionManagement.isGameOver(day, time);
    }
    
    Object onContinue(DayOption day, TimeOption time)
    {
        controller.completeDay(day, time);

        return indexPage.getPageZone();
    }

    public String getPageZoneId()
    {
        return indexPage.getPageZone().getClientId();
    }
}
