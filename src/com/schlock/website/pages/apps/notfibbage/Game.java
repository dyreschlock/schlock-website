package com.schlock.website.pages.apps.notfibbage;

import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Game
{
    @Inject
    private NotFibbageManagement management;

    @Inject
    private NotFibbageController controller;

    @Inject
    private Messages messages;


    Object onActivate()
    {
        return true;
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
