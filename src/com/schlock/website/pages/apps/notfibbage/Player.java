package com.schlock.website.pages.apps.notfibbage;

import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Player
{
    @Inject
    private NotFibbageManagement management;

    @Inject
    private NotFibbageController controller;

    @Inject
    private Messages messages;


    @Property
    private String playerName;


    Object onActivate()
    {
        if (this.playerName != null)
        {
            boolean registered = management.isRegisteredPlayer(this.playerName);
            if (!registered)
            {
                this.playerName = null;
            }
        }
        return true;
    }

    Object onActivate(String parameter)
    {
        boolean registered = management.isRegisteredPlayer(parameter);
        if (!registered)
        {
            return onActivate();
        }

        this.playerName = parameter;
        return true;
    }


    public boolean hasPlayerName()
    {
        return this.playerName != null;
    }


    public String getTitle()
    {
        return messages.get("title");
    }


    public boolean isRegisterPhase()
    {
        return controller.isRegisterPhase();
    }

    public boolean isQuestionPhase()
    {
        return controller.isQuestionPhase();
    }

    public boolean isAnswerPhase()
    {
        return controller.isAnswerPhase();
    }

    public boolean isResultsPhase()
    {
        return controller.isResultsPhase();
    }

    public boolean isStandingsPhase()
    {
        return controller.isStandingsPhase();
    }

    public boolean isFinalPhase()
    {
        return controller.isFinalPhase();
    }
}
