package com.schlock.website.components.codejam.may2012;

import com.schlock.website.model.codejam.may2012.DayOption;
import com.schlock.website.model.codejam.may2012.DecisionOption;
import com.schlock.website.model.codejam.may2012.SwitchOption;
import com.schlock.website.services.codejam.may2012.DecisionManagement;
import com.schlock.website.model.codejam.may2012.TimeOption;
import com.schlock.website.pages.codejam.may2012.Index;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class Finale
{
    @Property
    private SwitchOption currentSwitch;

    @Property
    private DecisionOption currentDecision;
    
    @Property
    private DayOption day;

    @Property
    private TimeOption time;

    @Inject
    private Messages messages;
    
    @Inject
    private DecisionManagement decisionManagement;

    @InjectPage
    private Index indexPage;

    void setupRender()
    {
        day = DayOption.FRIDAY;
        time = TimeOption.DREAM;
    }

    public boolean isAvailable()
    {
        return decisionManagement.isAvailable(day, time) &&
                decisionManagement.isValid(day, time);
    }
    
    public boolean isMale()
    {
        SwitchOption male = decisionManagement.getSwitch(DayOption.WEDNESDAY);
        return SwitchOption.MALE.equals(male); 
    }
    
    public boolean isFemale()
    {
        SwitchOption female = decisionManagement.getSwitch(DayOption.WEDNESDAY);
        return SwitchOption.FEMALE.equals(female);
    }
    
    public boolean isHasFriends()
    {
        if (isMale())
        {
            DecisionOption friends = decisionManagement.getDecision(DayOption.THURSDAY, TimeOption.EVENING);
            return DecisionOption.FRIENDS.equals(friends);
        }
        return false;
    }

    public List<SwitchOption> getSwitches()
    {
        return SwitchOption.values(day, time);
    }

    public boolean isSelectedSwitch()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(day);

        return currentSwitch.equals(switchOption);
    }

    public String getSwitchText()
    {
        return messages.get(currentSwitch.name().toLowerCase());
    }
    
    Object onMakeSwitch(DayOption day, TimeOption time, SwitchOption switchOption)
    {
        decisionManagement.makeSwitch(day, switchOption);

        return indexPage.getPageZone();
    }

    public boolean isSwitchMade()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(day);

        return switchOption != null;
    }

    public String getSwitchResultText()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(day);

        return messages.get("switch-" + switchOption.name().toLowerCase());
    }
    
    public String getBarSwitchResultText()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(day);

        return messages.get("bar-switch-" + switchOption.name().toLowerCase());
    }

    public String getFightSwitchResultText()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(day);

        String character = messages.get("dream-female");
        if (isMale())
        {
            character = messages.get("dream-male");
        }

        return messages.format("fight-switch-" + switchOption.name().toLowerCase(), character);
    }
    
    public List<DecisionOption> getDecisions()
    {
        return decisionManagement.getDecisions(day, time);
    }
    
    public boolean isSelectedDecision()
    {
        DecisionOption option = decisionManagement.getDecision(day, time);

        return currentDecision.equals(option);
    }
    
    public String getDecisionText()
    {
        String character = messages.get("dream-female");
        if (isMale())
        {
            character = messages.get("dream-male");
        }
        return messages.format(currentDecision.name().toLowerCase(), character);
    }

    Object onMakeDecision(DayOption day, TimeOption time, DecisionOption option)
    {
        decisionManagement.makeDecision(day, time, option);

        return indexPage.getPageZone();
    }

    public boolean isDecisionMade()
    {
        DecisionOption option = decisionManagement.getDecision(day, time);
        
        return option != null;
    }
    
    public String getDecisionResultText()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(DayOption.FRIDAY);
        DecisionOption decision = decisionManagement.getDecision(DayOption.FRIDAY, TimeOption.DREAM);

        String character = messages.get("dream-female");
        if (isMale())
        {
            character = messages.get("dream-male");
        }

        String key = switchOption.name().toLowerCase() + "-" + decision.name().toLowerCase();

        return messages.format(key, character, character);
    }
    
    public String getBarDecisionText()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(DayOption.FRIDAY);
        DecisionOption decision = decisionManagement.getDecision(DayOption.FRIDAY, TimeOption.DREAM);

        String key = "bar-" +
                switchOption.name().toLowerCase() +
                "-" +
                decision.name().toLowerCase();

        return messages.get(key);
    }
    
    public String getGenderDecisionText()
    {
        SwitchOption switchOption = decisionManagement.getSwitch(DayOption.WEDNESDAY);
        DecisionOption decision = decisionManagement.getDecision(DayOption.FRIDAY, TimeOption.DREAM);

        String key = "bar-" +
                decision.name().toLowerCase();

        if (isHasFriends())
        {
            key += "-friends";
        }
        
        key += "-" + switchOption.name().toLowerCase();

        return messages.get(key);
    }

    

    public String getPageZoneId()
    {
        return indexPage.getPageZone().getClientId();
    }
}
