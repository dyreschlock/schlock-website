package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.pocket.PocketGame;
import com.schlock.website.services.apps.pocket.PocketDataService;

import javax.inject.Inject;
import java.util.List;

public class MenuPanel
{
    @Inject
    private PocketDataService pocketDataService;


    public String getTotalCount()
    {
        final String SPAN_HTML = "<span class=\"totalCount\">%s</span>";

        List<PocketGame> games = pocketDataService.getGames();

        int count = games.size();

        return String.format(SPAN_HTML, Integer.toString(count));
    }
}
