package com.schlock.website.entities.apps.games;

public class DataPanelData
{
    private String name;
    private String count;

    public DataPanelData(String name, String count)
    {
        this.name = name;
        this.count = count;
    }

    public String getName()
    {
        return name;
    }

    public String getCount()
    {
        return count;
    }
}
