package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.tapestry5.ioc.Messages;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel
{
    private static final String GENRE_TITLE_KEY = "genre";

    @Inject
    private Messages messages;

    @Inject
    private PocketDataService pocketDataService;


    public String getTotalCount()
    {
        final String SPAN_HTML = "<span class=\"totalCount\">%s</span>";

        int count = pocketDataService.getGames().size();

        return String.format(SPAN_HTML, Integer.toString(count));
    }


    public String getGenreTitle()
    {
        return messages.get(GENRE_TITLE_KEY);
    }

    public List<DataPanelData> getGenreData()
    {
        final String SPAN_HTML = "<span class=\"bold\">%s</span>";

        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(String genre : pocketDataService.getGameGenres())
        {
            int count = pocketDataService.getGamesByGenre(genre).size();

            data.add(new DataPanelData(genre, Integer.toString(count)));
        }
        return data;
    }
}
