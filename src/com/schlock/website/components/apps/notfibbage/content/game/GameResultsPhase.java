package com.schlock.website.components.apps.notfibbage.content.game;

import com.schlock.website.pages.apps.notfibbage.Game;
import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class GameResultsPhase
{
    @Inject
    private NotFibbageController controller;

    @Inject
    private NotFibbageManagement management;

    @Inject
    private Messages messages;


    @Property
    private String currentAnswer;

    @Property
    private String currentPlayer;

    @Property
    private Integer currentIndex;

    @Property
    @Persist
    private Integer selectedIndex;



    public List<String> getAnsweredResponses()
    {
        return management.getAnsweredResponses();
    }

    public String getResultCssClass()
    {
        String css = "result ";

//        if (currentIndex != selectedIndex)
//        {
//            css = " inactive ";
//        }
        return css;
    }

    public String getColumnClass()
    {
        String css = "column ";

        if ((currentIndex + 1) % 2 == 0)
        {
            css += " twoColumnLast ";
        }

        return css;
    }

    public boolean isComputerAnswer()
    {
        boolean correct = isCorrectAnswer();
        boolean notPlayerAnswer = !isHasPlayersResponded();

        return correct || notPlayerAnswer;
    }

    public String getComputerResponse()
    {
        String truth = messages.get("truth");
        String lie = messages.get("lie");

        if (isCorrectAnswer())
        {
            return truth;
        }
        return lie;
    }


    private boolean isCorrectAnswer()
    {
        return management.isResponseCorrect(currentAnswer);
    }

    private boolean isHasPlayersResponded()
    {
        List<String> players = getPlayersResponded();
        return players.size() != 0;
    }

    public List<String> getPlayersResponded()
    {
        return management.getPlayersByResponse(currentAnswer);
    }

    public List<String> getPlayersAnswered()
    {
        return management.getPlayersByAnswer(currentAnswer);
    }

    public String getComputerPoints()
    {
        if (isCorrectAnswer())
        {
            return "+" + management.getCurrentPointValue();
        }
        else
        {
            return "-" + management.getHalfCurrentPointValue();
        }
    }

    public String getPlayerPointMath()
    {
        int points = management.getHalfCurrentPointValue();
        int players = getPlayersResponded().size();

        return points + " x " + players + " = ";
    }

    public String getPlayerPointTotal()
    {
        int points = management.getHalfCurrentPointValue();
        int players = getPlayersResponded().size();

        return "+" + (points * players);
    }


    public String getCurrentQuestion()
    {
        return management.getQuestionText();
    }

    Object onNext()
    {
        controller.next();

        selectedIndex = 0;

        return Game.class;
    }
}
