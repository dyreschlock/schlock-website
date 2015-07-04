package com.schlock.website.entities.apps.notfibbage;

public class NotFibbagePlayer
{
    private final String name;

    private int score = 0;

    private String currentResponse;
    private String currentAnswer;


    public NotFibbagePlayer(String name)
    {
        this.name = name;
    }

    public void reset()
    {
        this.score = 0;
        this.currentResponse = null;
        this.currentAnswer = null;
    }


    public String getName()
    {
        return name;
    }


    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getCurrentResponse()
    {
        return currentResponse;
    }

    public void setCurrentResponse(String currentResponse)
    {
        this.currentResponse = currentResponse;
    }

    public String getCurrentAnswer()
    {
        return currentAnswer;
    }

    public void setCurrentAnswer(String currentAnswer)
    {
        this.currentAnswer = currentAnswer;
    }
}
