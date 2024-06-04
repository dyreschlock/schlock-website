package com.schlock.website.components.apps.ps2;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.ps2.PlaystationPlatform;
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

        int totalCount = gameDAO.getAvailableGames().size();

        String output;

        output = String.format(SPAN_HTML, Integer.toString(totalCount));

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

        for(String genre : gameDAO.getAllGenres())
        {
            data.add(new DataPanelData(genre, "0"));
        }
        return data;
    }
}
