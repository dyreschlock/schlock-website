package com.schlock.website.components.apps.pocket;

import com.schlock.website.services.apps.pocket.PocketDataService;

import javax.inject.Inject;

public class MenuPanel
{
    @Inject
    private PocketDataService pocketDataService;


    public String getTotalCount()
    {
        final String SPAN_HTML = "<span class=\"totalCount\">%s</span>";

        int count = pocketDataService.getGames().size();

        return String.format(SPAN_HTML, Integer.toString(count));
    }
}
