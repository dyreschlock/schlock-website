package com.schlock.website.pages.apps.notfibbage;

import com.schlock.website.components.apps.notfibbage.content.game.*;
import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "context:js/apps/notfibbage.js")
public class Game
{
    public static final int REFRESH_RATE_SECONDS = 2;


    @Inject
    private NotFibbageManagement management;

    @Inject
    private NotFibbageController controller;

    @Inject
    private JavaScriptSupport javascript;

    @Inject
    private ComponentResources resources;

    @Inject
    private Messages messages;


    @InjectComponent
    private GameRegisterPhase registerComponent;

    @InjectComponent
    private GameQuestionPhase questionComponent;

    @InjectComponent
    private GameAnswerPhase answerComponent;

    @InjectComponent
    private GameResultsPhase resultsComponent;

    @InjectComponent
    private GameStandingsPhase standingsComponent;

    @InjectComponent
    private GameFinalPhase finalComponent;


    Object onActivate()
    {
        return true;
    }


    public String getTitle()
    {
        return messages.get("title");
    }


    public String getPhaseTitle()
    {
        String key = controller.getGameState() + "-phase";
        return messages.get(key);
    }

    public boolean isShowNextButton()
    {
        return !isFinalPhase();
    }

    Object onNext()
    {
        controller.next();

        resetComponentState();

        return this;
    }

    Object onReset()
    {
        controller.reset();

        resetComponentState();

        return this;
    }

    Object onRestart()
    {
        controller.restart();

        resetComponentState();

        return this;
    }

    private void resetComponentState()
    {
        registerComponent.reset();
        questionComponent.reset();
        answerComponent.reset();
        resultsComponent.reset();
        standingsComponent.reset();
        finalComponent.reset();
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
