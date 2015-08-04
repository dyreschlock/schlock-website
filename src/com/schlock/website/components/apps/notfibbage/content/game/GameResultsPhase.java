package com.schlock.website.components.apps.notfibbage.content.game;

import com.schlock.website.pages.apps.notfibbage.Game;
import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class GameResultsPhase
{
    @Inject
    private NotFibbageController controller;

    @Inject
    private NotFibbageManagement management;


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


    public boolean isCorrectAnswer()
    {
        return management.isResponseCorrect(currentAnswer);
    }


    public boolean isHasPlayersResponded()
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
