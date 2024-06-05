package com.schlock.website.entities.apps.games;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

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


    public static List<DataPanelData> createPanelData(List<String[]> results)
    {
        List<DataPanelData> data = new ArrayList<DataPanelData>();
        for(String[] d : results)
        {
            String name = d[0];
            String count = d[1];

            data.add(new DataPanelData(name, count));
        }
        return data;
    }

}
