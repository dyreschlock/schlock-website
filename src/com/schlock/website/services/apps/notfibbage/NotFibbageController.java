package com.schlock.website.services.apps.notfibbage;

public interface NotFibbageController
{
    public void next();

    public void reset();

    public void restart();


    public String getGameState();

    public String getPlayerState(String playerName);

    public int getRoundNumber();


    public boolean isRegisterPhase();

    //register players

    //start


    public boolean isQuestionPhase();

    //load question

    //gather responses from players

    //show who has responded


    public boolean isAnswerPhase();

    //show responses

    //gather answers from players

    //show who has answered



    public boolean isResultsPhase();

    //results

    //show incorrect response

    //show players that chose it

    //show player (or players) that wrote the response

    //give points




    //show correct response

    //show players that chose it

    //give points


    public boolean isStandingsPhase();

    //show round results



    public boolean isFinalPhase();

    //show final results


    //play again

    //delete game

}
