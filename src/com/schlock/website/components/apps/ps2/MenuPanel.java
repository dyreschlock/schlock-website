package com.schlock.website.components.apps.ps2;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
import com.schlock.website.pages.apps.ps2.Index;
import com.schlock.website.services.database.apps.ps2.PlaystationGameDAO;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

public class MenuPanel
{
    private static final String GENRE_TITLE_KEY = "genre";

    @Inject
    private Messages messages;

    @Inject
    private PlaystationGameDAO gameDAO;

    @Parameter
    private PlaystationPlatform platform;

    @Parameter
    private String genre;

    @Parameter
    private Boolean imageView;


    public String getTotalCount()
    {
        final String SPAN_HTML = "<span class=\"totalCount\">%s</span>";

        int totalCount = gameDAO.getCombinedAvailableGames().size();

        String output;
        if (platform != null || genre != null)
        {
            int count = gameDAO.getCombinedAvailableGamesByPlatformGenre(platform, genre).size();
            output = String.format(SPAN_HTML, Integer.toString(count));
            output += " / " + totalCount;
        }
        else
        {
            output = String.format(SPAN_HTML, Integer.toString(totalCount));
        }
        return output;
    }


    public String getGenreTitle()
    {
        return messages.get(GENRE_TITLE_KEY);
    }

    public List<DataPanelData> getGenreData()
    {
        final String SPAN_HTML = "<span class=\"bold\">%s</span>";

        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(String[] genreData : gameDAO.getAllGenres())
        {
            String name = genreData[0];
            String count = genreData[1];
            String link = Index.getPageLink(imageView, platform, name);

            if (name.equalsIgnoreCase(genre))
            {
                name = String.format(SPAN_HTML, name);
                link = Index.getPageLink(imageView, platform, null);
            }

            data.add(new DataPanelData(name, count, link));
        }
        return data;
    }
}
