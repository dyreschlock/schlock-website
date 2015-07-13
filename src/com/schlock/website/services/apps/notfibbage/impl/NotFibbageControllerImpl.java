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
    private int currentRound = 0;


    public NotFibbageControllerImpl(NotFibbageManagement management)
    {
        this.management = management;
    }



    public void next()
    {
        if (isRegisterPhase())
        {
            this.currentRound++;

            boolean nextRoundAvailable = management.setNewQuestion(currentRound);
            if (nextRoundAvailable)
            {
                this.currentPhase = QUESTION_PHASE;
            }
            else
            {
                this.currentPhase = FINAL_PHASE;
            }
        }


    }


    public String getGameState()
    {
        return currentPhase;
    }

    public String getPlayerState(String playerName)
    {
        String state = currentPhase + playerName;

        if (isQuestionPhase())
        {
            boolean responded = management.hasPlayerResponded(playerName);
            state += Boolean.toString(responded);
        }
        if (isAnswerPhase())
        {
            boolean answered = management.hasPlayerAnswered(playerName);
            state += Boolean.toString(answered);
        }
        return state;
    }

    public int getRoundNumber()
    {
        return currentRound;
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
