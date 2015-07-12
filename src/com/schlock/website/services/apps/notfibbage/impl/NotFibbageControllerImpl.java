package com.schlock.website.services.apps.notfibbage.impl;

import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;

public class NotFibbageControllerImpl implements NotFibbageController
{
    private final static String REGISTER_PHASE = "register";
    private final static String QUESTION_PHASE = "question";
    private final static String ANSWER_PHASE = "answer";
    private final static String RESULTS_PHASE = "results";
    private final static String STANDINGS_PHASE = "standings";
    private final static String FINAL_PHASE = "final";

    private final NotFibbageManagement management;

    private String currentPhase = REGISTER_PHASE;

    public NotFibbageControllerImpl(NotFibbageManagement management)
    {
        this.management = management;
    }



    public void beginGame()
    {
    }

    public void next()
    {
    }


    public String getGameState()
    {
        return currentPhase;
    }

    public String getPlayerState(String playerName)
    {
        String state = currentPhase + playerName;



        return state;
    }

    public boolean isRegisterPhase()
    {
        return REGISTER_PHASE.equals(currentPhase);
    }

    public boolean isQuestionPhase()
    {
        return QUESTION_PHASE.equals(currentPhase);
    }

    public boolean isAnswerPhase()
    {
        return ANSWER_PHASE.equals(currentPhase);
    }

    public boolean isResultsPhase()
    {
        return RESULTS_PHASE.equals(currentPhase);
    }

    public boolean isStandingsPhase()
    {
        return STANDINGS_PHASE.equals(currentPhase);
    }

    public boolean isFinalPhase()
    {
        return FINAL_PHASE.equals(currentPhase);
    }
}
