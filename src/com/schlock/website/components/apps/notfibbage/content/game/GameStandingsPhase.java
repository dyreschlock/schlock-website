package com.schlock.website.components.apps.notfibbage.content.game;

import com.schlock.website.pages.apps.notfibbage.Game;
import com.schlock.website.services.apps.notfibbage.NotFibbageController;
import com.schlock.website.services.apps.notfibbage.NotFibbageManagement;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class GameStandingsPhase
{
    @Inject
    private NotFibbageController controller;

    @Inject
    private NotFibbageManagement management;


    @Property
    private String currentPlayer;

    @Property
    private Integer currentIndex;


    public List<String> getPlayers()
    {
        return management.getPlayersInOrderByScore();
    }

    public boolean isDisplayLinebreak()
    {
        return currentIndex != 0;
    }

    public String getCurrentScore()
    {
        int score = management.getScore(currentPlayer);
        return Integer.toString(score);
    }


    Object onNext()
    {
        controller.next();

        return Game.class;
    }
}
