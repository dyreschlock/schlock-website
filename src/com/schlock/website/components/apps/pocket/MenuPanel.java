package com.schlock.website.components.apps.pocket;

import com.schlock.website.entities.apps.games.DataPanelData;
import com.schlock.website.entities.apps.pocket.Device;
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
    private static final String DEVICE_TITLE_KEY = "device";

    private final String SPAN_HTML = "<span class=\"%s\">%s</span>";
    private final String SELECTED = "bold";
    private final String UNSELECTED = "zero";

    @Inject
    private Messages messages;

    @Inject
    private PocketDataService pocketDataService;

    @Parameter
    private Device device;

    @Parameter
    private PocketCore core;

    @Parameter
    private String genre;

    @Parameter
    private Boolean imageView;

    public String getTotalCount()
    {
        final String SPAN_HTML = "<span class=\"totalCount\">%s</span>";

        int count = pocketDataService.getGamesByDeviceCoreGenre(device, core, genre).size();
        if (genre == null)
        {
            if (device != null && core == null)
            {
                int totalCount = pocketDataService.getGames().size();

                String output = String.format(SPAN_HTML, count) + " / " + totalCount;
                return output;
            }
            return String.format(SPAN_HTML, count);
        }

        int totalCount = pocketDataService.getGamesByDeviceCoreGenre(device, core, null).size();

        String output = String.format(SPAN_HTML, Integer.toString(count)) + " / " + totalCount;
        return output;
    }

    public String getDeviceTitle()
    {
        return messages.get(DEVICE_TITLE_KEY);
    }

    public List<DataPanelData> getDeviceData()
    {
        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(Device d : Device.values())
        {
            String key = d.name().toLowerCase();
            String displayText = messages.get(key);
            if (device != null && d == device)
            {
                displayText = String.format(SPAN_HTML, SELECTED, displayText);
            }
            else if (device != null)
            {
                displayText = String.format(SPAN_HTML, UNSELECTED, displayText);
            }

            int count = pocketDataService.getGamesByDeviceCoreGenre(d, core, null).size();

            String link = null;
            if (count > 0)
            {
                if (d == device)
                {
                    link = Index.getPageLink(imageView, null, core, genre);
                }
                else
                {
                    link = Index.getPageLink(imageView, d, core, genre);
                }
            }
            data.add(new DataPanelData(displayText, Integer.toString(count), link));
        }
        return data;
    }


    public String getGenreTitle()
    {
        return messages.get(GENRE_TITLE_KEY);
    }

    public List<DataPanelData> getGenreData()
    {
        List<DataPanelData> data = new ArrayList<DataPanelData>();

        for(String genreId : pocketDataService.getGameGenres())
        {
            String genreText = messages.get(genreId);
            if (genreId.equalsIgnoreCase(genre))
            {
                genreText = String.format(SPAN_HTML, SELECTED, genreText);
            }

            int count = pocketDataService.getGamesByDeviceCoreGenre(device, core, genreId).size();

            String link = null;
            if (count > 0)
            {
                if(StringUtils.equalsIgnoreCase(genre, genreId))
                {
                    link = Index.getPageLink(imageView, device, core, null);
                }
                else
                {
                    link = Index.getPageLink(imageView, device, core, genreId);
                }
            }

            data.add(new DataPanelData(genreText, Integer.toString(count), link));
        }
        return data;
    }
}
