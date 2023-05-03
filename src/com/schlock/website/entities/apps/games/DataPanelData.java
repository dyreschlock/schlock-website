package com.schlock.website.entities.apps.games;

import org.apache.commons.lang.StringUtils;

public class DataPanelData
{
    private String name;
    private String count;

    private String link;

    public DataPanelData(String name, String count)
    {
        this(name, count, null);
    }

    public DataPanelData(String name, String count, String link)
    {
        this.name = name;
        this.count = count;
        this.link = link;
    }

    public String getName()
    {
        return name;
    }

    public String getCount()
    {
        return count;
    }

    public String getLink()
    {
        return link;
    }

    public boolean isHasLink()
    {
        return StringUtils.isNotBlank(this.link);
    }
}
