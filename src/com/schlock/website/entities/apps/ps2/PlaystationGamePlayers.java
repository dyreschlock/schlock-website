package com.schlock.website.entities.apps.ps2;

public enum PlaystationGamePlayers
{
    ONE("players/1", "1"),
    TWO("players/2", "2"),
    THREE("players/3", "3"),
    FOUR("players/4", "4");

    private final String players;
    private final String playersText;

    PlaystationGamePlayers(String players, String playerText)
    {
        this.players = players;
        this.playersText = playerText;
    }

    public String players()
    {
        return this.players;
    }

    public String playersText()
    {
        return this.playersText;
    }

    public static PlaystationGamePlayers getFromText(String players)
    {
        if (FOUR.players.equals(players))
        {
            return FOUR;
        }
        if (THREE.players.equals(players))
        {
            return THREE;
        }
        if (TWO.players.equals(players))
        {
            return TWO;
        }
        if (ONE.players.equals(players))
        {
            return ONE;
        }
        return null;
    }
}
