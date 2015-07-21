package com.schlock.website.services.apps.notfibbage;

import java.util.List;

public interface NotFibbageManagement
{
    //register phase

    public void registerPlayer(String playerName);

    public boolean isRegisteredPlayer(String playerName);

    public List<String> getRegisteredPlayers();

    //question phase

    public boolean hasNextQuestion(int roundNumber);

    public boolean setNewQuestion(int roundNumber);

    public String getQuestionText();

    public boolean registerResponse(String player, String response);

    public boolean isResponseCorrect(String response);

    public List<String> getPlayersResponded();

    public boolean hasPlayerResponded(String player);

    //answer phase

    public List<String> getQuestionResponses();

    public boolean registerAnswer(String player, String answer);

    public List<String> getPlayersAnswered();

    public boolean hasPlayerAnswered(String player);

    //results phase

    public List<String> getAnsweredResponses();

    public List<String> getPlayersByAnswer(String response);

    public List<String> getPlayersByResponse(String response);

    public void tabulatePointsAndClearResponses();

    //standings phase

    public List<String> getPlayersInOrderByScore();

    public Integer getScore(String player);

    //reset phase

    public void resetGame();

    public void resetScore();
}
