package com.schlock.website.components.codejam.may2012;

import com.schlock.website.codejam.may2012.model.DayOption;
import com.schlock.website.codejam.may2012.model.DecisionController;
import com.schlock.website.codejam.may2012.model.DecisionOption;
import com.schlock.website.codejam.may2012.model.TimeOption;
import com.schlock.website.codejam.may2012.services.DecisionManagement;
import com.schlock.website.pages.codejam.may2012.Index;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

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
    private int currentIndex;

    @Inject
    private Messages messages;

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
        String key =
                day.name().toLowerCase() +
                "-" +
                time.name().toLowerCase() +
                "-" +
                "introduction";

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

    Object onMakeDecision(DayOption day, TimeOption time, DecisionOption decision)
    {
        decisionManagement.makeDecision(day, time, decision);

        return indexPage.getPageZone();
    }

    public boolean isDecisionMade()
    {
        if (TimeOption.DREAM.equals(time))
        {
            return true;
        }

        DecisionOption decision = decisionManagement.getDecision(day, time);

        return decision != null;
    }

    public String getDecisionResult()
    {
        DecisionOption decision = decisionManagement.getDecision(day, time);

        String key =
                day.name().toLowerCase() +
                        "-" +
                        time.name().toLowerCase() +
                        "-" +
                        decision.name().toLowerCase();

        return messages.get(key);
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
