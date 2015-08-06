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
            this.currentRound = 1;

            management.setNewQuestion(currentRound);
            this.currentPhase = QUESTION_PHASE;
        }
        else if (isQuestionPhase())
        {
            this.currentPhase = ANSWER_PHASE;
        }
        else if (isAnswerPhase())
        {
            this.currentPhase = RESULTS_PHASE;
        }
        else if (isResultsPhase())
        {
            management.tabulatePointsAndClearResponses();

            int nextRound = currentRound + 1;
            boolean nextQuestionAvailable = management.hasNextQuestion(nextRound);
            if (nextQuestionAvailable)
            {
                this.currentPhase = STANDINGS_PHASE;
            }
            else
            {
                this.currentPhase = FINAL_PHASE;
            }
        }
        else if (isStandingsPhase())
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

    public void reset()
    {
        this.currentPhase = REGISTER_PHASE;

        management.resetGame();
    }

    public void restart()
    {
        this.currentPhase = REGISTER_PHASE;

        management.resetScore();
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
