package com.schlock.website.pages.apps.ranking;

import com.schlock.website.services.apps.ranking.RankingService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

public class RankingIndex
{
    @Inject
    private RankingService service;


    @Property
    private String currentOption;


    public boolean isFinished()
    {
        return service.isFinished();
    }

    public List<String> getCurrentOptions()
    {
        return service.getCurrentPair();
    }

    Object onSelectOption(String option)
    {
        service.choose(option);

        return this;
    }

    public List<String> getSortedList()
    {
        return service.getSortedList();
    }
}
