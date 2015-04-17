package com.schlock.website.services.notfibbage;

public interface NotfibbageManagement
{

    public void registerPlayer(String playerName);

    public boolean isRegisteredPlayer(String playerName);

    public Integer getScore(String playerName);

    public void addPoints(String playerName, Integer points);

    public void resetGame();

    public void resetScore();
}
