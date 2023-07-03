package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.PocketCore;
import com.schlock.website.pages.apps.pocket.Index;
import com.schlock.website.services.apps.pocket.PocketDataService;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Parameter;
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

    @Parameter
    private PocketCore core;

    @Parameter
    private String genre;

    public String getTotalCount()
    {
        final String SPAN_HTML = "<span class=\"totalCount\">%s</span>";

        int count = 0;
        if (core != null)
        {
            count = pocketDataService.getGamesByCore(core).size();
        }
        else
        {
            count = pocketDataService.getGames().size();
        }
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

        for(String genreId : pocketDataService.getGameGenres())
        {
            String genreText = messages.get(genreId);

            int count = 0;
            if (core != null)
            {
                count = pocketDataService.getGamesByCoreGenre(core, genreId).size();
            }
            else
            {
                count = pocketDataService.getGamesByGenre(genreId).size();
            }

            String link = null;
            if (count > 0)
            {
                if(StringUtils.equalsIgnoreCase(genre, genreId))
                {
                    link = Index.getPageLink(core, null);
                }
                else
                {
                    link = Index.getPageLink(core, genreId);
                }
            }

            data.add(new DataPanelData(genreText, Integer.toString(count), link));
        }
        return data;
    }
}
