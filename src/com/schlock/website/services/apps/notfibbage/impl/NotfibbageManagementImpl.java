package com.schlock.website.services.apps.notfibbage.impl;

import com.schlock.website.services.apps.notfibbage.NotfibbageManagement;

import java.util.HashMap;
import java.util.Map;

public class NotfibbageManagementImpl implements NotfibbageManagement
{
    private Map<String, Integer> playersAndScore;


    public NotfibbageManagementImpl()
    {


        resetGame();
    }





    public void registerPlayer(String playerName)
    {
        this.playersAndScore.put(playerName, 0);
    }

    public boolean isRegisteredPlayer(String playerName)
    {
        Integer score = this.playersAndScore.get(playerName);
        return score != null;
    }

    public Integer getScore(String playerName)
    {
        return this.playersAndScore.get(playerName);
    }

    public void addPoints(String playerName, Integer points)
    {
        Integer score = this.playersAndScore.get(playerName);
        if(score != null)
        {
            score += points;
            this.playersAndScore.put(playerName, score);
        }
    }


    public void resetGame()
    {
        this.playersAndScore = new HashMap<String, Integer>();
    }

    public void resetScore()
    {
        for (String player : this.playersAndScore.keySet())
        {
            this.playersAndScore.put(player, 0);
        }
    }
}
